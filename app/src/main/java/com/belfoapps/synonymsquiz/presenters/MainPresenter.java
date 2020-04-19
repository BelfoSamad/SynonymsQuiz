package com.belfoapps.synonymsquiz.presenters;

import android.util.Log;

import com.belfoapps.synonymsquiz.R;
import com.belfoapps.synonymsquiz.contracts.MainContract;
import com.belfoapps.synonymsquiz.models.SharedPreferencesHelper;
import com.belfoapps.synonymsquiz.pojo.Synonym;
import com.belfoapps.synonymsquiz.utils.GDPR;
import com.belfoapps.synonymsquiz.views.activities.MainActivity;
import com.google.ads.consent.ConsentForm;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class MainPresenter implements MainContract.Presenter {
    private static final String TAG = "MainPresenter";
    /**************************************** Declarations ****************************************/
    private MainActivity mView;
    private SharedPreferencesHelper mSharedPrefs;
    private JSONArray jsonArray;
    private GDPR gdpr;
    private ConsentForm form;
    private FirebaseFirestore mDb;
    private InterstitialAd mInterstitialAd;
    private int count;

    /***************************************** Constructor ****************************************/
    public MainPresenter(MainActivity mView) {

        this.mSharedPrefs = new SharedPreferencesHelper(mView);
        mDb = FirebaseFirestore.getInstance();
        gdpr = new GDPR(mSharedPrefs, form, mView);

        count = 0;

        getSynonymJsonFile();
        attach(mView);
    }

    /***************************************** Essential Methods **********************************/
    @Override
    public void attach(MainContract.View view) {
        mView = (MainActivity) view;
    }

    @Override
    public void dettach() {
        mView = null;
    }

    @Override
    public boolean isAttached() {
        return !(mView == null);
    }

    /**************************************** Methods *********************************************/
    @Override
    public void loadAd(AdView ad) {
        if (mSharedPrefs.isAdPersonalized()) {
            gdpr.showPersonalizedAdBanner(ad);
        } else {
            gdpr.showNonPersonalizedAdBanner(ad);
        }
    }

    @Override
    public void loadInterstitialAd() {
        mInterstitialAd = new InterstitialAd(mView);
        mInterstitialAd.setAdUnitId(mView.getResources().getString(R.string.INTERSTITIAL_AD_ID));

        if (mView.getResources().getBoolean(R.bool.INTERSTITIAL_AD_Enabled)) {
            if (mSharedPrefs.isAdPersonalized())
                gdpr.loadPersonalizedInterstitialAd(mInterstitialAd);
            else gdpr.loadNonPersonalizedInterstitialAd(mInterstitialAd);

            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    mInterstitialAd.show();
                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    // Code to be executed when an ad request fails.
                }

                @Override
                public void onAdOpened() {
                    // Code to be executed when the ad is displayed.
                }

                @Override
                public void onAdClicked() {
                    // Code to be executed when the user clicks on an ad.
                }

                @Override
                public void onAdLeftApplication() {
                    // Code to be executed when the user has left the app.
                }

                @Override
                public void onAdClosed() {
                }
            });
        }
    }

    @Override
    public void showInterstitialAd() {
        if (count < mView.getResources().getInteger(R.integer.SHOW_INTERSTITIAL_AD_AFTER))
            count++;
        else {
            loadInterstitialAd();
            count = 0;
        }
    }

    @Override
    public void getSynonymJsonFile() {
        try {
            jsonArray = new JSONArray(mSharedPrefs.getJsonFileFromAssets("synonyms.json"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getSynonyms(boolean offline) {
        if (offline) {
            //Offline Mode
            try {
                int ran1 = new Random().nextInt(jsonArray.length() - 1);
                int ran2 = new Random().nextInt(jsonArray.length() - 1);

                while (ran1 == ran2)
                    ran2 = new Random().nextInt(jsonArray.length());

                JSONObject obj = (JSONObject) jsonArray.get(ran1);
                mView.nextSynonym(new Synonym(obj.getString("word"), obj.getString("synonym")),
                        ((JSONObject) jsonArray.get(ran2)).getString("word"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            //Get Synonyms from Firebase
            mDb.collection("synonyms")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            int ran1 = new Random().nextInt(task.getResult().getDocuments().size() - 1);
                            int ran2 = new Random().nextInt(task.getResult().getDocuments().size() - 1);
                            while (ran1 == ran2)
                                ran2 = new Random().nextInt(task.getResult().getDocuments().size() - 1);
                            mView.nextSynonym(Objects.requireNonNull(task.getResult().getDocuments().get(ran1).toObject(Synonym.class)),
                                    (String) task.getResult().getDocuments().get(ran2).get("word"));
                        } else getSynonyms(true);
                    });
        }
    }

    @Override
    public void saveSynonym(Synonym syn) {
        ArrayList<Synonym> synonyms = mSharedPrefs.getSynonyms();
        if (synonyms == null)
            synonyms = new ArrayList<>();
        if (!synonyms.contains(syn))
            synonyms.add(syn);
        mSharedPrefs.saveSynonyms(synonyms);
    }
}

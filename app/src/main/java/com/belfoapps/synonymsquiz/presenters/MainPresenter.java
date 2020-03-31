package com.belfoapps.synonymsquiz.presenters;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.belfoapps.synonymsquiz.contracts.MainContract;
import com.belfoapps.synonymsquiz.models.SharedPreferencesHelper;
import com.belfoapps.synonymsquiz.pojo.Synonym;
import com.belfoapps.synonymsquiz.utils.Config;
import com.belfoapps.synonymsquiz.views.activities.MainActivity;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

public class MainPresenter implements MainContract.Presenter {
    private static final String TAG = "MainPresenter";
    /**************************************** Declarations ****************************************/
    private MainActivity mView;
    private SharedPreferencesHelper mSharedPrefs;
    private Gson gson;
    private JSONArray jsonArray;

    /***************************************** Constructor ****************************************/
    public MainPresenter(MainActivity mView) {
        this.mSharedPrefs = new SharedPreferencesHelper(mView);
        gson = new Gson();

        if (Config.OFFLINE_MODE)
            getSynonymsJsonFile();
        else getRandomWordsJsonFile();

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
    public void getRandomWordsJsonFile() {
        try {
            jsonArray = new JSONArray(mSharedPrefs.getJsonFileFromAssets("words.json"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getSynonymsJsonFile() {
        try {
            jsonArray = new JSONArray(mSharedPrefs.getJsonFileFromAssets("synonyms.json"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getRandomWord() {
        try {
            return jsonArray.getString(new Random().nextInt(jsonArray.length() - 1));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void getSynonym() {
        if (Config.OFFLINE_MODE) {
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
            String random_word = getRandomWord();
            new CallbackTask().execute(getUrl(random_word), random_word);
        }
    }

    private String getUrl(String word) {
        return "https://api.datamuse.com/words?ml=" + word + "&rel_[syn]";
    }

    @SuppressLint("StaticFieldLeak")
    private class CallbackTask extends AsyncTask<String, Integer, String> {

        String word = null;

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                word = params[1];
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

                // read the output from the server
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }

                return stringBuilder.toString();

            } catch (Exception e) {
                e.printStackTrace();
                return e.toString();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                JSONArray array = new JSONArray(result);
                if (array.length() > 0)
                    mView.nextSynonym(new Synonym(word, ((JSONObject) array.get(0)).getString("word")),
                            getRandomWord());
                else {
                    String random_word = getRandomWord();
                    new CallbackTask().execute(getUrl(random_word), random_word);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
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

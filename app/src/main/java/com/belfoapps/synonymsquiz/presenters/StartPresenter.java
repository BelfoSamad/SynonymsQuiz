package com.belfoapps.synonymsquiz.presenters;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.belfoapps.synonymsquiz.R;
import com.belfoapps.synonymsquiz.contracts.StartContract;
import com.belfoapps.synonymsquiz.models.SharedPreferencesHelper;
import com.belfoapps.synonymsquiz.utils.GDPR;
import com.belfoapps.synonymsquiz.views.activities.MainActivity;
import com.belfoapps.synonymsquiz.views.activities.StartActivity;
import com.google.ads.consent.ConsentForm;
import com.google.android.gms.ads.AdView;

public class StartPresenter implements StartContract.Presenter {
    /**************************************** Declarations ****************************************/
    private StartActivity mView;
    private GDPR gdpr;
    private ConsentForm form;

    /***************************************** Constructor ****************************************/
    public StartPresenter(StartActivity mView) {
        this.mView = mView;
        gdpr = new GDPR(new SharedPreferencesHelper(mView), form, mView);
    }

    /***************************************** Essential Methods **********************************/
    @Override
    public void attach(StartContract.View view) {
        mView = (StartActivity) view;
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
    public void checkGDPRConsent() {
        if (mView.getResources().getBoolean(R.bool.GDPR_Enabled)) {
            gdpr.checkForConsent();
        }
    }

    @Override
    public boolean checkNetwork() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mView.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void goToMain() {
        Intent intent = new Intent(mView, MainActivity.class);
        if (checkNetwork()) {
            intent.putExtra("offline", false);
            mView.startActivity(intent);
        } else if (mView.getResources().getBoolean(R.bool.OFFLINE_Enabled)) {
            intent.putExtra("offline", true);
            mView.startActivity(intent);
        } else Toast.makeText(mView, "No Network", Toast.LENGTH_SHORT).show();
    }
}

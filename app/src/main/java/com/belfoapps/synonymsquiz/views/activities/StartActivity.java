package com.belfoapps.synonymsquiz.views.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

import com.belfoapps.synonymsquiz.R;
import com.belfoapps.synonymsquiz.contracts.StartContract;
import com.belfoapps.synonymsquiz.presenters.StartPresenter;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartActivity extends AppCompatActivity implements StartContract.View {
    /**************************************** Declarations ****************************************/
    private StartPresenter mPresenter;

    /**************************************** Click Listeners *************************************/
    @OnClick(R.id.start)
    public void start() {
        mPresenter.goToMain();
    }

    @OnClick(R.id.drop_menu)
    public void dropDownMenu(View v) {
        PopupMenu popup = new PopupMenu(StartActivity.this, v);
        popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.gdpr:
                    mPresenter.requestGDPR();
                    break;
                case R.id.rate:
                    Uri uri = Uri.parse("market://details?id=" + getPackageName());
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                            Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                            Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    try {
                        startActivity(goToMarket);
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                    }
                    break;
                case R.id.others:
                    String developerName = getResources().getString(R.string.developer_name);
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=" + developerName)));
                    } catch (ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/search?q=" + developerName + "&hl=en")));
                    }
                    break;
                case R.id.feedback:
                    String[] TO = {getResources().getString(R.string.developer_email)};
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setData(Uri.parse("mailto:"));
                    emailIntent.setType("text/plain");

                    emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback For " + getResources().getString(R.string.app_name));
                    String message = "Message:\n\n---\n";
                    try {
                        PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
                        String version = pInfo.versionName;
                        message = message + "App Version : " + version + "\n";
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                    message = message + "Android Version : " + android.os.Build.VERSION.SDK_INT + "\n";
                    message = message + "Device Brand : " + Build.MANUFACTURER + "\n";
                    message = message + "Device Model : " + Build.MODEL;

                    emailIntent.putExtra(Intent.EXTRA_TEXT, message);

                    try {
                        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        //Error
                    }
                    break;
            }
            return true;
        });
        popup.show();
    }

    /**************************************** Essential Methods ***********************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //Set ButterKnife
        ButterKnife.bind(this);

        //init Presenter
        mPresenter = new StartPresenter(this);

        //check GDPR
        mPresenter.checkGDPRConsent();
    }

    /**************************************** Methods *********************************************/
    @Override
    public void initUI() {
        //Add Code Here
    }
}

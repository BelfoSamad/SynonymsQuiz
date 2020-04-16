package com.belfoapps.synonymsquiz.views.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.belfoapps.synonymsquiz.R;
import com.belfoapps.synonymsquiz.contracts.MainContract;
import com.belfoapps.synonymsquiz.pojo.Synonym;
import com.belfoapps.synonymsquiz.presenters.MainPresenter;
import com.google.android.gms.ads.AdView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    /**************************************** Declarations ****************************************/
    private MainPresenter mPresenter;
    private Synonym synonym = null;
    /**************************************** View Declarations ***********************************/
    @BindView(R.id.synonym)
    TextView synonym_text;
    @BindView(R.id.option_1)
    Button option_1;
    @BindView(R.id.option_2)
    Button option_2;
    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.adView)
    AdView ad;

    /**************************************** Click Listeners *************************************/
    @OnClick(R.id.saved_synonyms)
    public void goToSynonyms() {
        startActivity(new Intent(MainActivity.this, SynonymsActivity.class));
    }

    @OnClick(R.id.home)
    public void goToHome() {
        onBackPressed();
    }

    @OnClick({R.id.option_1, R.id.option_2})
    public void option(View v) {
        if (((Button) v).getText().equals(synonym.getSynonym())) {
            v.setBackground(getResources().getDrawable(R.drawable.button_correct));
            ((Button) v).setTextColor(getResources().getColor(R.color.answerButtonTextColor));
        } else {
            v.setBackground(getResources().getDrawable(R.drawable.button_wrong));
            ((Button) v).setTextColor(getResources().getColor(R.color.answerButtonTextColor));
            mPresenter.saveSynonym(synonym);
        }

        loading.setVisibility(View.VISIBLE);
        new Handler().postDelayed(() -> mPresenter.getSynonym(getIntent().getBooleanExtra("offline", true)), 1000);
    }

    /**************************************** Essential Methods ***********************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set ButterKnife
        ButterKnife.bind(this);

        //init Main Presenter
        mPresenter = new MainPresenter(this);

        //init UI:
        initUI();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ad.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ad.destroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ad.resume();
    }

    /**************************************** Methods *********************************************/
    @Override
    public void initUI() {
        //First Synonym
        loading.setVisibility(View.VISIBLE);
        mPresenter.getSynonym(getIntent().getBooleanExtra("offline", true));
    }

    @Override
    public void initAdBanner() {
        if (getResources().getBoolean(R.bool.AD_BANNER_Enabled)) {
            mPresenter.loadAd(ad);
        } else {
            ad.setVisibility(GONE);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void nextSynonym(Synonym synonym, String wrong_answer) {
        loading.setVisibility(GONE);

        this.synonym = synonym;

        option_1.setBackground(getResources().getDrawable(R.drawable.button_normal));
        option_1.setTextColor(getResources().getColor(R.color.buttonTextColor));
        option_2.setBackground(getResources().getDrawable(R.drawable.button_normal));
        option_2.setTextColor(getResources().getColor(R.color.buttonTextColor));

        Random rn = new Random();

        synonym_text.setText(getResources().getString(R.string.loading_words) + " " + synonym.getWord());
        if (rn.nextBoolean()) {
            option_1.setText(synonym.getSynonym());
            option_2.setText(wrong_answer);
        } else {
            option_1.setText(wrong_answer);
            option_2.setText(synonym.getSynonym());
        }
    }
}

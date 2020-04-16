package com.belfoapps.synonymsquiz.views.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.belfoapps.synonymsquiz.R;
import com.belfoapps.synonymsquiz.contracts.StartContract;
import com.belfoapps.synonymsquiz.presenters.StartPresenter;
import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartActivity extends AppCompatActivity implements StartContract.View {
    /**************************************** Declarations ****************************************/
    private StartPresenter mPresenter;
    /**************************************** View Declarations ***********************************/
    /**************************************** Click Listeners *************************************/
    @OnClick(R.id.start)
    public void start() {
        mPresenter.goToMain();
    }

    /**************************************** Essential Methods ***********************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //Set ButterKnife
        ButterKnife.bind(this);

        //init Presenter
        mPresenter = new StartPresenter(this);

        //init UI
    }

    /**************************************** Methods *********************************************/
    @Override
    public void initUI() {
        //Add Code Here
    }
}

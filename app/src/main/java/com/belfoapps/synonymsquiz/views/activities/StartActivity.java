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
    //Drawer Menu
    @BindView(R.id.drawer_layout)
    public DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    /**************************************** Click Listeners *************************************/
    @OnClick(R.id.start)
    public void start() {
        startActivity(new Intent(StartActivity.this, MainActivity.class));
    }

    @OnClick(R.id.menu)
    public void openDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START);
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
        initUI();
    }

    /**************************************** Methods *********************************************/
    @Override
    public void initUI() {
        mNavigationView.setItemIconTintList(null);
        mNavigationView.setNavigationItemSelectedListener(item -> {

            int id = item.getItemId();

            switch (id) {

            }

            // close drawer when item is tapped
            mDrawerLayout.closeDrawers();
            return true;
        });
    }
}

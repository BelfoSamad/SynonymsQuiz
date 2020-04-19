package com.belfoapps.synonymsquiz.views.activities;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.belfoapps.synonymsquiz.R;
import com.belfoapps.synonymsquiz.contracts.SynonymsContract;
import com.belfoapps.synonymsquiz.pojo.Synonym;
import com.belfoapps.synonymsquiz.presenters.SynonymsPresenter;
import com.belfoapps.synonymsquiz.views.adapters.SynonymsAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SynonymsActivity extends AppCompatActivity implements SynonymsContract.View {
    private static final String TAG = "SynonymsActivity";
    /**************************************** Declarations ****************************************/
    private SynonymsPresenter mPresenter;
    private SynonymsAdapter mAdapter;

    /**************************************** View Declarations ***********************************/
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.back)
    ImageButton back;

    /**************************************** Click Listeners *************************************/
    @OnClick(R.id.back)
    public void goBack() {
        onBackPressed();
    }

    /**************************************** Essential Methods ***********************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synonyms);

        //Set ButterKnife
        ButterKnife.bind(this);

        //init Main Presenter
        mPresenter = new SynonymsPresenter(this);

        //init UI
        initUI();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SynonymsActivity.this, StartActivity.class));
    }

    @Override
    public void initUI() {
        //When Right to left
        LayerDrawable layerDrawable = (LayerDrawable) getResources()
                .getDrawable(R.drawable.back);

        Drawable left = getResources().getDrawable(R.drawable.icon_holder_left);
        Drawable right = getResources().getDrawable(R.drawable.icon_holder_right);

        if (getResources().getBoolean(R.bool.is_right_to_left)) {
            layerDrawable.setDrawableByLayerId(R.id.back_drawable, left);
        } else {
            layerDrawable.setDrawableByLayerId(R.id.back_drawable, right);
        }
        back.setBackground(layerDrawable);

        mPresenter.initRecyclerView();
    }

    @Override
    public void initRecyclerView(ArrayList<Synonym> synonyms) {
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mAdapter = new SynonymsAdapter(synonyms);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new RecipesItemDecoration());
        mRecyclerView.setAdapter(mAdapter);
    }

    private static class RecipesItemDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.bottom = 24;
        }
    }
}

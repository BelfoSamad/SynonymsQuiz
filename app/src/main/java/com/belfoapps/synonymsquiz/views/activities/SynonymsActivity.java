package com.belfoapps.synonymsquiz.views.activities;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

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
    /**************************************** Declarations ****************************************/
    private SynonymsPresenter mPresenter;
    private SynonymsAdapter mAdapter;

    /**************************************** View Declarations ***********************************/
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    /**************************************** Click Listeners *************************************/
    @OnClick(R.id.back)
    public void goBack() {
        startActivity(new Intent(SynonymsActivity.this, StartActivity.class));
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
    public void initUI() {
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

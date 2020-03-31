package com.belfoapps.synonymsquiz.presenters;

import com.belfoapps.synonymsquiz.contracts.SynonymsContract;
import com.belfoapps.synonymsquiz.models.SharedPreferencesHelper;
import com.belfoapps.synonymsquiz.views.activities.SynonymsActivity;

public class SynonymsPresenter implements SynonymsContract.Presenter {
    /**************************************** Declarations ****************************************/
    private SynonymsActivity mView;
    private SharedPreferencesHelper mSharedPrefs;

    /***************************************** Constructor ****************************************/
    public SynonymsPresenter(SynonymsActivity mView) {
        this.mSharedPrefs = new SharedPreferencesHelper(mView);

        attach(mView);
    }

    /***************************************** Essential Methods **********************************/
    @Override
    public void attach(SynonymsContract.View view) {
        mView = (SynonymsActivity) view;
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
    public void initRecyclerView() {
        mView.initRecyclerView(mSharedPrefs.getSynonyms());
    }
}

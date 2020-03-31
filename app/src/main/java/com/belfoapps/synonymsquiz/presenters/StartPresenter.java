package com.belfoapps.synonymsquiz.presenters;

import com.belfoapps.synonymsquiz.contracts.StartContract;
import com.belfoapps.synonymsquiz.views.activities.StartActivity;

public class StartPresenter implements StartContract.Presenter {
    /**************************************** Declarations ****************************************/
    private StartActivity mView;

    /***************************************** Constructor ****************************************/
    public StartPresenter(StartActivity mView) {
        this.mView = mView;
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

}

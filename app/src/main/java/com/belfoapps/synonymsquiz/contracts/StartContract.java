package com.belfoapps.synonymsquiz.contracts;

import com.belfoapps.synonymsquiz.base.BasePresenter;
import com.belfoapps.synonymsquiz.base.BaseView;

public interface StartContract {
    interface Presenter extends BasePresenter<StartContract.View> {

        void checkGDPRConsent();

        boolean checkNetwork();

        void goToMain();
    }

    interface View extends BaseView {


    }
}

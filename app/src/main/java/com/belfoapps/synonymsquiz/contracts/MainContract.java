package com.belfoapps.synonymsquiz.contracts;

import com.belfoapps.synonymsquiz.base.BasePresenter;
import com.belfoapps.synonymsquiz.base.BaseView;
import com.belfoapps.synonymsquiz.pojo.Synonym;
import com.google.android.gms.ads.AdView;

public interface MainContract {

    interface Presenter extends BasePresenter<View> {

        void loadAd(AdView ad);

        void getSynonymsJsonFile();

        void getSynonym(boolean offline);

        void saveSynonym(Synonym syn);

    }

    interface View extends BaseView {

        void initAdBanner();

        void nextSynonym(Synonym synonym, String wrong_answer);

    }
}

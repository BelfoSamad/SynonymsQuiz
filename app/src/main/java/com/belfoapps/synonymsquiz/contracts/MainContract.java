package com.belfoapps.synonymsquiz.contracts;

import com.belfoapps.synonymsquiz.base.BasePresenter;
import com.belfoapps.synonymsquiz.base.BaseView;
import com.belfoapps.synonymsquiz.pojo.Synonym;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public interface MainContract {

    interface Presenter extends BasePresenter<View> {

        void loadAd(AdView ad);

        void loadInterstitialAd();

        void showInterstitialAd();

        void getSynonymJsonFile();

        void getSynonyms(boolean offline);

        void saveSynonym(Synonym syn);

    }

    interface View extends BaseView {

        void initAdBanner();

        void nextSynonym(Synonym synonym, String wrong_answer);

    }
}

package com.belfoapps.synonymsquiz.contracts;

import com.belfoapps.synonymsquiz.base.BasePresenter;
import com.belfoapps.synonymsquiz.base.BaseView;
import com.belfoapps.synonymsquiz.pojo.Synonym;

public interface MainContract {

    interface Presenter extends BasePresenter<View> {

        void getRandomWordsJsonFile();

        void getSynonymsJsonFile();

        String getRandomWord();

        void getSynonym();

        void saveSynonym(Synonym syn);

    }

    interface View extends BaseView {

        void nextSynonym(Synonym synonym, String wrong_answer);

    }
}

package com.belfoapps.synonymsquiz.contracts;

import com.belfoapps.synonymsquiz.base.BasePresenter;
import com.belfoapps.synonymsquiz.base.BaseView;
import com.belfoapps.synonymsquiz.pojo.Synonym;

import java.util.ArrayList;

public interface SynonymsContract {

    interface Presenter extends BasePresenter<View> {

        void initRecyclerView();

    }

    interface View extends BaseView {

        void initRecyclerView(ArrayList<Synonym> synonyms);

    }
}

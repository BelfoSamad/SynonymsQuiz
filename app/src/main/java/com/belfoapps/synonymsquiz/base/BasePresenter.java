package com.belfoapps.synonymsquiz.base;

public interface BasePresenter<V extends BaseView> {

    void attach(V view);

    void dettach();

    boolean isAttached();
}

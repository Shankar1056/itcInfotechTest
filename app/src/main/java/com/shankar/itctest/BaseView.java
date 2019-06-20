package com.shankar.itctest;

public interface BaseView<T extends BasePresenter> {

    void setPresenter(T Presenter);
}

package com.hoka.readrssproject.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.hoka.readrssproject.View.IMainActivity;


@InjectViewState
public class PresenterMainActivity extends MvpPresenter<IMainActivity> {
    public void showRecyclerDate(String strNameUrl){
        getViewState().showRecyclerViewDate(strNameUrl);
    }


}

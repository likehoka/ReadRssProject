package com.hoka.readrssproject.presenter;

import android.os.Bundle;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.hoka.readrssproject.View.IMainActivity;


@InjectViewState
public class PresenterMainActivity extends MvpPresenter<IMainActivity> {

    public void showRecyclerDate(){
        getViewState().showRecyclerViewDate();
    }

    public void showRecyclerState(Bundle mBundleRecyclerViewState) {
        getViewState().showRecyclerViewState(mBundleRecyclerViewState);
    }

}

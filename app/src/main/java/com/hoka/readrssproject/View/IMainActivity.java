package com.hoka.readrssproject.View;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;


public interface IMainActivity extends MvpView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void showRecyclerViewDate(String strNameUrl);

}
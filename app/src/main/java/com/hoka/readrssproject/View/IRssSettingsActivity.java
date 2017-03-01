package com.hoka.readrssproject.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

    /*
    The view is an interface that displays data (the model)
    and routes user commands (events) to the Presenter to act upon that data
     */

public interface IRssSettingsActivity extends MvpView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void showMainActivity();
    void showErrorMessage();
}

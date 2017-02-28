package com.hoka.readrssproject.View;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface IRssSettingsActivity extends MvpView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void showMainActivity();
    void showErrorMessage();
}

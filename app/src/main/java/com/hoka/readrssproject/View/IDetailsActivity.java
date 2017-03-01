package com.hoka.readrssproject.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.hoka.readrssproject.model.FeedItem;

public interface IDetailsActivity extends MvpView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void showDetails(FeedItem mFeedItem);
}

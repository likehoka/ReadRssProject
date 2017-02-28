package com.hoka.readrssproject.View;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.hoka.readrssproject.Adapter.FeedItem;


public interface IDetailsActivity extends MvpView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void showDetails(FeedItem mFeedItem);
}

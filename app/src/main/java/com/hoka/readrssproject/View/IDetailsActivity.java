package com.hoka.readrssproject.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.hoka.readrssproject.model.FeedItem;

    /*
    The view is an interface that displays data (the model)
    and routes user commands (events) to the Presenter to act upon that data
     */

public interface IDetailsActivity extends MvpView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void showDetails(FeedItem mFeedItem);
}

package com.hoka.readrssproject.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.hoka.readrssproject.model.FeedItem;
import com.hoka.readrssproject.view.IDetailsActivity;

@InjectViewState
public class DetailsActivityPresenter extends MvpPresenter<IDetailsActivity> {

    public void showDetails(FeedItem mFeedItem) {
        getViewState().showDetails(mFeedItem);
    }
}
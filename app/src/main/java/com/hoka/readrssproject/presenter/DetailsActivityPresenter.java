package com.hoka.readrssproject.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.hoka.readrssproject.model.FeedItem;
import com.hoka.readrssproject.view.IDetailsActivity;

    /*
    retrieves the data from the Model,
    applies the UI logic and manages the state of the View,
    decides what to display and reacts to user input notifications from the View.
     */

@InjectViewState
public class DetailsActivityPresenter extends MvpPresenter<IDetailsActivity> {

    public void showDetails(FeedItem mFeedItem) {
        getViewState().showDetails(mFeedItem);
    }
}
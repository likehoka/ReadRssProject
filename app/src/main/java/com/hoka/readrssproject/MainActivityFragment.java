package com.hoka.readrssproject;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.hoka.readrssproject.View.IMainActivity;
import com.hoka.readrssproject.adapter.NewsAdapter;
import com.hoka.readrssproject.database.DatabaseManager;
import com.hoka.readrssproject.model.FeedItem;
import com.hoka.readrssproject.model.SaveUrlItem;
import com.hoka.readrssproject.presenter.PresenterMainActivity;
import com.hoka.readrssproject.utils.ReadRss;
import com.hoka.readrssproject.utils.VerticalSpace;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivityFragment extends MvpAppCompatFragment implements SwipeRefreshLayout.OnRefreshListener,
        IMainActivity {

    /*
    *This MainActivityFragment refers to MainActivity,
    *fragment used Presenter, which perform tasks and
    *show result of the public void showRecyclerViewDate(String strNameUrl)
    */

    @InjectPresenter
    PresenterMainActivity mPresenterMainActivity;
    @BindView(R.id.recycler_view_fragment_main)
    RecyclerView mRecyclerview;
    @BindView(R.id.swipe_refresh_layout_fragment_main)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private NewsAdapter mAdapter;
    private List<FeedItem> mList;
    private List<SaveUrlItem> mListUrl;
    private ArrayList<FeedItem> mListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        DatabaseManager.init(getContext());
        mAdapter = new NewsAdapter();
        mRecyclerview.setAdapter(mAdapter);
        mListUrl = new ArrayList<>(DatabaseManager.getInstance().getAllSaveUrlItem());
        //If
        if (mListUrl.size() != 0) {
            mPresenterMainActivity.showRecyclerDate(mListUrl.get(0).getUrl().toString());
        }
        mRecyclerview.addItemDecoration(new VerticalSpace(25));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }


    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        if (mListUrl.size() != 0) {
            mPresenterMainActivity.showRecyclerDate(mListUrl.get(0).getUrl().toString());
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void onResume() {
        super.onResume();
        mListUrl = new ArrayList<>(DatabaseManager.getInstance().getAllSaveUrlItem());
        if (mListUrl.size() != 0) {
            mPresenterMainActivity.showRecyclerDate(mListUrl.get(0).getUrl().toString());
        }
    }


    @Override
    public void showRecyclerViewDate(String strNameUrl) {
        mList = new ArrayList<>(DatabaseManager.getInstance().getQueryForEqFeedItem("url", strNameUrl));
        mListAdapter = new ArrayList<FeedItem>();
        if (mList.size() == 0) {
            ReadRss readRss = new ReadRss(getActivity(), mRecyclerview, mAdapter, strNameUrl);
            readRss.execute();
        }
        if (mList != null) {
            for (int i = mList.size() - 1; i > -1; i--) {
                mListAdapter.add(mList.get(i));
            }
        }
        mAdapter.setAdapterList(mListAdapter);
    }
}
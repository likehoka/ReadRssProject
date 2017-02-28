package com.hoka.readrssproject;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.hoka.readrssproject.Adapter.DatabaseHelper;
import com.hoka.readrssproject.Adapter.DatabaseManager;
import com.hoka.readrssproject.Adapter.FeedItem;
import com.hoka.readrssproject.Adapter.NewsAdapter;
import com.hoka.readrssproject.Adapter.ReadRss;
import com.hoka.readrssproject.Adapter.SaveUrlItem;
import com.hoka.readrssproject.Adapter.VerticalSpace;
import com.hoka.readrssproject.View.IMainActivity;
import com.hoka.readrssproject.presenter.PresenterMainActivity;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivityFragment extends MvpAppCompatFragment implements SwipeRefreshLayout.OnRefreshListener,
        IMainActivity{
    @InjectPresenter
    PresenterMainActivity mPresenterMainActivity;

    private DatabaseHelper databaseHelper = null;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView; //назначаем переменную к content_main.xml recyclerView
    private LinearLayoutManager mLinearLayoutManager;
    private NewsAdapter mAdapter;
    private List<FeedItem> mList;
    private List<SaveUrlItem> mListUrl;
    private ArrayList<FeedItem> mListAdapter;
    private ArrayList<SaveUrlItem> mListAllUrl;
    private static Bundle mBundleRecyclerViewState=null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        DatabaseManager.init(getContext());
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
        mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mAdapter = new NewsAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh_layout);

        mListUrl = new ArrayList<>(DatabaseManager.getInstance().getAllSaveUrlItem());
        if (mListUrl.size() != 0) {
            mPresenterMainActivity.showRecyclerDate(mListUrl.get(0).getUrl().toString());
        }

        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        //Добавляем отступы экрана новости(слева, справа, снизу, сверху)
        mRecyclerView.addItemDecoration(new VerticalSpace(25));
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
    public void showRecyclerViewDate(String s) {
        mList = new ArrayList<>(DatabaseManager.getInstance().getQueryForEqFeedItem("url", s));
        mListAdapter = new ArrayList<FeedItem>();
        if (mList.size()==0) {
            ReadRss readRss = new ReadRss(getActivity(), mRecyclerView, mAdapter, s);
            readRss.execute();
        }
        if(mList != null) {
            for (int i = mList.size()-1; i > -1; i--) {
                mListAdapter.add(mList.get(i));
            }
        }
        mAdapter.setAdapterList(mListAdapter);
    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
        }
        return databaseHelper;
    }
}
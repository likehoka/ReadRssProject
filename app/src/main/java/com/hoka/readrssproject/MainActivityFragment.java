package com.hoka.readrssproject;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.hoka.readrssproject.Adapter.DatabaseManager;
import com.hoka.readrssproject.Adapter.FeedItem;
import com.hoka.readrssproject.Adapter.NewsAdapter;
import com.hoka.readrssproject.View.IMainActivity;
import com.hoka.readrssproject.presenter.PresenterMainActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivityFragment extends MvpAppCompatFragment implements SwipeRefreshLayout.OnRefreshListener,
        IMainActivity {
    @InjectPresenter
    PresenterMainActivity mPresenterMainActivity;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView; //назначаем переменную к content_main.xml recyclerView
    private LinearLayoutManager mLinearLayoutManager;
    private NewsAdapter mAdapter;
    private List<FeedItem> mList;
    private ArrayList<FeedItem> mListAdapter;
    private static Bundle mBundleRecyclerViewState=null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        DatabaseManager.init(getContext());
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
        //20.02.2017 14^59
        mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mAdapter = new NewsAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh_layout);

        mPresenterMainActivity.showRecyclerDate();

        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        //Добавляем отступы экрана новости(слева, справа, снизу, сверху)
        mRecyclerView.addItemDecoration(new VerticalSpace(25));

        mSwipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        ReadRss readRss = new ReadRss(getActivity(), mRecyclerView, mAdapter);
        readRss.execute();
        mSwipeRefreshLayout.setRefreshing(false);
    }

/*    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
*/

    @Override
    public void showRecyclerViewState(Bundle mBundleRecyclerViewState) {
        if (mBundleRecyclerViewState != null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable("recycler_state");
            mRecyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }
    }

    @Override
    public void showRecyclerViewDate() {
        mList = new ArrayList<>(DatabaseManager.getInstance().getAllFeedItem());
        mListAdapter = new ArrayList<FeedItem>();
        if (mList == null) {
            ReadRss readRss = new ReadRss(getActivity(), mRecyclerView, mAdapter);
            readRss.execute();
        }
        if(mList != null) {
            for (int i = mList.size()-1; i > -1; i--) {
                mListAdapter.add(mList.get(i));
            }
        }
        mAdapter.setAdapterList(mListAdapter);
    }

}
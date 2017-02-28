package com.hoka.readrssproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hoka.readrssproject.Adapter.DatabaseManager;
import com.hoka.readrssproject.Adapter.FeedItem;
import com.hoka.readrssproject.Adapter.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    //private SwipeRefreshLayout mSwipeRefreshLayout;
    //private RecyclerView mRecyclerView; //назначаем переменную к content_main.xml recyclerView
    private LinearLayoutManager mLinearLayoutManager;
    private NewsAdapter mAdapter;
    private List<FeedItem> mList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        DatabaseManager.init(getContext());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        mAdapter = new NewsAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);


        mList = new ArrayList<>(DatabaseManager.getInstance().getAllFeedItem());
        if (mList != null) {
            mAdapter.setAdapterList(mList);
        }

        mAdapter.setAdapterList(mList);
        ReadRss readRss = new ReadRss(getActivity(), mRecyclerView, mAdapter);
        readRss.execute();

        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        //Добавляем отступы экрана новости(слева, справа, снизу, сверху)
        mRecyclerView.addItemDecoration(new VerticalSpace(25));

        mSwipeRefreshLayout.setOnRefreshListener(this);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        ReadRss readRss = new ReadRss(getActivity(), mRecyclerView, mAdapter);
        readRss.execute();
        mRecyclerView.scrollToPosition(0);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onPause() {
        super.onPause();
//        mBundleRecyclerViewState = new Bundle();
//        Parcelable listState = recyclerView.getLayoutManager().onSaveInstanceState();
//        mBundleRecyclerViewState.putParcelable("recycler_state", listState);
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (mBundleRecyclerViewState != null) {
//            Parcelable listState = mBundleRecyclerViewState.getParcelable("recycler_state");
//            recyclerView.getLayoutManager().onRestoreInstanceState(listState);
//        }
    }

}

package com.hoka.readrssproject.Adapter;

import android.content.Context;

import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static DatabaseManager instance;
    private DatabaseHelper helper;

    public static void init(Context context) {
        if (instance == null) {
            instance = new DatabaseManager(context);
        }
    }

    static public DatabaseManager getInstance() {
        return instance;
    }

    public DatabaseManager(Context context) {
        helper = new DatabaseHelper(context);
    }

    public DatabaseHelper getHelper() { return helper; }

    public List<FeedItem> getAllFeedItem() {
        List<FeedItem> feeditem = null;
        try {
            feeditem = (ArrayList<FeedItem>) getHelper().getFeedItemDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feeditem;
    }

    public ArrayList<FeedItem> getQueryForEq(String param, String text){
        ArrayList<FeedItem> feedItems = null;
        try {
            feedItems = (ArrayList<FeedItem>) getHelper().getFeedItemDao().queryForEq(param, text);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedItems;
    }



    public void addFeedItem(FeedItem feeditem) {
        try {
            getHelper().getFeedItemDao().create(feeditem);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void refreshFeedItem(FeedItem feeditem) {
        try {
            getHelper().getFeedItemDao().refresh(feeditem);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateFeedItem(FeedItem wishList) {
        try {
            getHelper().getFeedItemDao().update(wishList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFeedItem(int id) {
        try {
            DeleteBuilder<FeedItem, Integer> deleteBuilder = getHelper().getFeedItemDao().deleteBuilder();
            deleteBuilder.where().eq("id", id);
            deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

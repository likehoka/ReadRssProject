package com.hoka.readrssproject.Adapter;

import android.content.Context;

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

    public ArrayList<FeedItem> getQueryForEqFeedItem(String nameTable, String param){
        ArrayList<FeedItem> feedItems = null;
        try {
            feedItems = (ArrayList<FeedItem>) getHelper().getFeedItemDao().queryForEq(nameTable, param);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedItems;
    }

    public List<SaveUrlItem> getAllSaveUrlItem() {
        List<SaveUrlItem> saveUrlItem = null;
        try {
            saveUrlItem = (ArrayList<SaveUrlItem>) getHelper().getSaveUrlItemDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return saveUrlItem;
    }
}

package com.hoka.readrssproject.database;

import android.content.Context;

import com.hoka.readrssproject.model.FeedItem;
import com.hoka.readrssproject.model.SaveUrlItem;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

    /*
    Class receives commands for working with database
     */

public class DatabaseManager {
    private static DatabaseManager sInstance;
    private DatabaseHelper mDatabaseHelper;

    public static void init(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseManager(context);
        }
    }

    static public DatabaseManager getInstance() {
        return sInstance;
    }

    public DatabaseManager(Context context) {
        mDatabaseHelper = new DatabaseHelper(context);
    }

    public DatabaseHelper getHelper() { return mDatabaseHelper; }

    public ArrayList<FeedItem> getQueryForEqFeedItem(String nameTable, String param){
        ArrayList<FeedItem> mArrayListFeedItems = null;
        try {
            mArrayListFeedItems = (ArrayList<FeedItem>) getHelper().getFeedItemDao().queryForEq(nameTable, param);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mArrayListFeedItems;
    }

    public List<SaveUrlItem> getAllSaveUrlItem() {
        List<SaveUrlItem> mListSaveUrlItem = null;
        try {
            mListSaveUrlItem = (ArrayList<SaveUrlItem>) getHelper().getSaveUrlItemDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mListSaveUrlItem;
    }
}

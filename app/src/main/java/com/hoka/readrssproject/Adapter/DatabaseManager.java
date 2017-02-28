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



    public ArrayList<FeedItem> getQueryForEqFeedItem(String nameTable, String param){
        ArrayList<FeedItem> feedItems = null;
        try {
            feedItems = (ArrayList<FeedItem>) getHelper().getFeedItemDao().queryForEq(nameTable, param);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedItems;
    }

    public ArrayList<SaveUrlItem> getQueryForEqSaveUrlItem(String nameTable, String param) {
        ArrayList<SaveUrlItem> saveUrlItems = null;
        try {
            saveUrlItems = (ArrayList<SaveUrlItem>) getHelper().getSaveUrlItemDao().queryForEq(nameTable, param);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return saveUrlItems;
    }

    public void addFeedItem(FeedItem feeditem) {
        try {
            getHelper().getFeedItemDao().create(feeditem);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addSaveUrlItem(SaveUrlItem saveUrlItem) {
        try {
            getHelper().getSaveUrlItemDao().create(saveUrlItem);
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

    public List<SaveUrlItem> getAllSaveUrlItem() {
        List<SaveUrlItem> saveUrlItem = null;
        try {
            saveUrlItem = (ArrayList<SaveUrlItem>) getHelper().getSaveUrlItemDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return saveUrlItem;
    }




    public void deleteSaveUrlItem() {
        try {
            getHelper().getSaveUrlItemDao().delete(getAllSaveUrlItem());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void refreshSaveUrlItem(SaveUrlItem saveUrlItem) {
        try {
            getHelper().getSaveUrlItemDao().refresh(saveUrlItem);
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

    public void updateSaveUrlItem(SaveUrlItem wishList) {
        try {
            getHelper().getSaveUrlItemDao().update(wishList);
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

    public void deleteSaveUrl(){
        try {
            DeleteBuilder<SaveUrlItem, Integer> deleteBuilder = getHelper().getSaveUrlItemDao().deleteBuilder();
            deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSaveUrlItem(int id) {
        try {
            DeleteBuilder<SaveUrlItem, Integer> deleteBuilder = getHelper().getSaveUrlItemDao().deleteBuilder();
            deleteBuilder.where().eq("id", id);
            deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

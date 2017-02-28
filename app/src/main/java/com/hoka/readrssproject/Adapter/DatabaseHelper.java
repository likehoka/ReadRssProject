package com.hoka.readrssproject.Adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "news.db";
    private static final int DATABASE_VERSION = 1;

    //private Dao<FeedItem, Integer> feedItemDao = null;
    private Dao<FeedItem, Integer> feeItemDao;
    private Dao<SaveUrlItem, Integer> saveUrlItemDao;
    RuntimeExceptionDao<FeedItem, Integer> feedItemRuntimeDao = null;
    RuntimeExceptionDao<SaveUrlItem, Integer> saveUrlItemRuntimeDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, FeedItem.class);
            TableUtils.createTable(connectionSource, SaveUrlItem.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, FeedItem.class, true);
            TableUtils.dropTable(connectionSource, SaveUrlItem.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Dao<FeedItem, Integer> getFeedItemDao() throws SQLException {
        if (feeItemDao == null) {
            feeItemDao = getDao(FeedItem.class);
        }
        return feeItemDao;
    }

    public RuntimeExceptionDao<FeedItem, Integer> getFeedItemRuntimeExceptionDao() {
        if (feedItemRuntimeDao == null){
            feedItemRuntimeDao = getRuntimeExceptionDao(FeedItem.class);
        }
        return feedItemRuntimeDao;
    }

    public Dao<SaveUrlItem, Integer> getSaveUrlItemDao() throws SQLException {
        if (saveUrlItemDao == null) {
            saveUrlItemDao = getDao(SaveUrlItem.class);
        }
        return saveUrlItemDao;
    }

    public RuntimeExceptionDao<SaveUrlItem, Integer> getSaveUrlItemRuntimeExceptionDao() {
        if (saveUrlItemRuntimeDao == null) {
            saveUrlItemRuntimeDao = getRuntimeExceptionDao(SaveUrlItem.class);
        }
        return saveUrlItemRuntimeDao;
    }

}

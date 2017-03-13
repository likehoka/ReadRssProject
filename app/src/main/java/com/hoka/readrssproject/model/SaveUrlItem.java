package com.hoka.readrssproject.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

    /*
    Create table url_item
     */

@DatabaseTable(tableName = "url_item")
public class SaveUrlItem implements Serializable {
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_URL = "url";

    @DatabaseField(columnName = COLUMN_ID, generatedId = true)
    private int id;

    @DatabaseField(columnName = COLUMN_URL, dataType = DataType.STRING)
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
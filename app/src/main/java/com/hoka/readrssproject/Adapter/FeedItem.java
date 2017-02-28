package com.hoka.readrssproject.Adapter;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "all_news")
public class FeedItem implements Serializable {
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_URL = "url";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_LINK = "link";
    public static final String COLUMN_CONTENT = "content";

    @DatabaseField(columnName = COLUMN_ID, generatedId = true)
    private int id;

    @DatabaseField(columnName = COLUMN_URL, dataType = DataType.STRING)
    private String url;

    @DatabaseField(columnName = COLUMN_TITLE)
    private String title;

    @DatabaseField(columnName = COLUMN_DATE, dataType = DataType.STRING)
    private String date;

    @DatabaseField(columnName = COLUMN_AUTHOR, dataType = DataType.STRING)
    private String author;

    @DatabaseField(columnName = COLUMN_IMAGE, dataType = DataType.BYTE_ARRAY)
    private byte[] image;

    @DatabaseField(columnName = COLUMN_DESCRIPTION, dataType = DataType.STRING)
    private String description;

    @DatabaseField(columnName = COLUMN_LINK, dataType = DataType.STRING)
    private String link;

    @DatabaseField(columnName = COLUMN_CONTENT, dataType = DataType.STRING)
    private String content;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public FeedItem() {

    }

    public FeedItem(String url, String title, String date, String author, byte[] image, String description, String link, String content) {
        this.url = url;
        this.title = title;
        this.date = date;
        this.author = author;
        this.image = image;
        this.description = description;
        this.link = link;
        this.content = content;
    }
}

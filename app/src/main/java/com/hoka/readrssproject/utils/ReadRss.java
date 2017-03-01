package com.hoka.readrssproject.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.hoka.readrssproject.adapter.NewsAdapter;
import com.hoka.readrssproject.database.DatabaseHelper;
import com.hoka.readrssproject.model.FeedItem;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ReadRss extends AsyncTask<Void, Void, Void> {
    private Context mContext;

    private DatabaseHelper mDatabaseHelper = OpenHelperManager.getHelper(mContext, DatabaseHelper.class);;
    private Dao<FeedItem, Integer> mFeedItemIntegerDao;
    private ArrayList<FeedItem> mArrayListFeedItems; //объявляем для хранения записей

    private boolean mBaseStatus=false;
    private ProgressDialog mProgressDialog;
    private URL mUrl;
    RecyclerView recyclerView;
    static String sAdressURL="";
    private NewsAdapter mAdapter;

    public ReadRss(Context context, RecyclerView recyclerView, NewsAdapter adapter, String sAdressURL){
        this.sAdressURL = sAdressURL;
        this.recyclerView = recyclerView;
        this.mContext = context;
        this.mAdapter = adapter;
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage("Loading...");
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (Getdata()!=null && !isCancelled()){
            ProcessXml(Getdata());
        }
        else {
            Toast.makeText(mContext, "Ошибка при проверке загружаемого документа", Toast.LENGTH_SHORT).show();
            Log.d("mLog", "Ошибка");
            }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage("Downloading content, please wait...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        creatBase(mArrayListFeedItems);
        recyclerView.scrollToPosition(0);
        this.mProgressDialog.dismiss();
    }

    @Override
    protected void onCancelled(Void aVoid) {
        super.onCancelled(aVoid);
        this.mProgressDialog.dismiss();
    }


    private byte[] getLogoImage(String url){
        try{
            URL imageURL = new URL(url);
            URLConnection ucon = imageURL.openConnection();
            InputStream inputStream = ucon.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(500);
            int current =0;
            while((current = bis.read())!= - 1){
                byteArrayOutputStream.write((byte)current);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (Exception exc){
            exc.printStackTrace();
            Log.d("ImageManager", "Error:" + exc.toString());
        } return null;
    }

    public void creatBase(ArrayList<FeedItem> feed){
        FeedItem feedItem = new FeedItem();
        for (int i = feed.size()-1; i > -1; i--){
            feedItem = feed.get(i);
            try {
                Dao<FeedItem, Integer> feedDao = mDatabaseHelper.getFeedItemDao();
                feedDao.create(feedItem);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            mAdapter.add(0, feed.get(i));
        }
    }

    private void ProcessXml(Document data) {

        if(data != null){
            mArrayListFeedItems=new ArrayList<>();
            Element root = data.getDocumentElement();
            Node channel = root.getChildNodes().item(1);
            NodeList items=channel.getChildNodes();
            for (int i=0; i<items.getLength();i++){
                Node cureentchild=items.item(i);
                if (cureentchild.getNodeName().equals("item")){
                    mBaseStatus=false; //меняет свое значение на true если считываемая запись в базе уже есть
                    FeedItem item=new FeedItem();
                    NodeList itemchilds=cureentchild.getChildNodes();
                    for (int j=0;j<itemchilds.getLength();j++){
                        Log.d("items.getLength()", j+"");
                        Node cureent= itemchilds.item(j);
                        ArrayList<FeedItem> daoList = null;
                        if(cureent.getNodeName().equals("title")){

                            try {
                                mFeedItemIntegerDao = mDatabaseHelper.getFeedItemDao();
                                daoList = (ArrayList<FeedItem>) mFeedItemIntegerDao.queryForEq("title", cureent.getTextContent());
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                            if (daoList.size() > 0) {
                                mBaseStatus=true;
                            }

                            if (mBaseStatus==false){ //+
                                item.setTitle(cureent.getTextContent());
                            }

                        } else if (cureent.getNodeName().equals("description")&&mBaseStatus==false){
                            item.setDescription(cureent.getTextContent());
                        } else if (cureent.getNodeName().equals("pubDate")&&mBaseStatus==false){
                            item.setDate(cureent.getTextContent());
                        } else if (cureent.getNodeName().equals("link")&&mBaseStatus==false){
                            item.setLink(cureent.getTextContent());
                        } else if (cureent.getNodeName().equals("media:thumbnail")&&mBaseStatus==false){
                            String url = cureent.getAttributes().item(0).getTextContent();
                            item.setContent(url);
                        } else if (cureent.getNodeName().equals("enclosure")) {
                            String url = cureent.getAttributes().item(0).getTextContent();
                            item.setImage(getLogoImage(url));
                        } else if (cureent.getNodeName().equals("media:content")) {
                            String url = cureent.getAttributes().item(0).getTextContent();
                            item.setImage(getLogoImage(url));
                        }
                    }

                    if(mBaseStatus==false){
                        item.setUrl(sAdressURL);
                        mArrayListFeedItems.add(item); //Запись значений в ArrayList
                    }
                }
            }
        }
    }

    public Document Getdata(){
        try {
            mUrl = new URL(sAdressURL);
            HttpURLConnection connection = (HttpURLConnection) mUrl.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream=connection.getInputStream();
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder=builderFactory.newDocumentBuilder();
            Document xmlDoc=builder.parse(inputStream);
            return xmlDoc;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

}

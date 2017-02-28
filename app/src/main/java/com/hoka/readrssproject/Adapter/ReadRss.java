package com.hoka.readrssproject.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

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
    Context context;

    private DatabaseHelper databaseHelper = null;
    private Dao<FeedItem, Integer> feedItemIntegerDao;
    private ArrayList<FeedItem> feedItems; //объявляем для хранения записей

    boolean BaseStatus=false;
    boolean Updater = false;
    public static Boolean ProcessReadrss=false;
    private ProgressDialog progressDialog;
    private URL url;
    RecyclerView recyclerView;
    static String sAdressURL="";
    NewsAdapter adapter;

    public ReadRss(Context context, RecyclerView recyclerView, NewsAdapter adapter, String sAdressURL){
        this.sAdressURL = sAdressURL;
        this.recyclerView = recyclerView;
        this.context = context;
        this.adapter = adapter;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (Getdata()!=null){
            ProcessXml(Getdata());
        }
        else {
            Toast.makeText(context, "Ошибка при проверке загружаемого документа", Toast.LENGTH_SHORT).show();
            Log.d("mLog", "Ошибка");
            Updater=true;}

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Downloading content, please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        ProcessReadrss=true;
        creatBase(feedItems);
        recyclerView.scrollToPosition(0);
        this.progressDialog.dismiss();
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
                Dao<FeedItem, Integer> feedDao = getHelper().getFeedItemDao();
                feedDao.create(feedItem);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            adapter.add(0, feed.get(i));
        }
    }

    private void ProcessXml(Document data) {

        if(data != null){
            feedItems=new ArrayList<>();
            Element root = data.getDocumentElement();
            Node channel = root.getChildNodes().item(1);
            NodeList items=channel.getChildNodes();
            for (int i=0; i<items.getLength();i++){
                Node cureentchild=items.item(i);
                if (cureentchild.getNodeName().equals("item")){
                    BaseStatus=false; //меняет свое значение на true если считываемая запись в базе уже есть
                    FeedItem item=new FeedItem();
                    NodeList itemchilds=cureentchild.getChildNodes();
                    for (int j=0;j<itemchilds.getLength();j++){
                        Log.d("items.getLength()", j+"");
                        Node cureent= itemchilds.item(j);
                        ArrayList<FeedItem> daoList = null;
                        if(cureent.getNodeName().equals("title")){

                            try {
                                feedItemIntegerDao = getHelper().getFeedItemDao();
                                daoList = (ArrayList<FeedItem>) feedItemIntegerDao.queryForEq("title", cureent.getTextContent());
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                            if (daoList.size() > 0) {
                                BaseStatus=true;
                            }

                            if (BaseStatus==false){ //+
                                item.setTitle(cureent.getTextContent());
                            }

                        } else if (cureent.getNodeName().equals("description")&&BaseStatus==false){
                            item.setDescription(cureent.getTextContent());
                        } else if (cureent.getNodeName().equals("pubDate")&&BaseStatus==false){
                            item.setDate(cureent.getTextContent());
                        } else if (cureent.getNodeName().equals("link")&&BaseStatus==false){
                            item.setLink(cureent.getTextContent());
                        } else if (cureent.getNodeName().equals("media:thumbnail")&&BaseStatus==false){
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

                    if(BaseStatus==false){
                        item.setUrl(sAdressURL);
                        feedItems.add(item); //Запись значений в ArrayList
                    }
                }
            }
        }

    }

    public Document Getdata(){
        try {
            url = new URL(sAdressURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
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

package com.hoka.readrssproject.presenter;

import android.os.AsyncTask;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.hoka.readrssproject.model.FeedItem;
import com.hoka.readrssproject.view.IRssSettingsActivity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

@InjectViewState
public class RssSettingsPresenter extends MvpPresenter<IRssSettingsActivity> {
    public void showCheckRss(final String strCheckUrl){
        AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {
            private Boolean mFlag;

            @Override
            protected Void doInBackground(Void... params) {
                if(!isRssOrNot(strCheckUrl)){
                    mFlag = false;
                } else mFlag = true;
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (mFlag){
                    getViewState().showMainActivity();
                }else {
                    getViewState().showErrorMessage();
                }
            }

            public boolean isRssOrNot(String strAddress) {
                Document data;
                data = getData(strAddress);
                Boolean ok = false;
                int count = 0;
                ArrayList<FeedItem> feedItems;
                try {
                    if (data != null) {
                        feedItems = new ArrayList<>();
                        Element root = data.getDocumentElement();
                        Node channel = root.getChildNodes().item(1);
                        NodeList items = channel.getChildNodes();
                        do {
                            for (int i = 0; i < items.getLength(); i++) {
                                Node currentChild = items.item(i);
                                if (currentChild.getNodeName().equalsIgnoreCase("item")) {
                                    FeedItem item = new FeedItem();
                                    NodeList itemChilds = currentChild.getChildNodes();
                                    for (int j = 0; j < itemChilds.getLength(); j++) {
                                        Node current = itemChilds.item(j);
                                        if (current.getNodeName().equalsIgnoreCase("title")) {
                                            item.setTitle(current.getTextContent());
                                        } else if (current.getNodeName().equalsIgnoreCase("description")) {
                                            item.setDescription(current.getTextContent());
                                        } else if (current.getNodeName().equalsIgnoreCase("link")) {
                                            item.setLink(current.getTextContent());
                                        } else if (current.getNodeName().equalsIgnoreCase("author")) {
                                            item.setAuthor(current.getTextContent());
                                        }
                                    }
                                    feedItems.add(item);
                                    if(feedItems.size()>0){
                                        ok = true;
                                    } count++;
                                }
                            }
                        } while (count == 0);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    ok = false;
                }
                return ok;
            }

            public Document getData(String strAddress){
                try{
                    URL url = new URL(strAddress);
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    InputStream inputStream = connection.getInputStream();
                    DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
                    Document xmlDoc = documentBuilder.parse(inputStream);
                    connection.disconnect();
                    return  xmlDoc;
                } catch (Exception exc){
                    exc.printStackTrace();
                    return  null;
                }
            }
        };
        asyncTask.execute();
    }
}

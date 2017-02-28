package com.hoka.readrssproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hoka.readrssproject.BlankDetails;
import com.hoka.readrssproject.DetailsActivity;
import com.hoka.readrssproject.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by hoka on 28.02.2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private TextView text_title, text_description, text_date;
    private ImageView image_thumbnail;
    private CardView cardView;

    private List<FeedItem> mList;
    //переменная gadget отвечает за открытие нового активити если приложение работает на телефоне
    //либо в планшете: false - телефон, true - планшет
    //значение gadget задается в MainActivity, если при загрузке виден фрагмент в layout-large
    //то приложение открывает информацию для просмотра во фрагменте, иначе открытие происходит в телефоне
    //с загрузкой DetailsActivity для предварительного просмотра записи
    public static Boolean mGadget=false;

    public NewsAdapter() {
        mList = new ArrayList<>();
    }

    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //норм
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custum_row_news_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).setItemName(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void add(int location, FeedItem item) {
        mList.add(location, item);
        notifyItemInserted(location);
    }

    public void setAdapterList(List<FeedItem> list) {
        mList = list;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public ViewHolder(View itemView) {
            super(itemView);
            text_title = (TextView) itemView.findViewById(R.id.title_text);
            text_description = (TextView) itemView.findViewById(R.id.description_text);
            text_date = (TextView) itemView.findViewById(R.id.date_text);
            image_thumbnail = (ImageView) itemView.findViewById(R.id.thumb_img);
            cardView = (CardView) itemView.findViewById(R.id.cardview);
            cardView.setOnClickListener(this);
        }

        public Locale getCurrentLocale(Context context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return context.getResources().getConfiguration().getLocales().get(0);
            } else {
                return context.getResources().getConfiguration().locale;
            }
        }



        public void setItemName(final FeedItem feed) {
            text_title.setText(feed.getTitle());
            //Description.setText(feed.getDescription());
            try{
                text_description.setText(Html.fromHtml(feed.getDescription()));
            } catch (NullPointerException e){
                text_description.setText("");
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, dd MMM yyy HH:mm:ss zzz",Locale.ENGLISH);
            try {
                java.util.Date date = simpleDateFormat.parse(feed.getDate());
                SimpleDateFormat print;
                if (getCurrentLocale(itemView.getContext())==Locale.ENGLISH) {
                    print = new SimpleDateFormat("E, dd MMM yyy HH:mm a",Locale.ENGLISH);
                } else {
                    print = new SimpleDateFormat("E, dd MMM yyy HH:mm z",getCurrentLocale(itemView.getContext()));
                }
                text_date.setText(print.format(date));

            } catch (ParseException exc){
                exc.printStackTrace();
                text_date.setText(feed.getDate());
            }


            //Date.setText(feed.getPubDate());
            try{
                image_thumbnail.setImageBitmap(BitmapFactory.decodeByteArray(feed.getImage(),0,feed.getImage().length));
            }
            catch (Exception e){
                image_thumbnail.setImageResource(R.drawable.news);
            }

            final NewsAdapter.ViewHolder holder = new ViewHolder(itemView);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mGadget == true) {
                        BlankDetails blankDetails = new BlankDetails();
                        blankDetails.setTitle(feed.getTitle());
                        blankDetails.setDescription(feed.getDescription());
                        blankDetails.setDate(feed.getDate());
                        blankDetails.setLink(feed.getLink());
                        try {
                            blankDetails.setThumbnail(BitmapFactory.decodeByteArray(feed.getImage(), 0, feed.getImage().length));
                        } catch (Exception e) {
                            blankDetails.setThumbnail(BitmapFactory.decodeResource(itemView.getContext().getResources(), R.drawable.news));
                        }
                        android.support.v4.app.FragmentManager ft = ((AppCompatActivity) itemView.getContext()).getSupportFragmentManager();
                        ft.beginTransaction().replace(R.id.fragmentBlankDetails, blankDetails).commit();
                    } else {
                        Intent intent = new Intent(itemView.getContext(), DetailsActivity.class);
                        intent.putExtra("DelaisActivity", feed);
                        itemView.getContext().startActivity(intent);
                    }
                }
            });
        }

        @Override
        public void onClick(View view) {
        }

    }
}

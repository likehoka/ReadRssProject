package com.hoka.readrssproject.adapter;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hoka.readrssproject.BlankDetails;
import com.hoka.readrssproject.DetailsActivity;
import com.hoka.readrssproject.R;
import com.hoka.readrssproject.model.FeedItem;
import com.hoka.readrssproject.utils.HelpLocaleFunction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hoka on 28.02.2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<FeedItem> mList;
    //переменная gadget отвечает за открытие нового активити если приложение работает на телефоне
    //либо в планшете: false - телефон, true - планшет
    //значение sGadget задается в MainActivity, если при загрузке виден фрагмент в layout-large
    //то приложение открывает информацию для просмотра во фрагменте, иначе открытие происходит в телефоне
    //с загрузкой DetailsActivity для предварительного просмотра записи
    public static Boolean sGadget = false;

    public NewsAdapter() {
        mList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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

        @BindView(R.id.textview_custum_row_news_item_date_text)
        TextView textDate;
        @BindView(R.id.imageview_custum_row_news_item_thumb_img)
        ImageView imageThumb;
        @BindView(R.id.textview_custum_row_news_item_title_text)
        TextView textTitle;
        @BindView(R.id.cardview_custum_row_news_item)
        CardView cardview;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setItemName(final FeedItem feed) {
            textTitle.setText(feed.getTitle());

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, dd MMM yyy HH:mm:ss zzz", Locale.ENGLISH);
            try {
                Date date = simpleDateFormat.parse(feed.getDate());
                HelpLocaleFunction mHelpLocaleFunction = new HelpLocaleFunction(itemView.getContext());
                simpleDateFormat = new SimpleDateFormat("E, dd MMM yyy HH:mm", mHelpLocaleFunction.HelpLocaleFunction());
                textDate.setText(simpleDateFormat.format(date));
            } catch (ParseException exc) {
                exc.printStackTrace();
                textDate.setText(feed.getDate());
            }
            try {
                imageThumb.setImageBitmap(BitmapFactory.decodeByteArray(feed.getImage(), 0, feed.getImage().length));
            } catch (Exception e) {
                imageThumb.setImageResource(R.drawable.news);
            }

            cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sGadget) {
                        BlankDetails blankDetails = new BlankDetails();
                        blankDetails.setTitle(feed.getTitle());
                        blankDetails.setDescription(feed.getDescription());
                        blankDetails.setDate(feed.getDate());
                        blankDetails.setLink(feed.getLink());
                        blankDetails.setAuthor(feed.getAuthor());
                        try {
                            blankDetails.setThumbnail(BitmapFactory.decodeByteArray(feed.getImage(), 0, feed.getImage().length));
                        } catch (Exception e) {
                            blankDetails.setThumbnail(BitmapFactory.decodeResource(itemView.getContext().getResources(), R.drawable.news));
                        }
                        FragmentManager ft = ((AppCompatActivity) itemView.getContext()).getSupportFragmentManager();
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

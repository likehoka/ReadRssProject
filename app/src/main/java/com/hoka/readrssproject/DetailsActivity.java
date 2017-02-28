package com.hoka.readrssproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.hoka.readrssproject.View.IDetailsActivity;
import com.hoka.readrssproject.model.FeedItem;
import com.hoka.readrssproject.presenter.DetailsActivityPresenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends MvpAppCompatActivity implements IDetailsActivity {
    @InjectPresenter
    DetailsActivityPresenter mDetailsActivityPresenter;

    //TextView mTitle, mDescription, mDate, mLink;
    //ImageView mThumbnail;
    //ScrollView mScrollView;
    @BindView(R.id.details_thumb_img)
    ImageView detailsThumbImg;
    @BindView(R.id.details_date_text)
    TextView detailsDateText;
    @BindView(R.id.details_title_text)
    TextView detailsTitleText;
    @BindView(R.id.details_description_text)
    TextView detailsDescriptionText;
    @BindView(R.id.details_scrollView)
    ScrollView detailsScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        final FeedItem mFeedItem = (FeedItem) getIntent().getSerializableExtra("DelaisActivity");
        //mTitle = (TextView) findViewById(R.id.details_title_text);
        //mDescription = (TextView) findViewById(R.id.details_description_text);
        //mDate = (TextView) findViewById(R.id.details_date_text);
        //mScrollView = (ScrollView) findViewById(R.id.details_scrollView);
        //mThumbnail = (ImageView) findViewById(R.id.details_thumb_img);

        mDetailsActivityPresenter.showDetails(mFeedItem);
    }

    public Locale getCurrentLocale(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return context.getResources().getConfiguration().getLocales().get(0);
        } else {
            return context.getResources().getConfiguration().locale;
        }
    }

    @Override
    public void showDetails(final FeedItem mFeedItem) {

        detailsTitleText.setText(mFeedItem.getTitle());
        detailsDescriptionText.setText(Html.fromHtml(mFeedItem.getDescription()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, dd MMM yyy HH:mm:ss zzz", Locale.ENGLISH);
        try {
            Date date = simpleDateFormat.parse(mFeedItem.getDate().toString());
            SimpleDateFormat print;

            if (getCurrentLocale(this) == Locale.ENGLISH) {
                print = new SimpleDateFormat("E, dd MMM yyy HH:mm a", Locale.ENGLISH);
            } else {
                print = new SimpleDateFormat("E, dd MMM yyy HH:mm z", getCurrentLocale(this));
            }
            detailsDateText.setText(print.format(date));
        } catch (ParseException exc) {
            exc.printStackTrace();
            detailsDateText.setText(mFeedItem.getDate().toString());
        }
        try {
            detailsThumbImg.setImageBitmap(BitmapFactory.decodeByteArray(mFeedItem.getImage(), 0, mFeedItem.getImage().length));
        } catch (Exception e) {
            detailsThumbImg.setImageResource(R.drawable.news);
        }

        detailsThumbImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsActivity.this, NewsDetails.class);
                intent.putExtra("Link", mFeedItem.getLink().toString());
                startActivity(intent);
            }
        });
    }
}

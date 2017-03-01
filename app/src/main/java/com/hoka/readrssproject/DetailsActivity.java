package com.hoka.readrssproject;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.hoka.readrssproject.view.IDetailsActivity;
import com.hoka.readrssproject.model.FeedItem;
import com.hoka.readrssproject.presenter.DetailsActivityPresenter;
import com.hoka.readrssproject.utils.HelpLocaleFunction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends MvpAppCompatActivity implements IDetailsActivity {
    @InjectPresenter
    DetailsActivityPresenter mDetailsActivityPresenter;

    @BindView(R.id.imageview_activity_details_thumb_img)
    ImageView imageThumbImg;
    @BindView(R.id.textview_activity_details_date_text)
    TextView textDate;
    @BindView(R.id.textview_activity_details_title_text)
    TextView textTitle;
    @BindView(R.id.textview_activity_details_description_text)
    TextView texDescription;
    @BindView(R.id.scrollView_activity_details)
    ScrollView scrollViewActivityDetails;
    @BindView(R.id.textview_activity_details_author_text)
    TextView textAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        final FeedItem mFeedItem = (FeedItem) getIntent().getSerializableExtra("DelaisActivity");

        mDetailsActivityPresenter.showDetails(mFeedItem);
    }

    @Override
    public void showDetails(final FeedItem mFeedItem) {

        textTitle.setText(mFeedItem.getTitle());
        if (mFeedItem.getAuthor() != null){
            textAuthor.setText(R.string.author + ": " + mFeedItem.getAuthor());
        }

        texDescription.setText(Html.fromHtml(mFeedItem.getDescription()));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, dd MMM yyy HH:mm:ss zzz", Locale.ENGLISH);
        try {
            Date date = simpleDateFormat.parse(mFeedItem.getDate());
            HelpLocaleFunction mHelpLocaleFunction = new HelpLocaleFunction(this);
            simpleDateFormat = new SimpleDateFormat("E, dd MMM yyy HH:mm", mHelpLocaleFunction.HelpLocaleFunction());
            textDate.setText(simpleDateFormat.format(date));
        } catch (ParseException exc) {
            exc.printStackTrace();
            textDate.setText(mFeedItem.getDate());
        }
        try {
            imageThumbImg.setImageBitmap(BitmapFactory.decodeByteArray(mFeedItem.getImage(), 0, mFeedItem.getImage().length));
        } catch (Exception e) {
            imageThumbImg.setImageResource(R.drawable.news);
        }

        imageThumbImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsActivity.this, NewsDetails.class);
                intent.putExtra("Link", mFeedItem.getLink().toString());
                startActivity(intent);
            }
        });
    }
}

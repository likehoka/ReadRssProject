package com.hoka.readrssproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends Activity {
    //***************
    //Элементы заполнения View элементов экрана
    @BindView(R.id.details_thumb_img)
    ImageView detailsThumbImg;
    @BindView(R.id.details_date_text)
    TextView detailsDateText;
    @BindView(R.id.details_title_text)
    TextView detailsTitleText;
    @BindView(R.id.details_description_text)
    TextView detailsDescriptionText;
    //***************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        final Bundle bundle = getIntent().getExtras();
        detailsTitleText.setText(bundle.getString("Title"));
        detailsDescriptionText.setText(Html.fromHtml(bundle.getString("Description")));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, dd MMM yyy HH:mm:ss zzz", Locale.ENGLISH);
        try {
            Date date = simpleDateFormat.parse(bundle.getString("Date"));
            SimpleDateFormat print;

            if (getCurrentLocale(this) == Locale.ENGLISH) {
                print = new SimpleDateFormat("E, dd MMM yyy HH:mm a", Locale.ENGLISH);
            } else {
                print = new SimpleDateFormat("E, dd MMM yyy HH:mm z", getCurrentLocale(this));
            }
            detailsDateText.setText(print.format(date));
        } catch (ParseException exc) {
            exc.printStackTrace();
            detailsDateText.setText(bundle.getString("Date"));
        }


        //Date.setText(bundle.getString("Date"));
        detailsThumbImg = (ImageView) findViewById(R.id.details_thumb_img);
        try {
            detailsThumbImg.setImageBitmap(BitmapFactory.decodeByteArray(bundle.getByteArray("Thumbnail"), 0, bundle.getByteArray("Thumbnail").length));
        } catch (Exception e) {
            detailsThumbImg.setImageResource(R.drawable.news);
        }

        detailsThumbImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsActivity.this, NewsDetails.class);
                intent.putExtra("Link", bundle.getString("Link"));
                startActivity(intent);
            }
        });
    }

    public Locale getCurrentLocale(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return context.getResources().getConfiguration().getLocales().get(0);
        } else {
            return context.getResources().getConfiguration().locale;
        }
    }
}

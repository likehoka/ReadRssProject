package com.hoka.readrssproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BlankDetails extends Fragment {
    //TextView Title, Description, DateLocal;
    //ImageView Thumbnail;
    //Элементы передачи входных данных из MainActivityFragment
    //Используются только для планшетов
    private Bitmap thumbnail;
    String title, description, datelocal, link;
    @BindView(R.id.frag_date_text)
    TextView fragDateText;
    @BindView(R.id.frag_thumb_img)
    ImageView fragThumbImg;
    @BindView(R.id.frag_description_text)
    TextView fragDescriptionText;
    @BindView(R.id.frag_title_text)
    TextView fragTitleText;
    @BindView(R.id.blankdetails_scrollview)
    ScrollView blankdetailsScrollview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        View view = inflater.inflate(R.layout.fragment_blank_details, container, false);
        ButterKnife.bind(this, view);
        fragTitleText.setText(title);
        fragDescriptionText.setText(Html.fromHtml(description));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, dd MMM yyy HH:mm:ss zzz", Locale.ENGLISH);
        try {
            Date date = simpleDateFormat.parse(datelocal);
            SimpleDateFormat print;

            if (getCurrentLocale(getContext()) == Locale.ENGLISH) {
                print = new SimpleDateFormat("E, dd MMM yyy HH:mm a", Locale.ENGLISH);
            } else {
                print = new SimpleDateFormat("E, dd MMM yyy HH:mm z", getCurrentLocale(getContext()));
            }
            fragDateText.setText(print.format(date));
        } catch (ParseException exc) {
            exc.printStackTrace();
            fragDateText.setText(datelocal);
        }

        fragThumbImg.setImageBitmap(thumbnail);
        fragThumbImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NewsDetails.class);
                intent.putExtra("Link", link);
                startActivity(intent);
            }
        });

        return view;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.datelocal = date;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Locale getCurrentLocale(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return context.getResources().getConfiguration().getLocales().get(0);
        } else {
            return context.getResources().getConfiguration().locale;
        }
    }


}
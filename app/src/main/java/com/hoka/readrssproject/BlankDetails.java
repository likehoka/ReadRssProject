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

    //Элементы передачи входных данных из MainActivityFragment
    //Используются только для планшетов
    private Bitmap mThumbnail;
    @BindView(R.id.textview_fragment_blank_details_date)
    TextView textDate;
    @BindView(R.id.image_fragment_blank_details_thumb_img)
    ImageView imageThumbImg;
    @BindView(R.id.textview_fragment_blank_details_description)
    TextView textDescription;
    @BindView(R.id.textview_fragment_blank_details_title)
    TextView textTitle;
    @BindView(R.id.blankdetails_scrollview)
    ScrollView blankdetailsScrollview;


    private String strTitle;
    private String strDescription;
    private String strDatelocal;
    private String strLink;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        View view = inflater.inflate(R.layout.fragment_blank_details, container, false);
        ButterKnife.bind(this, view);
        textTitle.setText(strTitle);
        textDescription.setText(Html.fromHtml(strDescription));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, dd MMM yyy HH:mm:ss zzz", Locale.ENGLISH);
        try {
            Date date = simpleDateFormat.parse(strDatelocal);
            SimpleDateFormat print;

            if (getCurrentLocale(getContext()) == Locale.ENGLISH) {
                print = new SimpleDateFormat("E, dd MMM yyy HH:mm a", Locale.ENGLISH);
            } else {
                print = new SimpleDateFormat("E, dd MMM yyy HH:mm z", getCurrentLocale(getContext()));
            }
            textDate.setText(print.format(date));
        } catch (ParseException exc) {
            exc.printStackTrace();
            textDate.setText(strDatelocal);
        }

        imageThumbImg.setImageBitmap(mThumbnail);
        imageThumbImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NewsDetails.class);
                intent.putExtra("Link", strLink);
                startActivity(intent);
            }
        });

        return view;
    }

    public void setTitle(String title) {
        this.strTitle = title;
    }

    public void setDescription(String description) {
        this.strDescription = description;
    }

    public void setDate(String date) {
        this.strDatelocal = date;
    }

    public void setLink(String link) {
        this.strLink = link;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.mThumbnail = thumbnail;
    }

    public Locale getCurrentLocale(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return context.getResources().getConfiguration().getLocales().get(0);
        } else {
            return context.getResources().getConfiguration().locale;
        }
    }


}
package com.hoka.readrssproject.utils;

import android.content.Context;
import android.os.Build;

import java.util.Locale;

    /*
    get the selected language of your device, this might help
     */

public class HelpLocaleFunction {
    private Context mContext;

    public HelpLocaleFunction(Context context) {
        this.mContext = context;
    }

    public Locale HelpLocaleFunction() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return mContext.getResources().getConfiguration().getLocales().get(0);
        } else {
            return mContext.getResources().getConfiguration().locale;
        }
    }
}

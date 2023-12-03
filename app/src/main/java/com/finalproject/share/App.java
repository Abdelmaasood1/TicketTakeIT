package com.finalproject.share;


import android.content.Context;

import androidx.multidex.MultiDexApplication;

import com.finalproject.language.Language;


public class App extends MultiDexApplication {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(Language.updateResources(newBase, "en"));
    }


    @Override
    public void onCreate() {
        super.onCreate();
        com.finalproject.share.TypefaceUtil.setDefaultFont(this, "DEFAULT", "fonts/ar_font.ttf");
        com.finalproject.share.TypefaceUtil.setDefaultFont(this, "MONOSPACE", "fonts/ar_font.ttf");
        com.finalproject.share.TypefaceUtil.setDefaultFont(this, "SERIF", "fonts/ar_font.ttf");
        com.finalproject.share.TypefaceUtil.setDefaultFont(this, "SANS_SERIF", "fonts/ar_font.ttf");

    }
}


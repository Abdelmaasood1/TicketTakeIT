package com.finalproject.ui.user.activity_noification;


import android.content.Context;
import android.os.Bundle;

import com.finalproject.R;
import com.finalproject.language.Language;
import com.finalproject.ui.activity_base.BaseActivity;

public class NotificationActivity extends BaseActivity {
    private String lang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
    }
}
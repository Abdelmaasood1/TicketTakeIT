package com.finalproject.ui.activity_language;

import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.finalproject.R;
import com.finalproject.databinding.ActivityLanguageBinding;
import com.finalproject.ui.activity_base.BaseActivity;

public class LanguageActivity extends BaseActivity {
    private String lang;
    private ActivityLanguageBinding binding;
    private String selectedLang;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_language);
        intView();
    }

    private void intView() {
        lang = getLang();
        binding.setLang(getLang());
        Log.e("lang",lang);

        ProgressDialog dialog = new ProgressDialog(this);
        //decorate the saved language
        if (lang.equals("en")) {
            binding.flEn.setBackgroundResource(R.drawable.small_stroke_primary2);
//            binding.enTxt.setTextColor(getResources().getColor(R.color.color2));
//            binding.arTxt.setTextColor(getResources().getColor(R.color.white));
            binding.flAr.setBackgroundResource(0);
        } else if (lang.equals("ar")) {
            binding.flAr.setBackgroundResource(R.drawable.small_stroke_primary2);
//            binding.arTxt.setTextColor(getResources().getColor(R.color.color2));
//            binding.enTxt.setTextColor(getResources().getColor(R.color.white));
            binding.flEn.setBackgroundResource(0);
        } else {
            Toast.makeText(this, "no selected language", Toast.LENGTH_SHORT).show();
        }

        binding.cardAr.setOnClickListener(view -> {
            selectedLang = "ar";
            if (!selectedLang.equals(lang)) {
                binding.llNext.setVisibility(View.VISIBLE);
            } else {
                binding.llNext.setVisibility(View.INVISIBLE);
            }

            binding.flAr.setBackgroundResource(R.drawable.small_stroke_primary2);
//            binding.arTxt.setTextColor(getResources().getColor(R.color.color2));
//            binding.enTxt.setTextColor(getResources().getColor(R.color.white));
            binding.flEn.setBackgroundResource(0);
        });
        binding.cardEn.setOnClickListener(view -> {
            selectedLang = "en";
            if (!selectedLang.equals(lang)) {
                binding.llNext.setVisibility(View.VISIBLE);
            } else {
                binding.llNext.setVisibility(View.INVISIBLE);
            }
            binding.flEn.setBackgroundResource(R.drawable.small_stroke_primary2);
//            binding.enTxt.setTextColor(getResources().getColor(R.color.color2));
//            binding.arTxt.setTextColor(getResources().getColor(R.color.white));
            binding.flAr.setBackgroundResource(0);
        });
        binding.llCancel.setOnClickListener(view -> finish());
        binding.llNext.setOnClickListener(view -> {
            Intent intent = getIntent();
            intent.putExtra("lang", selectedLang);
            setResult(RESULT_OK, intent);
            finish();

        });

    }
}

package com.finalproject.ui.user.activity_coming_soon;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.finalproject.R;
import com.finalproject.adapter.ComingSoonDetailsAdapter;
import com.finalproject.databinding.ActivityComingSoonBinding;
import com.finalproject.language.Language;
import com.finalproject.mvvm.ActivityComingSoonMVVM;
import com.finalproject.mvvm.FragmentHomeMVVM;
import com.finalproject.ui.activity_base.BaseActivity;

import java.util.Locale;

import io.paperdb.Paper;

public class ComingSoonActivity extends BaseActivity {
    private String lang;
    private ComingSoonActivity activity;
    private ActivityComingSoonBinding binding;
    private ComingSoonDetailsAdapter mallComingSoonDetailsAdapter;
    private ActivityComingSoonMVVM mvvm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_coming_soon);
        initView();
    }


    private void initView() {
        setUpToolbar(binding.toolbar, getString(R.string.comingSoon), R.color.color2, R.color.white);
        mvvm = ViewModelProviders.of(this).get(ActivityComingSoonMVVM.class);
        mvvm.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading) {
                    binding.progBarComingSoon1.setVisibility(View.VISIBLE);
                }
            }
        });



//        Paper.init(this);
//        lang = getLang();
//        binding.setLang(getLang());

        mallComingSoonDetailsAdapter = new ComingSoonDetailsAdapter(this);
        binding.comingRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.comingRecycler.setAdapter(mallComingSoonDetailsAdapter);
        mvvm.getComingSoon().observe(this, comingSoonModels -> {
            if (comingSoonModels.size()>0){
                binding.progBarComingSoon1.setVisibility(View.GONE);
                binding.tvNoComingSoon.setVisibility(View.GONE);
                mallComingSoonDetailsAdapter.updateList(comingSoonModels);
            }else {
                binding.tvNoComingSoon.setVisibility(View.VISIBLE);
            }
        });



        binding.toolbar.llBack.setOnClickListener(view -> {finish();});

        mvvm.getComingSoonData(activity);
    }
}
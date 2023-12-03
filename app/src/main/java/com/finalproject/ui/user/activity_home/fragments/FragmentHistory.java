package com.finalproject.ui.user.activity_home.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.finalproject.R;


import com.finalproject.adapter.HistoryAdapter;
import com.finalproject.databinding.FragmentHistoryBinding;
import com.finalproject.ui.activity_base.BaseFragment;
import com.finalproject.ui.user.activity_home.HomeActivity;


public class FragmentHistory extends BaseFragment {
    private HomeActivity activity;
    private FragmentHistoryBinding binding;
    private HistoryAdapter historyAdapter;


    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        historyAdapter = new HistoryAdapter(activity, this);
        binding.recyclerHistory.setLayoutManager(new LinearLayoutManager(activity));
        binding.recyclerHistory.setAdapter(historyAdapter);
    }
}




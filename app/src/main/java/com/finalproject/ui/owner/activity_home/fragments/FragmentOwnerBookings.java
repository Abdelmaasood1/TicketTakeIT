package com.finalproject.ui.owner.activity_home.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.finalproject.R;
import com.finalproject.adapter.OwnerHistoryAdapter;
import com.finalproject.databinding.FragmentOwnerBookingsBinding;
import com.finalproject.ui.activity_base.BaseFragment;
import com.finalproject.ui.owner.activity_home.OwnerHomeActivity;


public class FragmentOwnerBookings extends BaseFragment {
    private OwnerHomeActivity activity;
    private FragmentOwnerBookingsBinding binding;
    private OwnerHistoryAdapter historyAdapter;

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (OwnerHomeActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_owner_bookings, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }
    private void initView() {
        historyAdapter = new OwnerHistoryAdapter(activity, this);
        binding.recyclerHistory.setLayoutManager(new LinearLayoutManager(activity));
        binding.recyclerHistory.setAdapter(historyAdapter);
    }
}
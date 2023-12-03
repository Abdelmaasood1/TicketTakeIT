package com.finalproject.ui.user.activity_home.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.finalproject.R;
import com.finalproject.adapter.SearchItemAdapter;
import com.finalproject.databinding.FragmentSearchBinding;
import com.finalproject.model.MovieModel;
import com.finalproject.mvvm.FragmentSearchMvvm;
import com.finalproject.share.Common;
import com.finalproject.ui.activity_base.BaseFragment;
import com.finalproject.ui.user.activity_home.HomeActivity;
import com.finalproject.ui.user.activity_movie_details.MovieDetailsActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;


public class FragmentSearch extends BaseFragment {
    private static final String TAG = FragmentSearch.class.getName();
    private HomeActivity activity;
    private FragmentSearchBinding binding;
    private FragmentSearchMvvm fragmentSearchMvvm;
    private SearchItemAdapter adapter;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

    }

    private void initView() {
        fragmentSearchMvvm = ViewModelProviders.of(this).get(FragmentSearchMvvm.class);

        fragmentSearchMvvm.getIsLoading().observe(activity, isLoading -> {
            binding.swipeRef.setRefreshing(isLoading);
            if (isLoading) {
                adapter.updateList(null);
            }
        });

        fragmentSearchMvvm.getOnMoviesSuccess().observe(activity, movieModels -> {
            if (movieModels.size() > 0) {
                binding.cardNoData.setVisibility(View.GONE);
            } else {
                binding.cardNoData.setVisibility(View.VISIBLE);
            }
            if (adapter != null) {
                adapter.updateList(movieModels);
            }
        });
        adapter = new SearchItemAdapter(activity, this);
        binding.recyclerMovies.setLayoutManager(new GridLayoutManager(activity, 2));
        binding.recyclerMovies.setAdapter(adapter);
//        fragmentSearchMvvm.getMovies(null);

        binding.swipeRef.setOnRefreshListener(() -> fragmentSearchMvvm.getMovies(binding.edtSearch.getText().toString()));

        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                fragmentSearchMvvm.getMovies(s.toString());
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Common.CloseKeyBoard(activity, binding.edtSearch);
        disposable.clear();
    }

    public void navigateToMovieDetails(MovieModel movieModel) {
        Intent intent = new Intent(activity, MovieDetailsActivity.class);
        intent.putExtra("movie_id", movieModel.getId());
        startActivity(intent);
    }
}
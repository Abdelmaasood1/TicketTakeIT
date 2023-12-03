package com.finalproject.ui.user.activity_home.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.finalproject.R;
import com.finalproject.adapter.ComingSoonAdapter;
import com.finalproject.adapter.SliderAdapter;
import com.finalproject.adapter.TopMoviesAdapter;
import com.finalproject.adapter.TopShowsAdapter;

import com.finalproject.databinding.FragmentHomeBinding;
import com.finalproject.model.MovieModel;
import com.finalproject.model.ShowModel;
import com.finalproject.model.SliderModel;
import com.finalproject.mvvm.FragmentHomeMVVM;
import com.finalproject.ui.activity_base.BaseFragment;
import com.finalproject.ui.user.activity_coming_soon.ComingSoonActivity;
import com.finalproject.ui.user.activity_home.HomeActivity;
import com.finalproject.ui.user.activity_show_details.ShowDetailsActivity;
import com.finalproject.ui.user.activity_movie_details.MovieDetailsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FragmentHome extends BaseFragment {
    private HomeActivity activity;
    private FragmentHomeBinding binding;
    private TopMoviesAdapter topMoviesAdapter;
    private ComingSoonAdapter comingSoonAdapter;
    private TopShowsAdapter topShowsAdapter;
    private FragmentHomeMVVM mvvm;

    private Timer timer;

    private SliderAdapter sliderAdapter;
    private List<SliderModel> sliderModelList;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        sliderModelList = new ArrayList<>();
        mvvm = ViewModelProviders.of(this).get(FragmentHomeMVVM.class);

        mvvm.getIsLoading().observe(activity, isLoading -> {
            if (isLoading) {
                binding.progBarSlider.setVisibility(View.VISIBLE);
                binding.progBarTopPicked.setVisibility(View.VISIBLE);
                binding.progBarTopShow.setVisibility(View.VISIBLE);
                binding.progBarComingSoon.setVisibility(View.VISIBLE);

            }
        });

        mvvm.getSliderDataModelMutableLiveData().observe(activity, sliderDataModel -> {
            if (sliderDataModel.getSlider()!=null){
                binding.progBarSlider.setVisibility(View.GONE);
                sliderModelList.clear();
                sliderModelList.addAll(sliderDataModel.getSlider());
                sliderAdapter.notifyDataSetChanged();
                timer = new Timer();
                timer.scheduleAtFixedRate(new MyTask(), 3000, 3000);
            }
        });

        topMoviesAdapter = new TopMoviesAdapter(activity, this);
        binding.recyclerTopPicked.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
        binding.recyclerTopPicked.setAdapter(topMoviesAdapter);
        mvvm.getMovies().observe(activity, movieModels -> {
            if (movieModels.size() > 0) {
                binding.progBarTopPicked.setVisibility(View.GONE);
                topMoviesAdapter.updateList(movieModels);
                binding.tvNoMovies.setVisibility(View.GONE);
            } else {
                binding.tvNoMovies.setVisibility(View.VISIBLE);
            }
        });

        topShowsAdapter = new TopShowsAdapter(activity, this);
        binding.recyclerTopShow.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
        binding.recyclerTopShow.setAdapter(topShowsAdapter);
        mvvm.getShows().observe(activity, showModels -> {
            if (showModels.size()>0){
                binding.progBarTopShow.setVisibility(View.GONE);
                binding.tvNoShows.setVisibility(View.GONE);
                topShowsAdapter.updateList(showModels);
            }else {
                binding.tvNoShows.setVisibility(View.VISIBLE);
            }
        });

        comingSoonAdapter = new ComingSoonAdapter(activity, this);
        binding.recyclerComingSoon.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
        binding.recyclerComingSoon.setAdapter(comingSoonAdapter);
        mvvm.getComingSoon().observe(activity, comingSoonModels -> {
            if (comingSoonModels.size()>0){
                binding.progBarComingSoon.setVisibility(View.GONE);
                binding.tvNoComingSoon.setVisibility(View.GONE);
                comingSoonAdapter.updateList(comingSoonModels);
            }else {
                binding.tvNoComingSoon.setVisibility(View.VISIBLE);
            }
        });
      
        sliderAdapter = new SliderAdapter(sliderModelList,activity);
        binding.pager.setAdapter(sliderAdapter);
        binding.pager.setClipToPadding(false);
        binding.pager.setPadding(20, 0, 20, 0);
        binding.pager.setPageMargin(20);
        mvvm.getSlider();
        binding.tab.setupWithViewPager(binding.pager);

        binding.seeComingSoon.setPaintFlags(binding.seeComingSoon.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        binding.seeComingSoon.setOnClickListener(view -> {
            Intent i = new Intent(getContext(), ComingSoonActivity.class);
            startActivity(i);
        });

        mvvm.getHomeData(activity);

        binding.llSearch.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.fragmentSearch);
        });
    }

    public void navigateToMovieDetailsActivity(MovieModel movieModel, int adapterPosition) {
        Intent intent = new Intent(activity, MovieDetailsActivity.class);
        intent.putExtra("movie_id",movieModel.getId());
        startActivity(intent);
    }

    public void navigateToShowDetailsActivity(ShowModel showModel, int adapterPosition) {
        Intent intent = new Intent(activity, ShowDetailsActivity.class);
        intent.putExtra("show_id",showModel.getId());
        startActivity(intent);
    }

    public class MyTask extends TimerTask {
        @Override
        public void run() {
            activity.runOnUiThread(() -> {
                int current_page = binding.pager.getCurrentItem();
                if (current_page < sliderAdapter.getCount() - 1) {
                    binding.pager.setCurrentItem(binding.pager.getCurrentItem() + 1);
                } else {
                    binding.pager.setCurrentItem(0);

                }
            });

        }

    }

}
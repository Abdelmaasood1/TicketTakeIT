package com.finalproject.ui.user.activity_cinema_users;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.finalproject.R;
import com.finalproject.adapter.CinemaUsersAdapter;

import com.finalproject.databinding.ActivityCinemasUserBinding;
import com.finalproject.model.CinemaModel;
import com.finalproject.model.MovieModel;
import com.finalproject.model.ShowModel;
import com.finalproject.mvvm.ActivityCinemasMvvm;
import com.finalproject.mvvm.FragmentMoviesMvvm;
import com.finalproject.ui.activity_base.BaseActivity;
import com.finalproject.ui.user.activity_booking_seats.BookingSeatsActivity;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class CinemasUserActivity extends BaseActivity {
    private String lang;
    private CinemaUsersAdapter cinemaUsersAdapter;
    private ActivityCinemasUserBinding binding;
    private ActivityCinemasMvvm mvvm;
    private String id;
    private MovieModel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cinemas_user);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        model= (MovieModel) intent.getSerializableExtra("movieModel");
    }
    private void initView() {
        setUpToolbar(binding.toolbar, getString(R.string.choose_Cinema), R.color.color2, R.color.white);
        binding.toolbar.llBack.setOnClickListener(view -> finish());
        mvvm = ViewModelProviders.of(this).get(ActivityCinemasMvvm.class);

        mvvm.getIsLoading().observe(this, isLoading -> {
            binding.swipeRef.setRefreshing(isLoading);
        });

        mvvm.getOnCinemaSuccess().observe(this, cinemaModels -> {
            if (cinemaModels.size()>0){
                binding.cardNoData.setVisibility(View.GONE);
                if (cinemaUsersAdapter!=null){
                    cinemaUsersAdapter.updateList(cinemaModels);
                }
            }else {
                binding.cardNoData.setVisibility(View.VISIBLE);
            }
        });
        mvvm.getCinemas();

        binding.swipeRef.setOnRefreshListener(() -> {
                mvvm.getCinemas();
        });
        cinemaUsersAdapter = new CinemaUsersAdapter(this,getLang());
        binding.recViewCinemas.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.recViewCinemas.setAdapter(cinemaUsersAdapter);
    }


    public void navigateToBookingActivity(CinemaModel cinemaModel, int position) {
        Intent intent = new Intent(CinemasUserActivity.this, BookingSeatsActivity.class);
        intent.putExtra("movieModel",model);
        intent.putExtra("cinemaModel",cinemaModel);
        startActivity(intent);
    }
}
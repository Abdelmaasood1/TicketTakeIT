package com.finalproject.ui.user.activity_show_details;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.finalproject.R;
import com.finalproject.adapter.CastShowAdapter;
import com.finalproject.databinding.ActivityShowDetilesBinding;
import com.finalproject.model.ShowModel;
import com.finalproject.mvvm.ActivityDetailsMvvm;
import com.finalproject.ui.activity_base.BaseActivity;
import com.finalproject.ui.user.activity_booking_seats.BookingSeatsActivity;
import com.finalproject.ui.user.activity_cinema_users.CinemasUserActivity;
import com.finalproject.ui.user.activity_cinema_users.CinemasUserShowActivity;
import com.google.android.exoplayer2.util.Log;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShowDetailsActivity extends BaseActivity {
    private ActivityShowDetilesBinding binding;
    private CastShowAdapter castShowAdapter;
    private ShowModel model;
    private String id;
    private ActivityDetailsMvvm mvvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_detiles);
        getDataFromIntent();
        initView();
    }
    private void getDataFromIntent() {
        Intent intent = getIntent();
        id = (String) intent.getSerializableExtra("show_id");
    }

    private void initView() {
        setUpToolbar(binding.toolbar, getString(R.string.show_details), R.color.color2, R.color.white);
        binding.toolbar.llBack.setOnClickListener(view -> finish());
        mvvm = ViewModelProviders.of(this).get(ActivityDetailsMvvm.class);

        binding.setLang(getLang());
        model=new ShowModel();
        binding.setModel(model);

        mvvm.getIsLoading().observe(this, isLoading -> {
            if (isLoading){
                binding.loader.setVisibility(View.VISIBLE);
                binding.loader.startShimmer();
                binding.constraint.setVisibility(View.GONE);
            }else {
                binding.loader.setVisibility(View.GONE);
                binding.loader.stopShimmer();
                binding.constraint.setVisibility(View.VISIBLE);
            }
        });
        mvvm.getOnShowDataSuccess().observe(this, showModel -> {
            model=showModel;
            binding.setModel(model);
            if (model!=null){
                if (castShowAdapter!=null){
                    castShowAdapter.updateList(showModel.getHeroes());
                }

            }
        });
        mvvm.getOnShowDataSuccess().observe(this, showModel -> {
            model=showModel;
            binding.setModel(model);
            if (model!=null){
                if (model.getVideo()!=null){
//                    getVideoImage();
//                    setupPlayer();
                    setUpYoutube(this, model.getVideo());
                }
                if (castShowAdapter!=null){
                    castShowAdapter.updateList(showModel.getHeroes());
                }
            }
        });
        mvvm.getShowDetails(id);

        castShowAdapter = new CastShowAdapter(this);
        binding.recViewCast.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.recViewCast.setAdapter(castShowAdapter);

        binding.btnChooseCinema.setOnClickListener(view -> {
            Intent intent = new Intent(ShowDetailsActivity.this, CinemasUserShowActivity.class);
            intent.putExtra("showModel",model);
            startActivity(intent);
        });
    }
    private void setUpYoutube(Context context, String url) {
        String videoId = extractYTId(url);
        if (videoId != null) {

            binding.youtubePlayerView.setEnableAutomaticInitialization(false);
            binding.youtubePlayerView.initialize(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(YouTubePlayer youTubePlayer) {
                    super.onReady(youTubePlayer);
                    youTubePlayer.cueVideo(videoId, 0);

                }
            }, true);


        } else {
        }


    }
    private String extractYTId(String ytUrl) {
        String vId = null;
        Pattern pattern = Pattern.compile("^https?://.*(?:www\\.youtube\\.com/|v/|u/\\w/|embed/|watch\\?v=)([^#&?]*).*$",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(ytUrl);
        if (matcher.matches()) {
            vId = matcher.group(1);
        }
        return vId;
    }

}
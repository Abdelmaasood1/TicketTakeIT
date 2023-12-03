package com.finalproject.ui.user.activity_movie_details;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.finalproject.R;

import com.finalproject.adapter.CastAdapter;
import com.finalproject.databinding.ActivityMovieDetailsBinding;
import com.finalproject.model.MovieModel;
import com.finalproject.mvvm.ActivityDetailsMvvm;
import com.finalproject.ui.activity_base.BaseActivity;
import com.finalproject.ui.user.activity_cinema_users.CinemasUserActivity;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DataSource;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MovieDetailsActivity extends BaseActivity {
    private CastAdapter castadapter;
    private ActivityMovieDetailsBinding binding;
    private MovieModel model;
    private String id;
    private ExoPlayer player;
    private boolean isInFullScreen = false;
    private DataSource.Factory dataSourceFactory;
    private DefaultTrackSelector trackSelector;
    private ActivityDetailsMvvm mvvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        id = (String) intent.getSerializableExtra("movie_id");
    }

    private void initView() {
        setUpToolbar(binding.toolbar, getString(R.string.movie_details), R.color.color2, R.color.white);
        binding.toolbar.llBack.setOnClickListener(view -> finish());
        mvvm = ViewModelProviders.of(this).get(ActivityDetailsMvvm.class);
        binding.setLang(getLang());
        model=new MovieModel();
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
        mvvm.getOnDataSuccess().observe(this, movieModel -> {
            model=movieModel;
            binding.setModel(model);
            if (model!=null){
                if (model.getVideo()!=null){
//                    getVideoImage();
//                    setupPlayer();
                    setUpYoutube(this, model.getVideo());
                }
                if (castadapter!=null){
                    castadapter.updateList(movieModel.getHeroes());
                }
            }
        });
        mvvm.getDetails(id);


        binding.btnChooseCinema.setOnClickListener(view -> {
            Intent intent = new Intent(MovieDetailsActivity.this, CinemasUserActivity.class);
            intent.putExtra("movieModel",model);
            startActivity(intent);
        });
        castadapter = new CastAdapter(this);
        binding.recViewCast.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.recViewCast.setAdapter(castadapter);

//        binding.flVideo.setOnClickListener(v -> {
//            isInFullScreen = true;
////            binding.motionLayout.transitionToEnd();
//            if (player != null) {
//                player.setPlayWhenReady(true);
//            }
//
//
//        });


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


//    private void getVideoImage() {
//
//        int microSecond = 6000000;// 6th second as an example
//        Uri uri = Uri.parse(model.getVideo());
//        RequestOptions options = new RequestOptions().frame(microSecond).override(binding.imageVideo.getWidth(), binding.imageVideo.getHeight());
//        Glide.with(this).asBitmap()
//                .load(uri)
//                .centerCrop()
//                .apply(options)
//                .into(binding.imageVideo);
//    }
//    @SuppressLint("ClickableViewAccessibility")
//    private void setupPlayer() {
//
//        if (model.getVideo() != null) {
//            trackSelector = new DefaultTrackSelector(this);
//            dataSourceFactory = new DefaultDataSource.Factory(this);
//            MediaSourceFactory mediaSourceFactory = new DefaultMediaSourceFactory(dataSourceFactory);
//            MediaItem mediaItem = MediaItem.fromUri(Uri.parse(model.getVideo()));
//
//            player = new ExoPlayer.Builder(this)
//                    .setTrackSelector(trackSelector)
//                    .setMediaSourceFactory(mediaSourceFactory)
//                    .build();
//
//            player.setMediaItem(mediaItem);
//            player.setPlayWhenReady(false);
//            player.setRepeatMode(ExoPlayer.REPEAT_MODE_ONE);
//            binding.exoPlayer.setPlayer(player);
//            player.prepare();
//
//            binding.exoPlayer.setOnTouchListener((v, event) -> {
//                if (player != null && player.isPlaying()) {
//                    player.setPlayWhenReady(false);
//                } else if (player != null && !player.isPlaying()) {
//
//                    player.setPlayWhenReady(true);
//
//                }
//                return false;
//            });
//
//
//        }
//
//
//    }
//
//    public boolean isFullScreen() {
//        return isInFullScreen;
//    }
//
//    public void setToNormalScreen() {
//
//        isInFullScreen = false;
////        binding.motionLayout.transitionToStart();
//        if (player != null) {
//            player.setPlayWhenReady(false);
//        }
//
//
//    }
//    @Override
//    public void onResume() {
//        if ((Util.SDK_INT <= 23 || player == null) && model != null) {
//            setupPlayer();
//        }
//        super.onResume();
//
//
//    }

//    @Override
//    public void onStart() {
//        if (Util.SDK_INT > 23) {
//            if (player == null && model != null) {
//                setupPlayer();
//                binding.exoPlayer.onResume();
//            }
//
//
//        }
//        super.onStart();
//
//
//    }
//
//    @Override
//    public void onPause() {
//        if (Util.SDK_INT <= 23) {
//            if (player != null) {
//                player.setPlayWhenReady(false);
//            }
//        }
//        super.onPause();
//
//
//    }


}
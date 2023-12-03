package com.finalproject.ui.activity_splash;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.finalproject.R;

import com.finalproject.databinding.ActivitySplashhBinding;
import com.finalproject.preferences.Preferences;
import com.finalproject.ui.activity_base.BaseActivity;
import com.finalproject.ui.activity_login.LoginActivity;
import com.finalproject.ui.user.activity_home.HomeActivity;

import java.util.concurrent.TimeUnit;

import io.paperdb.Paper;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends BaseActivity {
    private ActivitySplashhBinding binding;
    private CompositeDisposable disposable = new CompositeDisposable();
    private Preferences preferences;
    private String lang;

    private Animation animation1;
    private Animation animation2;
    private Animation animation3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_splashh);
        initView();

    }
    private void initView() {
        Paper.init(this);
        lang = getLang();
        binding.setLang(getLang());

        preferences = Preferences.getInstance();
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Long aLong) {
                        animateMethod();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


        binding.loginBtn.setOnClickListener(view -> navigateToLoginActivity());

        binding.guestBtn.setOnClickListener(view -> navigateToHomeActivity());

    }

    private void animateMethod() {

        animation1 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.slide_up);
        animation2 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.slide_up);
        animation3 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.slide_up);
        binding.tLogo.setVisibility(View.VISIBLE);


        binding.tLogo.startAnimation(animation1);


        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.tLogo.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.tName.startAnimation(animation2);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.tName.setVisibility(View.VISIBLE);

            }


            @Override
            public void onAnimationEnd(Animation animation) {

                binding.buttonLinear.startAnimation(animation3);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation3.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

                binding.buttonLinear.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void navigateToLoginActivity() {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
    }


    private void navigateToHomeActivity() {
        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

}
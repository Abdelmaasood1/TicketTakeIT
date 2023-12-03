package com.finalproject.mvvm;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.finalproject.model.ComingSoonModel;
import com.finalproject.model.HomeDataModel;
import com.finalproject.model.SliderDataModel;
import com.finalproject.model.SliderModel;
import com.finalproject.model.MovieModel;
import com.finalproject.model.ShowModel;
import com.finalproject.remote.Api;
import com.finalproject.tags.Tags;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class FragmentHomeMVVM extends AndroidViewModel {
    private Context context;
    private MutableLiveData<SliderDataModel> sliderDataModelMutableLiveData;
    private MutableLiveData<List<MovieModel>> topMovieModelMutableLiveData;
    private MutableLiveData<List<ShowModel>> topShowModelMutableLiveData;
    private MutableLiveData<List<ComingSoonModel>> comingSoonMutableLiveData;
    private MutableLiveData<Boolean> isLoadingLivData;
    private CompositeDisposable disposable = new CompositeDisposable();


    public FragmentHomeMVVM(@NonNull @NotNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<List<MovieModel>> getMovies() {
        if (topMovieModelMutableLiveData == null) {
            topMovieModelMutableLiveData = new MutableLiveData<>();

        }
        return topMovieModelMutableLiveData;
    }

    public LiveData<List<ShowModel>> getShows() {
        if (topShowModelMutableLiveData == null) {
            topShowModelMutableLiveData = new MutableLiveData<>();

        }
        return topShowModelMutableLiveData;
    }
    public LiveData<List<ComingSoonModel>> getComingSoon() {
        if (comingSoonMutableLiveData == null) {
            comingSoonMutableLiveData = new MutableLiveData<>();

        }
        return comingSoonMutableLiveData;
    }

    public MutableLiveData<SliderDataModel> getSliderDataModelMutableLiveData() {
        if (sliderDataModelMutableLiveData == null) {
            sliderDataModelMutableLiveData = new MutableLiveData<>();
        }
        return sliderDataModelMutableLiveData;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        if (isLoadingLivData == null) {
            isLoadingLivData = new MutableLiveData<>();
        }
        return isLoadingLivData;
    }


    public void getSlider() {
        isLoadingLivData.setValue(true);
        Api.getService(Tags.base_url).getSlider()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<SliderDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<SliderDataModel> response) {
                        isLoadingLivData.postValue(false);

                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getStatus() == 200) {
                                sliderDataModelMutableLiveData.postValue(response.body());
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        isLoadingLivData.setValue(false);
                    }
                });
    }

    public void getHomeData(Context context) {
        isLoadingLivData.setValue(true);
        Api.getService(Tags.base_url).getHomeData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<HomeDataModel>>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NotNull Response<HomeDataModel> response) {
                        isLoadingLivData.postValue(false);
                        if (response.isSuccessful()&&response.body()!=null){
                            if (response.body().getStatus()==200){
                                topMovieModelMutableLiveData.postValue(response.body().getMoves());
                                topShowModelMutableLiveData.postValue(response.body().getShows());
                                comingSoonMutableLiveData.postValue(response.body().getSoon());
                            }
                        }
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        Log.e("error",e.getMessage());
                    }
                });
    }

}

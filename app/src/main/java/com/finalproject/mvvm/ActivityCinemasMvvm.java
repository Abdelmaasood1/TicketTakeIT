package com.finalproject.mvvm;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.finalproject.model.CinemaDataModel;
import com.finalproject.model.CinemaModel;
import com.finalproject.model.MovieModel;
import com.finalproject.remote.Api;
import com.finalproject.tags.Tags;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ActivityCinemasMvvm extends AndroidViewModel {

    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<List<CinemaModel>> onCinemaSuccess;
    private CompositeDisposable disposable = new CompositeDisposable();
    public ActivityCinemasMvvm(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> getIsLoading() {
        if (isLoading==null){
            isLoading=new MutableLiveData<>();
        }
        return isLoading;
    }

    public MutableLiveData<List<CinemaModel>> getOnCinemaSuccess() {
        if (onCinemaSuccess==null){
            onCinemaSuccess=new MutableLiveData<>();
        }
        return onCinemaSuccess;
    }

    public void getCinemas(){
        isLoading.setValue(true);

        Api.getService(Tags.base_url).getCinemas()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<CinemaDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<CinemaDataModel> response) {
                        isLoading.setValue(false);

                        if (response.isSuccessful() && response.body()!=null){
                            if (response.body().getData()!=null && response.body().getStatus()==200){
                                onCinemaSuccess.postValue(response.body().getData());
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("error", e.toString());
                    }
                });
    }
}

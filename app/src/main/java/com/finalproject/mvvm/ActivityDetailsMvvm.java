package com.finalproject.mvvm;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.finalproject.model.MovieDetailsDataModel;
import com.finalproject.model.MovieModel;
import com.finalproject.model.ShowDetailsDataModel;
import com.finalproject.model.ShowModel;
import com.finalproject.remote.Api;
import com.finalproject.tags.Tags;
import com.google.android.material.tabs.TabLayout;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ActivityDetailsMvvm extends AndroidViewModel {

    private MutableLiveData<Boolean> isLoading;

    private MutableLiveData<MovieModel> onDataSuccess;

    private MutableLiveData<ShowModel> onShowDataSuccess;

    private CompositeDisposable disposable = new CompositeDisposable();

    public ActivityDetailsMvvm(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> getIsLoading() {
        if (isLoading==null){
            isLoading=new MutableLiveData<>();
        }
        return isLoading;
    }

    public MutableLiveData<MovieModel> getOnDataSuccess() {
        if (onDataSuccess==null){
            onDataSuccess=new MutableLiveData<>();
        }
        return onDataSuccess;
    }

    public MutableLiveData<ShowModel> getOnShowDataSuccess() {
        if (onShowDataSuccess==null){
            onShowDataSuccess=new MutableLiveData<>();
        }
        return onShowDataSuccess;
    }

    public void getDetails(String id){
        isLoading.setValue(true);

        Api.getService(Tags.base_url).getDetails(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<MovieDetailsDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<MovieDetailsDataModel> response) {
                        isLoading.setValue(false);
                        if (response.isSuccessful() && response.body()!=null){
                            if (response.body().getData()!=null && response.body().getStatus()==200){
                                onDataSuccess.postValue(response.body().getData());
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("error",e.toString());
                    }
                });
    }

    public void getShowDetails(String id){
        isLoading.setValue(true);
        Api.getService(Tags.base_url).getShowDetails(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<ShowDetailsDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<ShowDetailsDataModel> response) {
                        isLoading.setValue(false);
                        if (response.isSuccessful() && response.body()!=null){
                            if (response.body().getData()!=null && response.body().getStatus()==200){
                                onShowDataSuccess.postValue(response.body().getData());
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("error",e.toString());
                    }
                });
    }
}

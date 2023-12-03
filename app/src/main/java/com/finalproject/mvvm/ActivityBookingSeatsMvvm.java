package com.finalproject.mvvm;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.finalproject.model.CinemaModel;
import com.finalproject.model.DayDataModel;
import com.finalproject.model.DayModel;
import com.finalproject.model.SeatsModel;
import com.finalproject.model.TimeDataModel;
import com.finalproject.model.TimeModel;
import com.finalproject.remote.Api;
import com.finalproject.tags.Tags;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ActivityBookingSeatsMvvm extends AndroidViewModel {
    private Context context;

    private MutableLiveData<List<DayModel>> onDaySuccess;
    private MutableLiveData<List<TimeModel>> onTimeSuccess;
    private MutableLiveData<SeatsModel> onSeatsSuccess;
    private CompositeDisposable disposable = new CompositeDisposable();

    public ActivityBookingSeatsMvvm(@NonNull Application application) {
        super(application);
    }


    public MutableLiveData<List<DayModel>> getOnDaySuccess() {
        if (onDaySuccess == null) {
            onDaySuccess = new MutableLiveData<>();
        }
        return onDaySuccess;
    }

    public MutableLiveData<List<TimeModel>> getOnTimeSuccess() {
        if (onTimeSuccess == null) {
            onTimeSuccess = new MutableLiveData<>();
        }
        return onTimeSuccess;
    }

    public MutableLiveData<SeatsModel> getOnSeatsSuccess() {
        if (onSeatsSuccess == null) {
            onSeatsSuccess = new MutableLiveData<>();
        }
        return onSeatsSuccess;
    }

    public void getDays(String cinema_id, String post_id) {
//        Log.e("cinema_id",cinema_id);
        Api.getService(Tags.base_url).getDays(cinema_id, post_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<DayDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<DayDataModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getData() != null && response.body().getStatus() == 200) {
                                onDaySuccess.postValue(response.body().getData());
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("error", e.toString());
                    }
                });
    }

    public void getTimes(DayModel dayModel) {
//        Log.e("lll", dayModel.getId());
        Api.getService(Tags.base_url).getTimes(dayModel.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<TimeDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<TimeDataModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getData() != null && response.body().getStatus() == 200) {
                                onTimeSuccess.postValue(response.body().getData());
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("error", e.toString());
                    }
                });
    }

    public void getSeats(CinemaModel cinemaModel, DayModel dayModel, TimeModel timeModel) {
        Log.e("ids", cinemaModel.getId() + " " + dayModel.getId() + " " + timeModel.getId());
        Api.getService(Tags.base_url).getSeats(cinemaModel.getId(), dayModel.getId(), timeModel.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<SeatsModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<SeatsModel> response) {
                        Log.e("status",response.body().getStatus()+" "+response.code());
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getData() != null && response.body().getStatus() == 200) {
                                getOnSeatsSuccess().postValue(response.body());
                            } else if (response.body().getStatus() == 509) {

                            } else if (response.body().getStatus() == 510) {

                            } else if (response.body().getStatus() == 511) {

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

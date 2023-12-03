package com.finalproject.mvvm;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.finalproject.R;
import com.finalproject.model.LoginModel;
import com.finalproject.model.UserModel;
import com.finalproject.remote.Api;
import com.finalproject.share.Common;
import com.finalproject.tags.Tags;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ActivityLoginMvvm extends AndroidViewModel {
    private Context context;
    public MutableLiveData<UserModel> onLoginSuccess = new MutableLiveData<>();

    private CompositeDisposable disposable = new CompositeDisposable();


    public ActivityLoginMvvm(@NonNull Application application) {
        super(application);
    }


    public MutableLiveData<UserModel> getOnLoginSuccess() {
        if (onLoginSuccess == null) {
            onLoginSuccess = new MutableLiveData<>();
        }
        return onLoginSuccess;

    }


    public void login(Context context, LoginModel loginModel, String type) {
        ProgressDialog dialog = Common.createProgressDialog(context, context.getResources().getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
//        Log.e("data", loginModel.getUser_name() + "_" + loginModel.getPassword() + "_" + loginModel.getType() + "");
        Api.getService(Tags.base_url).login(loginModel.getUser_name(), loginModel.getPassword(), type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<UserModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);

                    }

                    @Override
                    public void onSuccess(@NonNull Response<UserModel> response) {
                        dialog.dismiss();
//                        Log.e("ssss", response.code() + "_");
                        if (response.isSuccessful() && response.body() != null) {

                            if (response.body().getStatus() == 200) {
                                onLoginSuccess.setValue(response.body());
                            }else if (response.body().getStatus()==403){
                                Toast.makeText(context, R.string.incorrect_data, Toast.LENGTH_SHORT).show();
                            }else if (response.body().getStatus()==509){
                                Toast.makeText(context, R.string.user_not_found, Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dialog.dismiss();
                        Log.e("error", e.toString());

                    }
                });
    }


}

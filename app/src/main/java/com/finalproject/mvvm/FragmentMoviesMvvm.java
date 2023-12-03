package com.finalproject.mvvm;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.finalproject.model.CategoryDataModel;
import com.finalproject.model.CategoryModel;
import com.finalproject.model.MovieModel;
import com.finalproject.model.MoviesDataModel;
import com.finalproject.remote.Api;
import com.finalproject.tags.Tags;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class FragmentMoviesMvvm extends AndroidViewModel {

    private MutableLiveData<Boolean> isLoading;

    private MutableLiveData<List<CategoryModel>> onCategorySuccess;

    private MutableLiveData<List<MovieModel>> onMoviesSuccess;

    private MutableLiveData<String> categoryId;

    private MutableLiveData<Integer> selectedCategoryPos;

    private CompositeDisposable disposable = new CompositeDisposable();

    public FragmentMoviesMvvm(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> getIsLoading() {
        if (isLoading == null) {
            isLoading = new MutableLiveData<>();
        }
        return isLoading;
    }

    public MutableLiveData<List<CategoryModel>> getOnCategorySuccess() {
        if (onCategorySuccess == null) {
            onCategorySuccess = new MutableLiveData<>();
        }
        return onCategorySuccess;
    }

    public MutableLiveData<List<MovieModel>> getOnMoviesSuccess() {
        if (onMoviesSuccess == null) {
            onMoviesSuccess = new MutableLiveData<>();
        }
        return onMoviesSuccess;
    }

    public MutableLiveData<String> getCategoryId() {
        if (categoryId == null) {
            categoryId = new MutableLiveData<>();
        }
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        getCategoryId().setValue(categoryId);
    }

    public MutableLiveData<Integer> getSelectedCategoryPos() {
        if (selectedCategoryPos == null) {
            selectedCategoryPos = new MutableLiveData<>(-1);
        }

        return selectedCategoryPos;
    }

    public void setSelectedCategoryPos(int pos) {
        getSelectedCategoryPos().setValue(pos);

    }

    public void getCategory() {
        isLoading.setValue(true);

        Api.getService(Tags.base_url).getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<CategoryDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<CategoryDataModel> response) {
                        isLoading.setValue(false);
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getData() != null && response.body().getStatus() == 200) {
                                onCategorySuccess.postValue(response.body().getData());
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("error", e.toString());
                    }
                });
    }

    public void getMovies(String search) {
        isLoading.setValue(true);

        Api.getService(Tags.base_url).getMovies(getCategoryId().getValue(),search)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<MoviesDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<MoviesDataModel> response) {
                        isLoading.setValue(false);
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getData() != null && response.body().getStatus() == 200) {
                                onMoviesSuccess.postValue(response.body().getData());
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

package com.finalproject.service;

import com.finalproject.model.CategoryDataModel;
import com.finalproject.model.CinemaDataModel;
import com.finalproject.model.DayDataModel;
import com.finalproject.model.HomeDataModel;
import com.finalproject.model.MovieDetailsDataModel;
import com.finalproject.model.MoviesDataModel;
import com.finalproject.model.SeatsModel;
import com.finalproject.model.ShowDetailsDataModel;
import com.finalproject.model.ShowDataModel;
import com.finalproject.model.ShowModel;
import com.finalproject.model.SliderDataModel;
import com.finalproject.model.StatusResponse;
import com.finalproject.model.TimeDataModel;
import com.finalproject.model.UserModel;

import java.util.List;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Service {

    @Multipart
    @POST("api/register")
    Single<Response<UserModel>> signUp(@Part("name") RequestBody name,
                                       @Part("user_name") RequestBody user_name,
                                       @Part("password") RequestBody password,
                                       @Part("national_id") RequestBody national_id,
                                       @Part("email") RequestBody email,
                                       @Part("gender") RequestBody gender,
                                       @Part("type") RequestBody type,
                                       @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("api/login")
    Single<Response<UserModel>> login(
            @Field("user_name") String user_name,
            @Field("password") String password,
            @Field("type") String type);

    @FormUrlEncoded
    @POST("api/logoutOrDelete")
    Single<Response<StatusResponse>> logOut(@Field("user_id") String user_id,
                                            @Field("delete") String delete);

    @FormUrlEncoded
    @POST("api/logoutOrDelete")
    Single<Response<StatusResponse>> delete(@Field("user_id") String user_id,
                                            @Field("delete") String delete);


    @Multipart
    @POST("api/edit_profile")
    Single<Response<UserModel>> update(@Part("user_id") RequestBody user_id,
                                       @Part("name") RequestBody name,
                                       @Part("user_name") RequestBody user_name,
                                       @Part("national_id") RequestBody national_id,
                                       @Part("gender") RequestBody gender,
                                       @Part("email") RequestBody email,
                                       @Part("password") RequestBody password,
                                       @Part MultipartBody.Part image);


    @GET("api/home")
    Single<Response<SliderDataModel>> getSlider();

    @GET("api/home")
    Single<Response<HomeDataModel>> getHomeData();

    @GET("api/posts/show")
    Single<Response<ShowDataModel>> getShow(@Query("title") String title);

    @GET("api/posts/move")
    Single<Response<MoviesDataModel>> getMovies(@Query("category_id") String category_id,
                                                @Query("title") String title);

    @GET("api/categories")
    Single<Response<CategoryDataModel>> getCategories();

    @GET("api/onePost")
    Single<Response<MovieDetailsDataModel>> getDetails(@Query("post_id") String post_id);

    @GET("api/onePost")
    Single<Response<ShowDetailsDataModel>> getShowDetails(@Query("post_id") String post_id);

    @FormUrlEncoded
    @POST("api/contact_us")
    Single<Response<StatusResponse>> ContactUs(
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("message") String message
    );

    @GET("api/cinemas")
    Single<Response<CinemaDataModel>> getCinemas();

    @GET("api/postDays")
    Single<Response<DayDataModel>> getDays(@Query("cinema_id") String cinema_id,
                                           @Query("post_id") String post_id);

    @GET("api/hoursOfDay")
    Single<Response<TimeDataModel>> getTimes(@Query("day_id") String day_id);

    @GET("api/chairStatus")
    Single<Response<SeatsModel>> getSeats(@Query("cinema_id") String cinema_id,
                                          @Query("day_id") String day_id,
                                          @Query("hour_id") String hour_id);


}

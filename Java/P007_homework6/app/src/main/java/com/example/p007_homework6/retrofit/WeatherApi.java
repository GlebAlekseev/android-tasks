package com.example.p007_homework6.retrofit;

import com.example.p007_homework6.retrofit.gson_model.WeatherRoot;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface WeatherApi {
    @GET("weather?" + Urls.WEATHER_API)
    Call<WeatherRoot> getWeather(@Query("q") String city);
}

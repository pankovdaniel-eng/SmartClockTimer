package com.example.smartclock.weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {
    @GET("data/3.0/onecall")
    Call<WeatherResponse> getWeather(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("appid") String appId,
            @Query("units") String units,
            @Query("exclude") String exclude
    );
}

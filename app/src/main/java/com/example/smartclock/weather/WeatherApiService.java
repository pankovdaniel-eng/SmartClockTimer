package com.example.smartclock.weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {
    @GET("current.json")
    Call<WeatherApiResponse> getWeather(
            @Query("key") String key,
            @Query("q") String q
    );
}

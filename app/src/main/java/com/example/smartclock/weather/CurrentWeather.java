package com.example.smartclock.weather;

import com.google.gson.annotations.SerializedName;

public class CurrentWeather {
    @SerializedName("temp_c")
    public double tempC;

    public Condition condition;
}

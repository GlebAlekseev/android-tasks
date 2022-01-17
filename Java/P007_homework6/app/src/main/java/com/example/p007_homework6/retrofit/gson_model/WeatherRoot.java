package com.example.p007_homework6.retrofit.gson_model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherRoot {
    @SerializedName("name")
    private String name;
    @SerializedName("sys")
    private Sys sys;
    @SerializedName("wind")
    private Wind wind;
    @SerializedName("main")
    private Main main;
    @SerializedName("weather")
    private List<Weather> weather = null;

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }
}



package com.example.p007_homework6.retrofit.gson_model;

import com.google.gson.annotations.SerializedName;

public class Main {
    @SerializedName("humidity")
    private double humidity;
    @SerializedName("temp")
    private double temp;

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }
}

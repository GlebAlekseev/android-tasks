package com.example.p007_homework6.helpers;

import android.content.Context;
import android.content.Intent;

import com.example.p007_homework6.activities.MainActivity;
import com.example.p007_homework6.room_db.Request;

public class HelperMethods {
    public static void startActivityWithRequestData(Context context, Class cls, Request request){
        Intent intent = new Intent(context,cls);
        // Передать данные
        intent.putExtra(MainActivity.REQUEST_NAME,request.name);
        intent.putExtra(MainActivity.REQUEST_TEMPERATURE,request.temperature);
        intent.putExtra(MainActivity.REQUEST_HUMIDITY,request.humidity);
        intent.putExtra(MainActivity.REQUEST_WIND_SPEED,request.windSpeed);
        intent.putExtra(MainActivity.REQUEST_SUNRISE,request.sunrise);
        intent.putExtra(MainActivity.REQUEST_SUNSET,request.sunset);
        intent.putExtra(MainActivity.REQUEST_DESCRIPTION,request.description);
        context.startActivity(intent);
    }

    public static Request getRequestDataFromOldActivity(Intent intent){
        Request newRequest = new Request();
        newRequest.name = intent.getStringExtra(MainActivity.REQUEST_NAME);
        newRequest.description = intent.getStringExtra(MainActivity.REQUEST_DESCRIPTION);
        newRequest.temperature = intent.getDoubleExtra(MainActivity.REQUEST_TEMPERATURE,0);
        newRequest.humidity = intent.getDoubleExtra(MainActivity.REQUEST_HUMIDITY,0);
        newRequest.windSpeed = intent.getDoubleExtra(MainActivity.REQUEST_WIND_SPEED,0);
        newRequest.sunrise = intent.getLongExtra(MainActivity.REQUEST_SUNRISE,0);
        newRequest.sunset = intent.getLongExtra(MainActivity.REQUEST_SUNSET,0);
        return newRequest;
    }
}

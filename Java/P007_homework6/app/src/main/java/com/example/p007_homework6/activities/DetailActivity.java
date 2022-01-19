package com.example.p007_homework6.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.p007_homework6.R;
import com.example.p007_homework6.helpers.HelperMethods;
import com.example.p007_homework6.room_db.Request;

public class DetailActivity extends AppCompatActivity {
    private TextView tvName;
    private TextView tvTemperature;
    private TextView tvHumidity;
    private TextView tvWindSpeed;
    private TextView tvSunrise;
    private TextView tvSunset;
    private TextView btnBack;
    private TextView tvDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Request request = HelperMethods.getRequestDataFromActivity(getIntent());
        initViews();
        initListeners();
        bindDataViews(request);
    }
    private void initViews(){
        tvName = findViewById(R.id.tvName);
        tvTemperature = findViewById(R.id.tvTemperature);
        tvHumidity = findViewById(R.id.tvHumidity);
        tvWindSpeed = findViewById(R.id.tvWindSpeed);
        tvSunrise = findViewById(R.id.tvSunriseTime);
        tvSunset = findViewById(R.id.tvSunsetTime);
        btnBack = findViewById(R.id.btnBack);
        tvDescription = findViewById(R.id.tvDescription);
    }
    private void initListeners(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void bindDataViews(Request request){
        tvName.setText(String.format( "%s %s",getResources().getString(R.string.name),request.name));
        tvTemperature.setText(String.format( "%s %s",getResources().getString(R.string.now_temperature),request.temperature));
        tvHumidity.setText(String.format( "%s %s",getResources().getString(R.string.humidity),request.humidity));
        tvWindSpeed.setText(String.format( "%s %s",getResources().getString(R.string.wind_speed),request.windSpeed));
        tvSunrise.setText(String.format( "%s %s",getResources().getString(R.string.sunrise_time),request.sunrise));
        tvSunset.setText(String.format( "%s %s",getResources().getString(R.string.sunset_time),request.sunset));
        tvDescription.setText(String.format( "%s %s",getResources().getString(R.string.weather_conditions),request.description));
    }
}
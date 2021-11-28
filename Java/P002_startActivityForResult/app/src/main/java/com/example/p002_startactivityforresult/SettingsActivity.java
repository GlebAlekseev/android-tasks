package com.example.p002_startactivityforresult;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    private final static String TAG = "SettingsActivity:";
    public static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        // Обработчик событие по нажатию, для перехода на другую Activity
        findViewById(R.id.btnEditName).setOnClickListener(view ->{
            Intent intent = new Intent(this, EditNameActivity.class);
            intent.putExtra("currentName",((TextView)findViewById(R.id.lblName)).getText().toString());
            startActivityForResult(intent,REQUEST_CODE);
        });
        Log.d(TAG,"onCreate");
    }

    // Получение результата порожденного Activity, после закрытия
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode){
            case REQUEST_CODE:
                String name = data.getStringExtra("name");
                ((TextView)findViewById(R.id.lblName)).setText(name);
                break;
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString("sName",((TextView)findViewById(R.id.lblName)).getText().toString());
        Log.d(TAG,"onSaveInstanceState");
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        ((TextView)findViewById(R.id.lblName)).setText(savedInstanceState.getString("sName"));
        Log.d(TAG,"onRestoreInstanceState");
    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG,"onStart");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG,"onResume");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG,"onPause");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG,"onStop");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }




}
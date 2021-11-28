package com.example.p002_resultapi;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.widget.TextView;


public class SettingsActivity extends AppCompatActivity {

    private final static String TAG = "SettingsActivity:";

    // Регистрация Callback для контракта StartActivityForResult - получение ссылки на объект-лаунчер
    ActivityResultLauncher<Intent> iARL = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK){
                        String name =  result.getData().getStringExtra("name");
                        ((TextView)findViewById(R.id.lblName)).setText(name);
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Обработчик события по нажатию, для перехода на другую Activity
        findViewById(R.id.btnEditName).setOnClickListener(view ->{
            Intent intent = new Intent(this, EditNameActivity.class);
            intent.putExtra("currentName",((TextView)findViewById(R.id.lblName)).getText().toString());
            iARL.launch(intent);
        });
        Log.d(TAG,"onCreate");
    }

    // Для сохранения значений при удалении Activity из памяти (нехватка памяти/смена ориентации экрана)
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
    // Отслеживание жизненного цикла Активности
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
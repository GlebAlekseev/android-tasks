package com.example.p002_resultapi;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.widget.TextView;


public class SettingsActivity extends AppCompatActivity {

    private final static String TAG = "SettingsActivity:";
    public final static String NAME = "name";
    public final static String SNAME = "sName";
    private TextView lblName;

    // Регистрация Callback для контракта StartActivityForResult - получение ссылки на объект-лаунчер
    ActivityResultLauncher<Intent> editNameActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK){
                        String name =  result.getData().getStringExtra(NAME);
                        if (name !=null){
                            lblName.setText(name);
                        }

                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        lblName= (TextView)findViewById(R.id.lblName);

        // Обработчик события по нажатию, для перехода на другую Activity
        findViewById(R.id.btnEditName).setOnClickListener(view ->{
            Intent intent = new Intent(this, EditNameActivity.class);
            intent.putExtra(EditNameActivity.CURRENT_NAME,lblName.getText().toString());
            editNameActivityLauncher.launch(intent);
        });
        Log.d(TAG,"onCreate");

    }

    // Для сохранения значений при удалении Activity из памяти (нехватка памяти/смена ориентации экрана)
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString(SNAME,lblName.getText().toString());
        Log.d(TAG,"onSaveInstanceState");
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        lblName.setText(savedInstanceState.getString(SNAME));
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
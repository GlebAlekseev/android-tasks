package com.example.p001_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class AccountActivity extends AppCompatActivity {

    Button btnExit;
    final String TAG = "AccountActivity:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Log.d(TAG,"onCreate");

        // Обработчик события нажатия на кнопку, завершение текущего Activity
        btnExit = (Button)findViewById(R.id.btnExit);
        View.OnClickListener oclBtnExit = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
        btnExit.setOnClickListener(oclBtnExit);

        // Получение переданных данных по ключу "login" из интента
        Intent intent_n = getIntent();
        String login_str = intent_n.getStringExtra("login");
        ((TextView)findViewById(R.id.lblHello)).setText("Привет, " + login_str);
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
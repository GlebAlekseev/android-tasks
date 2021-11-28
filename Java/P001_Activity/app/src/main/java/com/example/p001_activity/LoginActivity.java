package com.example.p001_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.view.View.OnClickListener;

public class LoginActivity extends AppCompatActivity {

    Button btnGo;
    EditText loginInp;
    final String TAG = "LoginActivity:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"onCreate");

        // Обработчик событие по нажатию, для перехода на другую Activity с сохранением значения "login"
        btnGo = (Button)findViewById(R.id.btnAuth);
        Intent intent2 = new Intent(this, AccountActivity.class);
        OnClickListener oclBtnGo = new OnClickListener() {
            @Override
            public void onClick(View v) {
                intent2.putExtra("login",((EditText)findViewById(R.id.login_inp)).getText().toString());
                startActivity(intent2);
            }
        };
        btnGo.setOnClickListener(oclBtnGo);

        // Обработчик события на изменение значения инпута, проверка на Enabled
        loginInp = (EditText)findViewById(R.id.login_inp);
        loginInp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {};

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable s){};
        });

        // Проверка на Enabled
        checkInput();
    }

    private void checkInput(){
        if (loginInp.length()!=0){
            btnGo.setEnabled(true);
            btnGo.setBackgroundColor(getResources().getColor(R.color.color_1));
            btnGo.setTextColor(getResources().getColor(R.color.color_2));
        }else{
            btnGo.setEnabled(false);
            btnGo.setBackgroundColor(getResources().getColor(R.color.disabledBG));
            btnGo.setTextColor(getResources().getColor(R.color.disabledTextColor));
        }
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
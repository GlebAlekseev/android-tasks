package com.example.p002_startactivityforresult;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.util.Log;

public class EditNameActivity extends AppCompatActivity {

    private final static String TAG = "EditNameActivity:";
    Button btnSaveName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_name);

        Intent intent_ = getIntent();
        ((EditText)findViewById(R.id.name_inp)).setText(intent_.getStringExtra("currentName"));

        btnSaveName = findViewById(R.id.btnSaveName);
        btnSaveName.setOnClickListener(view ->{
            Intent intent = new Intent();
            intent.putExtra("name",((EditText)findViewById(R.id.name_inp)).getText().toString());
            setResult(Activity.RESULT_OK,intent);
            finish();
        });
        Log.d(TAG,"onCreate");
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
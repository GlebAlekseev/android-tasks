package com.example.p002_resultapi;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.util.Log;

public class EditNameActivity extends AppCompatActivity {

    private final static String TAG = "EditNameActivity:";
    public final static String CURRENT_NAME = "currentName";
    private EditText nameInp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_name);
        nameInp = (EditText)findViewById(R.id.name_inp);
        // Заполнение поля значением из порождающей активности
        String currentName = getIntent().getStringExtra(CURRENT_NAME);
        if (currentName !=null){
            nameInp.setText(currentName);
        }

        // Обработчик события для клика
        findViewById(R.id.btnSaveName).setOnClickListener(view ->{
            Intent intent = new Intent();
            intent.putExtra(SettingsActivity.NAME,nameInp.getText().toString());
            // Установление возвращаемого интента для текущей активности, а также код завершения
            setResult(Activity.RESULT_OK,intent);
            // Завершение активности
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
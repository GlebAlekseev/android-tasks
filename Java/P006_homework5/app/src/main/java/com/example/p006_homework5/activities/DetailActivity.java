package com.example.p006_homework5.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.p006_homework5.helpers.HelperMethods;
import com.example.p006_homework5.room_db.App;
import com.example.p006_homework5.room_db.Human;
import com.example.p006_homework5.R;

public class DetailActivity extends AppCompatActivity {
    public final static String CURRENT_EMAIl = "current_email";
    public ImageView imageView;
    public TextView fioTextView;
    public TextView emailTextView;
    public TextView birthdayTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String email = getIntent().getStringExtra(CURRENT_EMAIl);
        Human human = App.getInstance().getAppDataBase().humanDao().getByEmail(email);
        if (human != null){
            imageView = findViewById(R.id.iv_image);
            birthdayTextView = findViewById(R.id.tv_birthday);
            emailTextView = findViewById(R.id.tv_email);
            fioTextView = findViewById(R.id.tv_fio);
            emailTextView.setText(human.email);
            fioTextView.setText(human.last_name + " " + human.name + " " + human.last_last_name);
            birthdayTextView.setText(human.birthday);

            HelperMethods.loadImageFromLinkToView(getApplicationContext(),human.url_image,imageView, HelperMethods.SIZE_IMAGE.DEFAULT_900x900);
        }else{
            Toast.makeText(getApplicationContext(), "Not Found User with email: "+email, Toast.LENGTH_SHORT).show();
        }

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Завершение активности
                setResult(DetailActivity.RESULT_OK);
                finish();
            }
        });
    }
}
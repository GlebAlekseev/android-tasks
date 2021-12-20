package com.example.p005_homework4;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {
    public final static String CURRENT_HUMAN = "current_human";
    public ImageView imageView;
    public TextView fioTextView;
    public TextView emailTextView;
    public TextView birthdayTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // getParcelableExtra - получение переданного объекта через интент
        // Объект типа Human упакован в Parcelable
        Human human = getIntent().getParcelableExtra(CURRENT_HUMAN);
        if (human != null){
            imageView = findViewById(R.id.iv_image);
            birthdayTextView = findViewById(R.id.tv_birthday);
            emailTextView = findViewById(R.id.tv_email);
            fioTextView = findViewById(R.id.tv_fio);

            emailTextView.setText(human.getEmail());
            fioTextView.setText(human.getFio());
            birthdayTextView.setText(human.getBirthday());
            // Библиотка Glide - требует permission internet для load(https)
            // with - передаю контекст, load - загружаю картинку (https)
            // override - изменение размера картинки
            // centerCrop - обрезает картинку по размеру элемента относительно центра
            // into - загрузка картинки в ImageView
            Glide.with(getApplicationContext())
                    .load(human.getUrl())
                    .override(900, 900)
                    .centerCrop()
                    .into(imageView);
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
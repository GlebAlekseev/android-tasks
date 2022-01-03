package com.example.p006_homework5.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.p006_homework5.R;
import com.example.p006_homework5.helpers.HelperMethods;
import com.example.p006_homework5.room_db.App;
import com.example.p006_homework5.room_db.Human;

public class UpBlockFragment  extends Fragment {
    private EditText lastName;
    private EditText lastLastName;
    private EditText name;
    private EditText email;
    private EditText birthday;
    private EditText urlImage;
    private Button btnLoadImage;
    private Button btnSave;
    private Button btnLoadInfo;
    private Button btnClear;
    private ImageView  image;
    public static final String IMAGE_CONTENT_DESCRIPTION = "image_content_description";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Инициализация view элементов
        initViews(view);
        btnLoadInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Human human = App.getInstance().getAppDataBase().humanDao().getByEmail(email.getText().toString());
                HelperMethods.setDataToViews(human,v.getContext(),name,lastName,lastLastName,email,birthday,urlImage,image);
            }
        });
        btnLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Установка кртинки
                HelperMethods.loadImageFromLinkToView(v.getContext(),urlImage.getText().toString(),image, HelperMethods.SIZE_IMAGE.DEFAULT_900x900);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Human human = getHumanFromViews();
                // Добавление собранного объекта в БД
                App.getInstance().getAppDataBase().humanDao().insert(human);
                // Обновление интерфейса
                DownBlockFragment downBlockFragment = (DownBlockFragment)getParentFragmentManager().findFragmentById(R.id.fragment_down_block);
                downBlockFragment.humanAdapter.Refresh();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Оичстка полей
                clearTextViews();
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Изменение интерфейса в зависимости от наличия почты в БД
                changeStateEmailUpBlock();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Восстановление картинки после смены конфигурации
        if (savedInstanceState != null){
            String savedUrlImage = savedInstanceState.getString(IMAGE_CONTENT_DESCRIPTION);
            HelperMethods.loadImageFromLinkToView(getContext(),savedUrlImage,image, HelperMethods.SIZE_IMAGE.DEFAULT_900x900);
        }
    }
    private void changeStateEmailUpBlock(){
        if (HelperMethods.isEmailValid(email.getText().toString())){
            // Почта уже зарегистрирована, отобразить кнопку для редактирования, изменить текст кнопки на Сохранить вместо Добавить
            // Подсветить поле ввода на оранжевый
            email.setTextColor(getResources().getColor(R.color.design_default_color_error));
            btnLoadInfo.setVisibility(View.VISIBLE);
            btnSave.setText("Сохранить");
        }else  {
            email.setTextColor(getResources().getColor(R.color.black));
            btnLoadInfo.setVisibility(View.INVISIBLE);
            btnSave.setText("Добавить");
        }
    }

    private Human getHumanFromViews(){
        Human human = new Human();
        human.birthday = birthday.getText().toString();
        human.email = email.getText().toString();
        human.url_image = urlImage.getText().toString();
        human.url_image = urlImage.getText().toString();
        human.last_last_name = lastLastName.getText().toString();
        human.last_name = lastName.getText().toString();
        human.name = name.getText().toString();
        return human;
    }

    private void clearTextViews(){
        lastLastName.setText("");
        lastName.setText("");
        name.setText("");
        email.setText("");
        birthday.setText("");
        urlImage.setText("");
    }
    private void initViews(View view){
        lastLastName = view.findViewById(R.id.et_lastlastname_add);
        lastName = view.findViewById(R.id.et_lastname_add);
        name = view.findViewById(R.id.et_name_add);
        email = view.findViewById(R.id.et_email_add);
        birthday = view.findViewById(R.id.et_birthday_add);
        urlImage = view.findViewById(R.id.et_url_image_add);
        btnSave = view.findViewById(R.id.btn_save_add);
        btnLoadImage = view.findViewById(R.id.btn_load_image_add);
        btnLoadInfo = view.findViewById(R.id.btn_load_info_add);
        btnClear = view.findViewById(R.id.btn_clear_add);
        image = view.findViewById(R.id.iv_image_add);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
       /* image.*/
        if (image.getContentDescription() != null){
            outState.putString(IMAGE_CONTENT_DESCRIPTION,image.getContentDescription().toString());
        }
    }
}

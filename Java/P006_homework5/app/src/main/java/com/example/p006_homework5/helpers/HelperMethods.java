package com.example.p006_homework5.helpers;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.p006_homework5.R;
import com.example.p006_homework5.fragments.UpBarFragment;
import com.example.p006_homework5.fragments.UpBlockFragment;
import com.example.p006_homework5.room_db.App;
import com.example.p006_homework5.room_db.Human;


public class HelperMethods {
    public enum SIZE_IMAGE{
        DEFAULT_900x900,
        SIZE_400x400,
        SIZE_200x200
    }
    public static Boolean isEmailValid(String email){
        return App.getInstance().getAppDataBase().humanDao().getByEmail(email) != null;
    }
    public static void loadImageFromLinkToView(Context context, String urlImage, ImageView image, SIZE_IMAGE size){
        int width = 0;
        int height = 0;
        switch (size){
            case DEFAULT_900x900:
                width = 900;
                height = 900;
                break;
            case SIZE_400x400:
                width = 400;
                height = 400;
                break;
            case SIZE_200x200:
                width = 200;
                height = 200;
                break;
        }
        // Библиотка Glide - требует permission internet для load(https)
        // with - передаю контекст, load - загружаю картинку (https)
        // override - изменение размера картинки
        // centerCrop - обрезает картинку по размеру элемента относительно центра
        // into - загрузка картинки в ImageView
        Glide.with(context)
                .load(urlImage)
                .override(width, height)
                .centerCrop()
                .into(image);
        // Добавляю в описание картинки, ссылку
        image.setContentDescription(urlImage);
    }
    public static void displayUpBlock(Fragment fragment){
        FragmentManager fragmentManager = fragment.getParentFragmentManager();
        UpBlockFragment upBlockFragment = (UpBlockFragment)fragmentManager.findFragmentById(R.id.fragment_up_block);
        UpBarFragment upBarFragment = (UpBarFragment)fragmentManager.findFragmentById(R.id.fragment_up_bar);
        Button btnNewContact = upBarFragment.getView().findViewById(R.id.btn_new_contact);

        fragmentManager.beginTransaction().show(upBlockFragment).commit();
        upBarFragment.lastDrawable = android.R.drawable.arrow_down_float;
        btnNewContact.setCompoundDrawablesWithIntrinsicBounds(0, 0, upBarFragment.lastDrawable, 0);
    }
    public static void setDataToViews(Human human, Context context,EditText name, EditText lastName, EditText lastLastName,
                                      EditText email, EditText birthday, EditText urlImage, ImageView image){
        lastLastName.setText(human.last_last_name);
        lastName.setText(human.last_name);
        name.setText(human.name);
        email.setText(human.email);
        birthday.setText(human.birthday);
        urlImage.setText(human.url_image);
        // Отобразить картинку
        HelperMethods.loadImageFromLinkToView(context,urlImage.getText().toString(),image, HelperMethods.SIZE_IMAGE.DEFAULT_900x900);;
    }
}

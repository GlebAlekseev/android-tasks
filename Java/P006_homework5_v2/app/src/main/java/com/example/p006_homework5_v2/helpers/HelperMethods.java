package com.example.p006_homework5_v2.helpers;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.p006_homework5_v2.R;
/*import com.example.p006_homework5_v2.fragments.UpBarFragment;
import com.example.p006_homework5_v2.fragments.UpBlockFragment;*/
import com.example.p006_homework5_v2.activities.DetailActivity;
import com.example.p006_homework5_v2.activities.MainActivity;
import com.example.p006_homework5_v2.room_db.App;
import com.example.p006_homework5_v2.room_db.Human;


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
    public static void displayUpBlock(FragmentManager fragmentManager){
/*        UpBlockFragment upBlockFragment = (UpBlockFragment)fragmentManager.findFragmentById(R.id.fragment_up_block);
        UpBarFragment upBarFragment = (UpBarFragment)fragmentManager.findFragmentById(R.id.fragment_up_bar);
        Button btnNewContact = upBarFragment.getView().findViewById(R.id.btn_new_contact);

        fragmentManager.beginTransaction().show(upBlockFragment).commit();
        upBarFragment.lastDrawable = android.R.drawable.arrow_down_float;
        btnNewContact.setCompoundDrawablesWithIntrinsicBounds(0, 0, upBarFragment.lastDrawable, 0);*/
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
    public static AlertDialog.Builder getAlertDialogBuilder(Context context, String email){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getResources().getString(R.string.confirmation));
        builder.setCancelable(true);
        builder.setPositiveButton(context.getResources().getString(R.string.remove), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                App.getInstance().getAppDataBase().humanDao().delete(email);
                if (context instanceof MainActivity){
                    MainActivity mainActivity =  (MainActivity) context;
                    mainActivity.refresh();
                }else if(context instanceof DetailActivity){
                    DetailActivity detailActivity =  (DetailActivity) context;
                    detailActivity.setResult(MainActivity.RESULT_REFRESH);
                    detailActivity.finish();
                }
            }
        });
        builder.setNegativeButton(context.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        return builder;
    }

    public static void startActivityWithData(Context context, Human human, DetailActivity.STATE state){
        MainActivity mainActivity = (MainActivity) context;

        Intent intent = new Intent(context, DetailActivity.class);
        if (human != null)
            intent.putExtra(DetailActivity.CURRENT_EMAIl,human.email);
        intent.putExtra(DetailActivity.CURRENT_STATE, state.toString());
        mainActivity.detailActivityLauncher.launch(intent);
    }
}

package com.example.homework3;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class MainFragment extends Fragment {
    private static final int ID_MENU_HELP = 101;
    private static final int ID_MENU_CAMERA = 102;
    private static final int ID_MENU_POPUP_HELP = 103;
    private static final int ID_MENU_POPUP_CAMERA = 104;

    private Button buttonPermissions;
    private Activity activity;
    private Context context;
    private String STR_PERMISSION_ACCESS;
    private  String STR_PERMISSION_DENIED ;
    private  String STR_PROCESSING_RECEIVED_DATA ;

    private  String STR_HELP ;
    private  String STR_CAMERA;

    private  String STR_CAMERA_MISSING;
    private  String STR_CAMERA_STARTING;



    // Регистрация контракта RequestPermission
    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Toast.makeText(context, STR_PERMISSION_ACCESS, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, STR_PERMISSION_DENIED, Toast.LENGTH_SHORT).show();
                }
            });
    // Регистрация контракта StartActivityForResult
    private ActivityResultLauncher<Intent> editNameActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Toast.makeText(context, STR_PROCESSING_RECEIVED_DATA, Toast.LENGTH_SHORT).show();
                    }
                }
            });


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Указывает, чтобы фрагмент участвовал в заполнении меню опций
        setHasOptionsMenu(true);
        activity = getActivity();
        context = getContext();
        STR_PERMISSION_ACCESS = getResources().getString(R.string.permission_access);
        STR_PERMISSION_DENIED = getResources().getString(R.string.permission_denied);
        STR_PROCESSING_RECEIVED_DATA = getResources().getString(R.string.processing_received_data);


        STR_HELP = getResources().getString(R.string.help);
        STR_CAMERA = getResources().getString(R.string.camera);

        STR_CAMERA_MISSING = getResources().getString(R.string.camera_missing);
        STR_CAMERA_STARTING = getResources().getString(R.string.camera_missing);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Преобразование XML ресурса в программный объект
        return inflater.inflate(R.layout.fragment_main,container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonPermissions = view.findViewById(R.id.buttonPermissions);
        buttonPermissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // Добавляю пункты в Option Menu
        //           ид группы,   ид пункта, порядок, текстовой представление
        menu.add(   1,   ID_MENU_HELP,1,STR_HELP).setIcon(android.R.drawable.ic_menu_help)
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(   1,   ID_MENU_CAMERA,2,STR_CAMERA).setIcon(android.R.drawable.ic_menu_camera)
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Получение элементов меню
        ActionMenuItemView actionMenuItemViewFirst  = activity.findViewById(ID_MENU_HELP);
        ActionMenuItemView actionMenuItemViewSecond = activity.findViewById(ID_MENU_CAMERA);

        // Создание popUp Menu
        PopupMenu popup;
        // Реализация события PopupMenu.OnMenuItemClickListener
        PopupMenu.OnMenuItemClickListener listener = new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case ID_MENU_POPUP_HELP:
                        Toast.makeText(context, item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    case ID_MENU_POPUP_CAMERA:
                        // Если устройство обладает камерой
                        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
                            // Если разрешение уже получено
                            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                                // Запуск камеры
                                Toast.makeText(context, STR_CAMERA_STARTING, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                editNameActivityLauncher.launch(intent);
                            }else{
                                // Отправить запрос на получение разрешения
                                requestPermissionLauncher.launch(Manifest.permission.CAMERA);
                            }
                        }else{
                            Toast.makeText(context, STR_CAMERA_MISSING, Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    default:
                        return false;
                }
            }
        };
        // Привязка popUp Menu к определенному View, регистрация события методом setOnMenuItemClickListener
        switch (item.getItemId()){
            case ID_MENU_HELP:
                popup = new PopupMenu(context,actionMenuItemViewFirst);
                popup.getMenu().add(1,ID_MENU_POPUP_HELP,1,item.getTitle());
                popup.setOnMenuItemClickListener(listener);
                popup.show();
                return true;
            case ID_MENU_CAMERA:
                popup = new PopupMenu(context,actionMenuItemViewSecond);
                popup.getMenu().add(1,ID_MENU_POPUP_CAMERA,1,item.getTitle());
                popup.setOnMenuItemClickListener(listener);
                popup.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

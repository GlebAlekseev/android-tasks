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
    private ActionMenuItemView actionMenuItemViewFirst;
    private ActionMenuItemView actionMenuItemViewSecond;

    // Регистрация контракта RequestPermission
    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    displayToast(R.string.permission_access);
                } else {
                    displayToast(R.string.permission_denied);
                }
            });
    // Регистрация контракта StartActivityForResult
    private ActivityResultLauncher<Intent> cameraActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        displayToast(R.string.processing_received_data);
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
        //           id группы,   id элемента , order  , title
        menu.add(   1,   ID_MENU_HELP,1,getResources()
                .getString(R.string.help))
                .setIcon(android.R.drawable.ic_menu_help)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(   1,   ID_MENU_CAMERA,2,getResources()
                .getString(R.string.camera))
                .setIcon(android.R.drawable.ic_menu_camera)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Получение элементов меню
        actionMenuItemViewFirst  = activity.findViewById(ID_MENU_HELP);
        actionMenuItemViewSecond = activity.findViewById(ID_MENU_CAMERA);
        PopupMenu popup;
        PopupMenu.OnMenuItemClickListener listener = new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case ID_MENU_POPUP_HELP:
                        displayToast(item.getTitle());
                        return true;
                    case ID_MENU_POPUP_CAMERA:
                        if (cameraIncluded()){
                            if (permissionReceived()){
                                displayToast(R.string.camera_starting);
                                startCameraActivity();
                            }else{
                                // Отправить запрос на получение разрешения
                                requestPermissionLauncher.launch(Manifest.permission.CAMERA);
                            }
                        }else{
                            displayToast(R.string.camera_missing);
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
    private void startCameraActivity(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraActivityLauncher.launch(intent);
    }

    private boolean cameraIncluded(){
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }
    private boolean permissionReceived(){
        return ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void displayToast(int id){
        Toast.makeText(context, getResources().getString(id), Toast.LENGTH_SHORT).show();
    }
    private void displayToast(String string){
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }
    private void displayToast(CharSequence string){
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }
}

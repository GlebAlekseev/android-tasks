package com.example.p006_homework5_v2.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.p006_homework5_v2.R;
import com.example.p006_homework5_v2.helpers.HelperMethods;
import com.example.p006_homework5_v2.listeners.RefreshDataViewListener;
import com.example.p006_homework5_v2.listeners.ShowPopupMenuListener;
import com.example.p006_homework5_v2.recycler_view.HumanAdapter;
import com.example.p006_homework5_v2.room_db.App;
import com.example.p006_homework5_v2.room_db.Human;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RefreshDataViewListener, ShowPopupMenuListener {
    public HumanAdapter humanAdapter;
    private Button btnNewContact;
    public static final int RESULT_REFRESH = 101;
    RecyclerView recyclerView;
    public static final int POPUPMENU_EDIT = 1;
    public static final int POPUPMENU_DELETE = 2;
    // Обновляет список, если было произведено добавление/редактирование/удаление в активности DetailActivity
    public ActivityResultLauncher<Intent> detailActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_REFRESH){
                        refresh();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview_list);
        btnNewContact = findViewById(R.id.btn_new_contact);

        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        humanAdapter = new HumanAdapter(getHumans());
        recyclerView.setAdapter(humanAdapter);
        // Обновление интерфейса
        refreshCount();
        // Добавление нового контакта
        btnNewContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Запуск DetailActivity
                HelperMethods.startActivityWithData(v.getContext(), null, DetailActivity.STATE.ADD);
            }
        });

    }

    // Возвращает список Human объектов полученных из БД
    public List<Human> getHumans(){
        return App.getInstance().getAppDataBase().humanDao().getAll();
    }

    @Override
    public void refresh() {
        // Изменить данные в адаптере RecyclerView
        humanAdapter.refreshListData();
        // Сообщить об изменении в адаптере
        humanAdapter.notifyDataSetChanged();
        // Обновить кол-во элементов
        refreshCount();
    }

    @Override
    public void refreshCount() {
        TextView countContacts =  findViewById(R.id.tv_count);
        countContacts.setText(App.getInstance().getResources().getString(R.string.contacts) + String.valueOf(App.getInstance().getAppDataBase().humanDao().getAll().size()));
    }

    @Override
    public void showPopupMenu(View view,Human human) {
        Context context = view.getContext();
        // Создание попап-меню
        PopupMenu popupMenu =new PopupMenu(context,view);
        PopupMenu.OnMenuItemClickListener listener = new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch  (item.getItemId()) {
                    case POPUPMENU_EDIT:
                        // Запуск DetailActivity
                        HelperMethods.startActivityWithData(context, human, DetailActivity.STATE.EDIT);
                        break;
                    case POPUPMENU_DELETE:
                        // Диалоговое окно, удаление элемента
                        AlertDialog dialog = HelperMethods.getAlertDialogBuilder(context,human.email).create();
                        dialog.show();
                        break;
                }
                return true;
            }
        };
        // Наполнение попап-меню
        popupMenu.getMenu().add(1,POPUPMENU_EDIT,1,context.getResources().getString(R.string.edit));
        popupMenu.getMenu().add(1,POPUPMENU_DELETE,2,context.getResources().getString(R.string.remove));
        popupMenu.setOnMenuItemClickListener(listener);
        popupMenu.show();
    }
}
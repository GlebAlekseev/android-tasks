package com.example.p006_homework5.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.example.p006_homework5.R;

public class MainActivity extends AppCompatActivity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Прячу фрагмент при запуске
        if (savedInstanceState == null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_up_block);
            fragmentManager.beginTransaction().hide(fragment).commit();
        }
    }
}


//+ TODO контракт + finish, убрать лишнее
//+ TODO Интерфейс в отдельный файл, с маленькой буквы метод


// TODO Зачекм я в начале прячу фрагмент
// TODO Смысл прятать фрагмент



// TODO Нельзя передавать в адаптер компоненты андроид


// TODO Текста в коде не должно быть, убрать builder.setMessage("Вы точно хотите удалить данного пользователя?");
// TODO Перенести логику из Application


// TODO Как в адаптере получить доступ к менеджеру фрагмента и к ресурсам (замена getResources().getString(R.string.save))
// TODO альтернатива получения доступа к фрагменту

// TODO Реализовать взаимодействие вне адаптера


// TODO

// Сделать интерфейс, реализовать его в MainActivity или во фрагменте
// Объявить в отдельном файле
// Для использования интерфейса в адаптере, нужно получить доступ к объекту
// Как просунуть доступ в адаптер

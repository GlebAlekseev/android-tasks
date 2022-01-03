package com.example.p006_homework5.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.p006_homework5.room_db.Human;
import com.example.p006_homework5.recycler_view.HumanAdapter;
import com.example.p006_homework5.R;
import com.example.p006_homework5.activities.DetailActivity;
import com.example.p006_homework5.room_db.App;

import java.util.List;
import com.example.p006_homework5.room_db.AppDataBase;
import com.example.p006_homework5.room_db.HumanDao;


public class DownBlockFragment extends Fragment implements HumanAdapter.OnItemClickListener {
    public HumanAdapter humanAdapter;
    RecyclerView recyclerView;
    // Контракт Result Api
    public ActivityResultLauncher<Intent> detailsActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Работа с RecyclerView
        recyclerView = view.findViewById(R.id.recyclerview_list);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        humanAdapter = new HumanAdapter(this,getHumans());
        recyclerView.setAdapter(humanAdapter);
        // Обновление интерфейса
        humanAdapter.RefreshCount();
    }

    // Возвращает список Human объектов полученных из БД
    public List<Human> getHumans(){
        return App.getInstance().getAppDataBase().humanDao().getAll();
    }


    // Реализация интерфейса HumanAdapter.OnItemClickListener
    // Интерфейс, реализованный в классе унаследованном от ComponentActivity, помогает получить доступ к методу класса ComponentActivity, registerForActivityResult
    @Override
    public void OnItemClick(Human human) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra(DetailActivity.CURRENT_EMAIl,human.email);
        detailsActivityLauncher.launch(intent);
    }
}

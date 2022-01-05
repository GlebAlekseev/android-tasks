package com.example.p006_homework5.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.p006_homework5.listeners.RefreshDataViewListener;
import com.example.p006_homework5.room_db.Human;
import com.example.p006_homework5.recycler_view.HumanAdapter;
import com.example.p006_homework5.R;
import com.example.p006_homework5.room_db.App;

import java.util.List;


public class DownBlockFragment extends Fragment implements RefreshDataViewListener {
    public HumanAdapter humanAdapter;
    RecyclerView recyclerView;

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
        humanAdapter = new HumanAdapter(getHumans());
        recyclerView.setAdapter(humanAdapter);
        // Обновление интерфейса
        refreshCount();
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
        UpBarFragment upBarFragment = (UpBarFragment)getParentFragmentManager().findFragmentById(R.id.fragment_up_bar);
        TextView textView = upBarFragment.getView().findViewById(R.id.tv_count);
        textView.setText(App.getInstance().getResources().getString(R.string.contacts) + String.valueOf(App.getInstance().getAppDataBase().humanDao().getAll().size()));
    }
}

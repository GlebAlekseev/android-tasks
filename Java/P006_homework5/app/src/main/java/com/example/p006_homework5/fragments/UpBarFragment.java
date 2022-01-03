package com.example.p006_homework5.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.p006_homework5.R;
import com.example.p006_homework5.recycler_view.HumanAdapter;
import com.example.p006_homework5.room_db.Human;

public class UpBarFragment extends Fragment {
    Button btnNewContact;
    private static final String LAST_DRAWABLE = "arrow_drawable";
    public int lastDrawable = android.R.drawable.arrow_up_float;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Получаю объект на основе xml
        return inflater.inflate(R.layout.fragment_up_bar,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnNewContact = view.findViewById(R.id.btn_new_contact);
        btnNewContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vBtnNewContact) {
                // Меняем видимость фрагмента по нажатию на кнопку Новый Контакт.
                changeStateUpBlock();
            }
        });
        if (savedInstanceState != null){
            lastDrawable = savedInstanceState.getInt(LAST_DRAWABLE);
            btnNewContact.setCompoundDrawablesWithIntrinsicBounds(0, 0, lastDrawable, 0);
        }
    }

    // Запускается во фрагменте. Меняет видимость фрагмента (Редактирование/Добавление)
    public void changeStateUpBlock(){
        FragmentManager fragmentManager = getParentFragmentManager();
        UpBlockFragment upBlockFragment = (UpBlockFragment)fragmentManager.findFragmentById(R.id.fragment_up_block);
        UpBarFragment upBarFragment = (UpBarFragment)fragmentManager.findFragmentById(R.id.fragment_up_bar);

        Button btnNewContact = upBarFragment.getView().findViewById(R.id.btn_new_contact);
        if (upBlockFragment.isHidden()){
            fragmentManager.beginTransaction().show(upBlockFragment).commit();
            lastDrawable = android.R.drawable.arrow_down_float;
            btnNewContact.setCompoundDrawablesWithIntrinsicBounds(0, 0, lastDrawable, 0);
        }else{
            fragmentManager.beginTransaction().hide(upBlockFragment).commit();
            lastDrawable = android.R.drawable.arrow_up_float;
            btnNewContact.setCompoundDrawablesWithIntrinsicBounds(0, 0, lastDrawable, 0);
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(LAST_DRAWABLE,lastDrawable);
    }

}

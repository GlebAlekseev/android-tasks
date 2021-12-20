package com.example.p005_homework4;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements HumanAdapter.OnItemClickListener {

    // Контракт Result Api
    public ActivityResultLauncher<Intent> detailsActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK){
                Toast.makeText(getApplicationContext(), "Result", Toast.LENGTH_SHORT).show();
            }
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Получаю программный объект RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rv_main);
        // Устанавливаю GridLayoutManager отвечающий за расположение ViewHolder, 2 столбца
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        // Устанавливаю адаптер, связывание данных с ViewHolder-ами
        HumanAdapter adapter = new HumanAdapter(getHumans());
        recyclerView.setAdapter(adapter);
    }
    // Создаю список из Human, заполняю Human значениями
    public List<Human> getHumans(){
        List<Human> listHumans = new ArrayList<>();
        String[] humans_birtday = getResources().getStringArray(R.array.human_birthday);
        String[] humans_email = getResources().getStringArray(R.array.human_email);
        String[] humans_fio = getResources().getStringArray(R.array.human_fio);
        String[] humans_url = getResources().getStringArray(R.array.human_url);
        for (int i = 0;i<10;i++){
            Human human = new Human(humans_url[i],humans_fio[i],humans_email[i],humans_birtday[i]);
            listHumans.add(human);
        }
        return listHumans;
    }


    // Реализация интерфейса HumanAdapter.OnItemClickListener
    // Интерфейс, реализованный в классе унаследованном от ComponentActivity, помогает получить доступ к методу класса ComponentActivity, registerForActivityResult
    @Override
    public void OnItemClick(Human human) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.CURRENT_HUMAN,human);
        detailsActivityLauncher.launch(intent);
    }
}
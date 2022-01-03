package com.example.p006_homework5.room_db;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.p006_homework5.R;
// Синглтон, для единоразового создания БД и доступа к БД во всем проекте (internal)
public class App extends Application {
    private static App instance;
    private AppDataBase roomDataBase;
    public static App getInstance(){return instance;}
    public AppDataBase getAppDataBase(){return roomDataBase;}
    public static final String DATA_BASE_NAME = "db_test_1";

    @Override
    public void onCreate(){
        super.onCreate();
        instance = this;
        // Колбек для заполнения начальными данными в БД
        RoomDatabase.Callback rdc = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                // Инициализация стандартными данными
                String[] humans_birtday = getResources().getStringArray(R.array.human_birthday);
                String[] humans_email = getResources().getStringArray(R.array.human_email);
                String[] humans_fio = getResources().getStringArray(R.array.human_fio);

                String[] humans_name = new String[humans_fio.length];
                String[] humans_last_name = new String[humans_fio.length];
                String[] humans_last_last_name = new String[humans_fio.length];
                int k = 0;
                for (String fio:humans_fio) {
                    String[] strFIO = fio.split(" ");
                    humans_last_name[k] = strFIO[0];
                    humans_name[k] = strFIO[1];
                    humans_last_last_name[k] = strFIO[2];
                    k++;
                }

                String[] humans_url = getResources().getStringArray(R.array.human_url);
                for (int i=0;i<humans_fio.length; i++){
                    db.execSQL("INSERT INTO human (name,last_name,last_last_name,email,birthday,url_image) VALUES(\""+humans_name[i]+"\",\""
                                                                                                                     +humans_last_name[i]+"\",\""
                                                                                                                     +humans_last_last_name[i]+"\",\""
                                                                                                                     +humans_email[i]+"\",\""
                                                                                                                     +humans_birtday[i]+"\",\""
                                                                                                                     +humans_url[i]+"\");");
                }
            }
        };
        roomDataBase = Room.databaseBuilder(getApplicationContext(),AppDataBase.class,DATA_BASE_NAME).allowMainThreadQueries().addCallback(rdc).build();
    }
}


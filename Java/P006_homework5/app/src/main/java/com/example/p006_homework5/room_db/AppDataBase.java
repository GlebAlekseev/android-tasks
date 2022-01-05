package com.example.p006_homework5.room_db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.p006_homework5.R;

@Database(entities = {Human.class},version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract HumanDao humanDao();
    // Колбек для заполнения начальными данными в БД
    public static RoomDatabase.Callback rdc = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Context appContext =  App.getInstance().getApplicationContext();
            // Инициализация стандартными данными
            String[] humans_birtday = appContext.getResources().getStringArray(R.array.human_birthday);
            String[] humans_email = appContext.getResources().getStringArray(R.array.human_email);
            String[] humans_fio = appContext.getResources().getStringArray(R.array.human_fio);

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

            String[] humans_url = appContext.getResources().getStringArray(R.array.human_url);
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

}


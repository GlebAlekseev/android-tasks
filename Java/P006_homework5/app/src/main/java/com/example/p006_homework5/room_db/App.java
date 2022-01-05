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
        roomDataBase = Room.databaseBuilder(getApplicationContext(),AppDataBase.class,DATA_BASE_NAME).allowMainThreadQueries().addCallback(AppDataBase.rdc).build();
    }
}


package com.example.p007_homework6.room_db;

import android.app.Application;

import androidx.room.Room;

// Синглтон, для единоразового создания БД и доступа к БД во всем проекте
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
        roomDataBase = Room.databaseBuilder(getApplicationContext(),AppDataBase.class,DATA_BASE_NAME).allowMainThreadQueries().build();
    }
}
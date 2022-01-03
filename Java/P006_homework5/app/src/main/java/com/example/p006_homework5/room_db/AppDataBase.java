package com.example.p006_homework5.room_db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Human.class},version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract HumanDao humanDao();
}

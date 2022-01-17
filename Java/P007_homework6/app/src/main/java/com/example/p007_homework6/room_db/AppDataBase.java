package com.example.p007_homework6.room_db;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {Request.class},version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract RequestDao requestDao();
}

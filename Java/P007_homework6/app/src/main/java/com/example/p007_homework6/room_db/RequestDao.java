package com.example.p007_homework6.room_db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RequestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Request request);

    @Query("SELECT * FROM request ORDER BY time_created DESC")
    List<Request> getAll();

    @Query("SELECT * FROM request WHERE name = :name")
    Request getByName(String name);

    @Query("UPDATE request SET time_created = :time_created WHERE name = :name")
    void updateTimeCreatedAtName(String name,long time_created);
}

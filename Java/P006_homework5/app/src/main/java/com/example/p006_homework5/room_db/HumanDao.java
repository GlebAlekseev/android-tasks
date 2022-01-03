package com.example.p006_homework5.room_db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface HumanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Human human);

    @Update
    void update(Human human);

    @Delete
    void delete(Human human);
    @Query("DELETE FROM human WHERE email = :email")
    void delete(String email);

    @Query("SELECT * FROM human")
    List<Human> getAll();

    @Query("SELECT * FROM human WHERE id = :id")
    Human getById(long id);

    @Query("SELECT * FROM human WHERE email = :email")
    Human getByEmail(String email);

}

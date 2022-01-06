package com.example.p006_homework5_v2.room_db;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

// email - unique
@Entity(indices = @Index(value = {"email"},unique = true))
public class Human {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String last_name;
    public String name;
    public String last_last_name;
    public String email;
    public String birthday;
    public String url_image;

}

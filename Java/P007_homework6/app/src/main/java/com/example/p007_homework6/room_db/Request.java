package com.example.p007_homework6.room_db;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

// email - unique
@Entity(indices = @Index(value = {"name"},unique = true))
public class Request {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String name;
    public long sunrise;
    public long sunset;
    public double windSpeed;
    public double humidity;
    public double temperature;
    public String description;
    public long time_created;
    public boolean status;
}
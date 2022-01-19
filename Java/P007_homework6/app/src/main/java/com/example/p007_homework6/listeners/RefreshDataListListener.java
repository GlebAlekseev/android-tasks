package com.example.p007_homework6.listeners;

import com.example.p007_homework6.room_db.Request;

import java.util.List;

public interface RefreshDataListListener {
    void refresh(List<Request> requestList);
}
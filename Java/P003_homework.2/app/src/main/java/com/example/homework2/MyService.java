package com.example.homework2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null){

            String data =intent.getStringExtra(MainActivity.DATA);
            Intent intentBroadcast = new Intent();
            intentBroadcast.setAction(MainActivity.ACTION);
            intentBroadcast.putExtra(MainActivity.DATA, data);
            sendBroadcast(intentBroadcast);
        }
        return START_NOT_STICKY;
    }

}

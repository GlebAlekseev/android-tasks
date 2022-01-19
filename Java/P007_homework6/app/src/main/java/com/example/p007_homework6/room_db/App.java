package com.example.p007_homework6.room_db;

import android.app.Application;

import androidx.room.Room;

import com.example.p007_homework6.retrofit.Urls;
import com.example.p007_homework6.retrofit.WeatherApi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Синглтон, для единоразового создания БД и доступа к БД во всем проекте
public class App extends Application {
    private static App instance;
    private AppDataBase roomDataBase;
    public static App getInstance(){return instance;}
    public AppDataBase getAppDataBase(){return roomDataBase;}
    public static final String DATA_BASE_NAME = "db_test_1";

    public Retrofit retrofit;
    public WeatherApi weatherApi;

    public static final long CONNECT_TIMEOUT = 30;
    public static final long READ_TIMEOUT = 60;
    public static final long WRITE_TIMEOUT = 60;
    @Override
    public void onCreate(){
        super.onCreate();
        instance = this;
        roomDataBase = Room.databaseBuilder(getApplicationContext(),AppDataBase.class,DATA_BASE_NAME).allowMainThreadQueries().build();
        retrofit = getRetrofit();
        weatherApi = App.getInstance().retrofit.create(WeatherApi.class);
    }
    // Возвращает объект Retrofit проинициализированный базовым url, клиентом и Gson конвертером
    private Retrofit getRetrofit(){
        OkHttpClient.Builder okHttClientBuilder = new OkHttpClient.Builder();
        // Время ожидания TCP подключения
        okHttClientBuilder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        // Максимально возможное время бездействия, после которого соединение разрывается
        okHttClientBuilder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
        // Задержка для остальных новый подключений
        okHttClientBuilder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        return new Retrofit.Builder()
                // Базовая ссылка
                .baseUrl(Urls.BASE_URL)
                // Клиент, который отправляет HTTP запросы, идет вместе с retrofit
                .client(okHttClientBuilder.build())
                // преобразование ответы в Json
                .addConverterFactory(GsonConverterFactory.create())
                // получение экземпляра Retrofit
                .build();
    }
}
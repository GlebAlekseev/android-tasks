package com.example.p007_homework6.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.p007_homework6.R;
import com.example.p007_homework6.helpers.HelperMethods;
import com.example.p007_homework6.recycler_view.RequestAdapter;
import com.example.p007_homework6.retrofit.Urls;
import com.example.p007_homework6.retrofit.WeatherApi;
import com.example.p007_homework6.retrofit.gson_model.WeatherRoot;
import com.example.p007_homework6.room_db.App;
import com.example.p007_homework6.room_db.Request;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvRequestList;
    private EditText etCityName;
    private Button btnRequest;
    private RequestAdapter requestAdapter;

    public static final String REQUEST_NAME = "request_name";
    public static final String REQUEST_TEMPERATURE = "request_temperature";
    public static final String REQUEST_HUMIDITY = "request_humidity";
    public static final String REQUEST_WIND_SPEED = "request_windSpeed";
    public static final String REQUEST_SUNRISE = "request_sunrise";
    public static final String REQUEST_SUNSET = "request_sunset";
    public static final String REQUEST_DESCRIPTION = "request_description";

    public static final long CONNECT_TIMEOUT = 30;
    public static final long READ_TIMEOUT = 60;
    public static final long WRITE_TIMEOUT = 60;

    private long now_time;

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
    private WeatherApi getWeatherApi(){
        return getRetrofit().create(WeatherApi.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initRecyclerView();
        initListeners();
    }
    // Заполнение данными из базы
    private List<Request> getRequests(){
        // Получение всех записей из БД
        return App.getInstance().getAppDataBase().requestDao().getAll();
    }
    // Инициализация вью при создании активити
    private void initViews(){
        rvRequestList = findViewById(R.id.rvRequestHistory);
        etCityName = findViewById(R.id.etCityName);
        btnRequest = findViewById(R.id.btnRequest);
    }
    // Инициализация событий при создании активити
    private void initListeners(){
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String requestName = etCityName.getText().toString();
                // Выход из метода, если в поле введено менее 2-ух символов
                if (requestName.length() < 2) return;
                // Получение кеша
                Request request = App.getInstance().getAppDataBase().requestDao().getByName(requestName);
                // Текущее время
                now_time = Calendar.getInstance().getTime().getTime();
                // Проверка на просроченность (Последние 6 часов)
                if (request != null && request.status && request.time_created + 60*60*6 <= now_time){
                    // Актуально
                    // Обновить время годности
                    App.getInstance().getAppDataBase().requestDao().updateTimeCreatedAtName(requestName,now_time);
                    // Обновление RecyclerView
                    requestAdapter.refresh();
                    // Запуск активити с данными
                    HelperMethods.startActivityWithRequestData(btnRequest.getContext(),DetailActivity.class,request);
                }else{
                    // Кеш отсутствует или не актуален
                    // Отправка запроса
                    Call<WeatherRoot> weatherCall = getWeatherApi().getWeather(requestName);
                    weatherCall.enqueue(cb);
                }
            }

        });
    }
    private Callback<WeatherRoot> cb = new Callback<WeatherRoot>() {
        @Override
        public void onResponse(Call<WeatherRoot> call, Response<WeatherRoot> response) {
            Request request = new Request();
            // Кеширование
            // Добавить запись в БД
            if (response.isSuccessful()){
                // Успешное получение данных
                WeatherRoot weatherRoot = response.body();
                request = getRequestFromWeatherRoot(weatherRoot);
                insertRequest(request,response.isSuccessful());
                // Открыть отображение
                HelperMethods.startActivityWithRequestData(btnRequest.getContext(),DetailActivity.class,request);
            }else{
                // Неудачное получение данных
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_prefix) + response.message(), Toast.LENGTH_SHORT).show();
                request.name = etCityName.getText().toString();
                insertRequest(request,response.isSuccessful());
            }
        }

        @Override
        public void onFailure(Call<WeatherRoot> call, Throwable t) {
            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
        }
        private void insertRequest(Request request,boolean status){
            request.time_created = now_time;
            request.status = status;
            App.getInstance().getAppDataBase().requestDao().insert(request);
            // Обновление RecyclerView
            requestAdapter.refresh();
        }

        private Request getRequestFromWeatherRoot(WeatherRoot weatherRoot){
            Request request = new Request();
            request.name = weatherRoot.getName();
            request.temperature = weatherRoot.getMain().getTemp();
            request.humidity = weatherRoot.getMain().getHumidity();
            request.windSpeed = weatherRoot.getWind().getSpeed();
            request.sunrise = weatherRoot.getSys().getSunrise();
            request.sunset = weatherRoot.getSys().getSunset();
            request.description = weatherRoot.getWeather() != null ? weatherRoot.getWeather().get(0).getDescription() :  getResources().getString(R.string.unknown);
            return request;
        }
    };
    // Инициализация RecyclerView при создании Активити
    private void initRecyclerView(){
        rvRequestList.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        requestAdapter = new RequestAdapter(getRequests());
        rvRequestList.setAdapter(requestAdapter);
    }



}
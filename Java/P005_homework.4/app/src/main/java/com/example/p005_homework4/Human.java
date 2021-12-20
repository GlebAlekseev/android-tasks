package com.example.p005_homework4;

import android.os.Parcel;
import android.os.Parcelable;

public class Human  implements Parcelable {
    private String url;
    private String fio;
    private String email;
    private String birthday;

    // Конструктор
    public Human(String url,String fio,String email,String birthday){
        this.url = url;
        this.fio = fio;
        this.email = email;
        this.birthday = birthday;
    }
    // Дополнительный конструктор для обертки Parcel
    public Human(Parcel parcel){
        // распаковка полей Parcel
        this.url = parcel.readString();
        this.fio = parcel.readString();
        this.birthday = parcel.readString();
        this.email = parcel.readString();
    }
    // Сеттеры/Геттеры
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Упаковываю в Parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(fio);
        dest.writeString(birthday);
        dest.writeString(email);
    }
    // Реализация интерфейса Creator из класса Parcelable
    public static final Parcelable.Creator<Human> CREATOR = new Parcelable.Creator<Human>() {
        public Human createFromParcel(Parcel in) {
            return new Human(in);
        }
        public Human[] newArray(int size) {
            return new Human[size];
        }
    };
}

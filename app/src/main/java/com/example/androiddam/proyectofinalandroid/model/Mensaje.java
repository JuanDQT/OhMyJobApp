package com.example.androiddam.proyectofinalandroid.model;

/**
 * Created by Juan on 27/05/2016.
 */
public class Mensaje {
    private String name;
    private String message;
    private String hour;

    public Mensaje(String name, String message, String hour) {
        this.name = name;
        this.message = message;
        this.hour = hour;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }


}

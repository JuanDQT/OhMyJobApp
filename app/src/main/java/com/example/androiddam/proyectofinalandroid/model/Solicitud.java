package com.example.androiddam.proyectofinalandroid.model;

/**
 * Created by Juan on 27/05/2016.
 */
public class Solicitud {
    private String url_user;
    private String tv_user_name;

    public Solicitud(String civ_user, String tv_user_name) {
        this.url_user = civ_user;
        this.tv_user_name = tv_user_name;
    }

    public String getUrl_user() {
        return url_user;
    }

    public void setUrl_user(String url_user) {
        this.url_user = url_user;
    }

    public String getTv_user_name() {
        return tv_user_name;
    }

    public void setTv_user_name(String tv_user_name) {
        this.tv_user_name = tv_user_name;
    }
}

package com.example.androiddam.proyectofinalandroid.model;

/**
 * Created by Juan on 21/05/2016.
 */
public class OptionMenu {
    private int icon;
    private String title;
    private int extra;

    public OptionMenu(int icon, String title, int extra) {
        this.icon = icon;
        this.title = title;
        this.extra = extra;
    }

    public int getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }

    public int getExtra() {
        return extra;
    }
}

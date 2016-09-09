package com.example.androiddam.proyectofinalandroid.model;

/**
 * Created by Edu-Dam on 19/05/2016.
 */
public class Tag {
    public String name;
    public int id;

    public Tag(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

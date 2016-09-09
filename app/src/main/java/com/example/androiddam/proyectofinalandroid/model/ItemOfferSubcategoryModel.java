package com.example.androiddam.proyectofinalandroid.model;

/**
 * Created by Juan on 04/09/2016.
 */
public class ItemOfferSubcategoryModel {
    private int id;
    private String name;
    private String url;

    public ItemOfferSubcategoryModel(int id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "ItemOfferSubcategoryModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}

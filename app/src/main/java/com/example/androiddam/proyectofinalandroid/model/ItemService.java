package com.example.androiddam.proyectofinalandroid.model;

/**
 * Created by Juan on 02/09/2016.
 */
public class ItemService {
    private int id;
    private String name;
    private String img;

    public ItemService(int id,String name, String drawable) {
        this.id = id;
        this.name = name;
        this.img = drawable;
    }

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ItemService{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}

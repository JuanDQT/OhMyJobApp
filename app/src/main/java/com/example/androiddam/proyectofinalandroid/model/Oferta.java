package com.example.androiddam.proyectofinalandroid.model;


/**
 * Created by Juan on 06/05/2016.
 */
public class Oferta {

    private int id;
    private String username;
    private int price;
    private String name;
    private String description;
    private Integer rating;
    private int cp;
    private int user_id;
    private String url_category;

    public int getId() {
        return id;
    }


    public String getUsername() {
        return username;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getRating() {
        return rating;
    }

    public int getCp() {
        return cp;
    }

    public int getUser_id() {
        return user_id;
    }

    //CONSTRUCTOR ADAPTER
    public Oferta(int id, int price, String name, Integer rating, String url_category) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.rating = rating;
        this.url_category = url_category;
    }

    //CONSTRUCTOR OFERTA GENERAL
    public Oferta(int id, int user_id, int price, String name, String description) {
        this.id = id;
        this.user_id = user_id;
        this.price = price;
        this.name = name;
        this.description = description;
    }/*    //CONSTRUCTOR OFERTA GENERAL
    public Oferta(int id, int user_id, int price, String name, Category category, String description) {
        this.id = id;
        this.user_id = user_id;
        this.price = price;
        this.name = name;
        this.category = category;
        this.description = description;
    }*/



    public String getUrl_category() {
        return url_category;
    }

    @Override
    public String toString() {
        return "Oferta{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                ", cp=" + cp +
                ", user_id=" + user_id +
                ", url_category='" + url_category + '\'' +
                '}';
    }

    public void setUrl_category(String url_category) {
this.url_category = url_category;
    }


}

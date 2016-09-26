package com.example.androiddam.proyectofinalandroid.model;


/**
 * Created by Juan on 06/05/2016.
 */
public class Oferta {

    private int id;
    private String pic_profile;
    private String username;
    private int price;
    private String name;
    private String description;
    private Integer rating;
    private int cp;
    private int user_id;
    private String url_category;

    //CONSTRUCTOR ADAPTER
    public Oferta(int id, int price, String name, String pic_profile, Integer rating, String url_category) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.pic_profile = pic_profile;
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

/*    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPic_profile() {
        return pic_profile;
    }

    public void setPic_profile(String pic_profile) {
        this.pic_profile = pic_profile;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public String getUrl_category() {
        return url_category;
    }

    @Override
    public String toString() {
        return "Oferta{" +
                "id=" + id +
                ", pic_profile='" + pic_profile + '\'' +
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

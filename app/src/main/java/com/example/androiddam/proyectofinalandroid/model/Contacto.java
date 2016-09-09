package com.example.androiddam.proyectofinalandroid.model;

/**
 * Created by Juan on 28/05/2016.
 */
public class Contacto {
    private int id_contact;
    private int accepted;
    private String name;
    private String lastname;
    private String img_path;

    public Contacto(int id_contact, int accepted, String name, String lastname, String img_path) {
        this.id_contact = id_contact;
        this.accepted = accepted;
        this.name = name;
        this.lastname = lastname;
        this.img_path = img_path;
    }

    public int getId_contact() {
        return id_contact;
    }

    public void setId_contact(int id_contact) {
        this.id_contact = id_contact;
    }

    public int getAccepted() {
        return accepted;
    }

    public void setAccepted(int accepted) {
        this.accepted = accepted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }
}
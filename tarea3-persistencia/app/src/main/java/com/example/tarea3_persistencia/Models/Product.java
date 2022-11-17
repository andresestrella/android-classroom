package com.example.tarea3_persistencia.Models;

import android.net.Uri;

import java.net.URI;

public class Product {
    private String uuid;
    private String name;
    private String imageUrl;
    private String brand;
    private float price;

    public Product() {}

    public Product(String uuid, String name, String brand, float price, String imageUrl) {
        this.uuid = uuid;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.brand = brand;
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}

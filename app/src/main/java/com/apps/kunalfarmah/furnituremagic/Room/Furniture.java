package com.apps.kunalfarmah.furnituremagic.Room;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "FURNITURE")

public class Furniture {
    public Furniture() {

    }

    @NonNull
    @PrimaryKey(autoGenerate = true)
    int id;

    String name, type, price, discountedPrice;
    String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Furniture(String name, String type, String price, String discountedPrice, String image) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.discountedPrice = discountedPrice;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(String discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

}

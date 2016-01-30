package com.example.jwetesko.travelapplication.Classes;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by jwetesko on 28.01.16.
 */
public class Country implements Serializable {
    private String name;
    private String URL;
    private String currency;
    private String language;
    private String capital;
    private ArrayList<String> photos = new ArrayList<String>();

    public void setProperty(String propertyType, String property) {
        switch(propertyType) {
            case("name"):
                this.name = property;
            case("URL"):
                this.URL = property;
            case("currency"):
                this.currency = property;
            case("language"):
                this.language = property;
            case("capital"):
                this.capital = property;
        }

    }

    public void addPhoto(String photoURL) { this.photos.add(photoURL); }

    public String getPhoto(int position) {
        return this.photos.get(position);
    }

    public ArrayList<String> getAllPhotos() {
        return this.photos;
    }

    public String getName() { return this.name; }

    public String getURL() { return this.URL; }

    public String getCurrency() { return this.currency; }

    public String getLanguage() { return this.language; }

    public String getCapital() { return this.capital; }
}

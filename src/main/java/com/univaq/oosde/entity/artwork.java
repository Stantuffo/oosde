package com.univaq.oosde.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class artwork {
    private int id, year, cat_id;
    private String isbn, title, description, language, img_url;

    public artwork(){}

    public artwork(artwork other) {
        this.id = other.getId();
        this.year = other.getYear();
        this.cat_id = other.getCat_id();
        this.isbn = other.getIsbn();
        this.title = other.getTitle();
        this.description = other.getDescription();
        this.language = other.getLanguage();
        this.img_url = other.getArtwork_main_img_url();
    }

    public artwork(int id, int year, int cat_id, String isbn, String title, String description, String language, String img_url) {
        this.id = id;
        this.year = year;
        this.cat_id = cat_id;
        this.isbn = isbn;
        this.title = title;
        this.description = description;
        this.language = language;
        this.img_url = img_url;
    }

    public artwork(ResultSet resultSet) throws SQLException {
        this.setId(resultSet.getInt("art_id"));
        this.setIsbn(resultSet.getString("isbn"));
        this.setTitle(resultSet.getString("title"));
        this.setDescription(resultSet.getString("description"));
        this.setLanguage(resultSet.getString("language"));
        this.setYear(resultSet.getInt("year"));
        this.setCat_id(resultSet.getInt("cat_id"));
        this.setArtwork_main_img_url(resultSet.getString("artwork_main_img_url"));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getArtwork_main_img_url() { return img_url; }

    public void setArtwork_main_img_url(String img_url) { this.img_url = img_url; }

}

package com.univaq.oosde.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class Artwork {
    private int id, year, cat_id, author_id;
    private String isbn, title, description, language;
    private boolean published;

    public Artwork() {
    }

    public Artwork(Artwork other) {
        this.id = other.getId();
        this.year = other.getYear();
        this.cat_id = other.getCat_id();
        this.isbn = other.getIsbn();
        this.title = other.getTitle();
        this.description = other.getDescription();
        this.language = other.getLanguage();
        this.author_id = other.getAuthor_id();
        this.published = other.isPublished();
    }

    public Artwork(int id, int year, int cat_id, String isbn, String title, String description, String language, String img_url, int author_id, boolean published) {
        this.id = id;
        this.year = year;
        this.cat_id = cat_id;
        this.isbn = isbn;
        this.title = title;
        this.description = description;
        this.language = language;
        this.author_id = author_id;
        this.published = published;
    }

    public Artwork(ResultSet resultSet) throws SQLException {
        this.setId(resultSet.getInt("art_id"));
        this.setIsbn(resultSet.getString("isbn"));
        this.setTitle(resultSet.getString("title"));
        this.setDescription(resultSet.getString("description"));
        this.setLanguage(resultSet.getString("language"));
        this.setYear(resultSet.getInt("year"));
        this.setCat_id(resultSet.getInt("cat_id"));
        this.setAuthor_id(resultSet.getInt("author_id"));
        this.setPublished(resultSet.getBoolean("published"));
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

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public List<Artwork> getAllOperas(boolean admin) throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();
        String sql;
        if (!admin) {
            sql = "SELECT * FROM artwork WHERE published = 1";
        } else {
            sql = "SELECT * FROM artwork order by published ASC";
        }
        ResultSet resultSet = statement.executeQuery(sql);
        List<Artwork> arts = new LinkedList<>();
        while (resultSet.next()) {
            Artwork art = new Artwork(resultSet);
            arts.add(art);
        }
        return arts;
    }

    public static Artwork getArtworkById(int id) throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM artwork WHERE art_id = " + id;
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        Artwork art = new Artwork(resultSet);
        return art;
    }

    public List<Image> getPagesOfOpera(int artId) throws SQLException {
        Image img = new Image();
        List<Image> pages = img.getPagesByArtworkId(artId);
        return pages;
    }
}

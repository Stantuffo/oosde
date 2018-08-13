package com.univaq.oosde.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class Category {
    private int id;
    private String name;

    public Category() {
    }

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category(Category other) {
        this.id = other.getId();
        this.name = other.getName();
    }

    public Category(ResultSet resultSet) throws SQLException {
        this.setId(resultSet.getInt("cat_id"));
        this.setName(resultSet.getString("name"));
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

    public List<Category> getCategoryList() throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM category";
        ResultSet resultSet = statement.executeQuery(sql);
        List<Category> catList = new LinkedList<>();
        while (resultSet.next()) {
            Category cat = new Category(resultSet);
            catList.add(cat);
        }
        return catList;
    }

    public static String getCategoryNameById(int catId) throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();
        String sql = "SELECT name FROM category WHERE cat_id ='" + catId + "'";
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next()) {
            return resultSet.getString("name");
        }
        return null;
    }
}

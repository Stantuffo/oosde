package com.univaq.oosde.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class Author implements AuthorModel {

    private int id;
    private String name, surname, birth_date, birth_place, birth_country;

    public Author(Author other) {
        this.id = other.getId();
        this.name = other.getName();
        this.surname = other.getSurname();
        this.birth_date = other.getBirth_date();
        this.birth_place = other.getBirth_place();
        this.birth_country = other.getBirth_country();
    }

    public Author(int id, String name, String surname, String birth_date, String birth_place, String birth_country) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birth_date = birth_date;
        this.birth_place = birth_place;
        this.birth_country = birth_country;
    }

    public Author(ResultSet resultSet) throws SQLException {
        this.setId(resultSet.getInt("auth_id"));
        this.setName(resultSet.getString("name"));
        this.setSurname(resultSet.getString("surname"));
        this.setBirth_date(resultSet.getString("birth_date"));
        this.setBirth_place(resultSet.getString("birth_place"));
        this.setBirth_country(resultSet.getString("birth_country"));
    }

    public Author() {
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getBirth_place() {
        return birth_place;
    }

    public void setBirth_place(String birth_place) {
        this.birth_place = birth_place;
    }

    public String getBirth_country() {
        return birth_country;
    }

    public void setBirth_country(String birth_country) {
        this.birth_country = birth_country;
    }

    public static Author getAuthorById(int authId) throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM author WHERE auth_id = " + authId;
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        Author auth = new Author(resultSet);
        return auth;
    }

    public List<Author> getAuthorList() throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM author ORDER BY surname";
        ResultSet resultSet = statement.executeQuery(sql);
        List<Author> authorList = new LinkedList<>();
        while (resultSet.next()) {
            Author auth = new Author(resultSet);
            authorList.add(auth);
        }
        return authorList;
    }
}

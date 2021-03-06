package com.univaq.oosde.model;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class Artwork implements ArtworkModel{
    private int id, year, cat_id;
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
        this.published = other.isPublished();
    }

    public Artwork(int id, int year, int cat_id, String isbn, String title, String description, String language, boolean published) {
        this.id = id;
        this.year = year;
        this.cat_id = cat_id;
        this.isbn = isbn;
        this.title = title;
        this.description = description;
        this.language = language;
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

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public static List<Artwork> getAllArtworks(boolean admin) throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();
        String sql;
        if (!admin) {
            sql = "SELECT * FROM artwork WHERE published = 1 ORDER BY title ASC";
        } else {
            sql = "SELECT * FROM artwork ORDER BY published ASC";
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

    public List<Image> getArtworkPages(int artId) throws SQLException {
        Image img = new Image();
        return img.getPagesByArtworkId(artId);
    }

    public static List<Author> getAuthorListById(int artId) throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM artwork AS art INNER JOIN paternity pat on art.art_id = pat.artwork_id INNER JOIN author AS auth ON pat.author_id = auth.auth_id WHERE art_id = " + artId +";";
        ResultSet resultSet = statement.executeQuery(sql);
        List<Author> authorList = new LinkedList<>();
        while (resultSet.next()){
            Author auth = new Author(resultSet);
            authorList.add(auth);
        }
        return authorList;
    }

    public static List<Artwork> searchArtworkBySearchString(String search) throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM artwork WHERE title LIKE '%"+search+"%' OR year LIKE '%"+search+"%' OR isbn LIKE '%"+search+"%';";
        ResultSet resultSet = statement.executeQuery(sql);
        List<Artwork> artworkList = new LinkedList<>();
        while (resultSet.next()){
            Artwork art = new Artwork(resultSet);
            artworkList.add(art);
        }
        return artworkList;
    }

    public static List<Artwork> getArtworkWithNotValidatedImages() throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();
        String sql = "SELECT DISTINCT artwork.* FROM artwork INNER JOIN image on artwork.art_id = image.artwork_id WHERE image.img_validated = 0";
        ResultSet resultSet = statement.executeQuery(sql);
        List<Artwork> artworkList = new LinkedList<>();
        while (resultSet.next()){
            Artwork art = new Artwork(resultSet);
            artworkList.add(art);
        }
        return artworkList;
    }

    public static int addArtwork(Connection connection, String title, String description, String language, int year, int category, int usrId, String isbn) throws SQLException {
        String sql = "INSERT INTO artwork(title, description, language, year, cat_id, added_by, isbn, published) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, title);
        statement.setString(2, description);
        statement.setString(3, language);
        if (year > 0) {
            statement.setInt(4, year);
        } else {
            statement.setNull(4, Types.INTEGER);
        }
        if (category > 0) {
            statement.setInt(5, category);
        } else {
            statement.setNull(5, Types.INTEGER);
        }
        statement.setInt(6, usrId);
        if (!isbn.equals("")) {
            statement.setString(7, isbn);
        } else {
            statement.setNull(7, Types.VARCHAR);
        }
        statement.setInt(8, 0);
        statement.executeUpdate();

        sql = "SELECT art_id FROM artwork ORDER BY art_id DESC LIMIT 1";
        ResultSet rs = statement.executeQuery(sql);
        rs.next();
        return rs.getInt("art_id");
    }

    public static boolean addAuthors(int art_id, String[] authorList, Connection connection) throws SQLException {
        for (String anAuthorList : authorList) {
            String sql = "INSERT INTO paternity(author_id, artwork_id) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(anAuthorList));
            statement.setInt(2, art_id);
            statement.executeUpdate();
        }
        return true;
    }
}

package com.univaq.oosde.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class Image {

    private int id, artwork_id;
    private String img_url, transcription;

    //Costruttore vuoto
    public Image(){}

    //Costruttore che prevede tutti i campi
    public Image(int id, int artwork_id, String img_url, String transcription) {
        this.id = id;
        this.img_url = img_url;
        this.artwork_id = artwork_id;
        this.transcription = transcription;
    }

    //costruttore da oggetto ResultSet (query DB)
    public Image(ResultSet resultSet) throws SQLException {
        this.setId(resultSet.getInt("img_id"));
        this.setImg_url(resultSet.getString("image_url"));
        this.setArtwork_id(resultSet.getInt("artwork_id"));
        this.setTranscription(resultSet.getString("transcription"));
    }

    //Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public int getArtwork_id() {
        return artwork_id;
    }

    public void setArtwork_id(int artwork_id) {
        this.artwork_id = artwork_id;
    }

    public String getTranscription() {
        return transcription;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    public List<Image> getPagesByArtworkId(int artId) throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM image WHERE artwork_id =" + artId;
        ResultSet resultSet = statement.executeQuery(sql);
        List<Image> pages = new LinkedList<>();
        while (resultSet.next()) {
            Image img = new Image(resultSet);
            pages.add(img);
        }
        return pages;
    }
}
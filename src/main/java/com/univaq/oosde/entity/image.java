package com.univaq.oosde.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class image {

    private int id, artwork_id;
    private String image_url, transcription;

    //Costruttore vuoto
    public image(){}

    //Costruttore che prevede tutti i campi
    public image(int id, int artwork_id, String image_url, String transcription) {
        this.id = id;
        this.image_url = image_url;
        this.artwork_id = artwork_id;
        this.transcription = transcription;
    }

    //costruttore da oggetto ResultSet (query DB)
    public image(ResultSet resultSet) throws SQLException {
        this.setId(resultSet.getInt("img_id"));
        this.setImage_url(resultSet.getString("image_url"));
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

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
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
}

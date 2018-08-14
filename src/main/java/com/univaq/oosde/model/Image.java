package com.univaq.oosde.model;

import org.springframework.web.servlet.ModelAndView;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class Image {

    private int id, artwork_id;
        private String img_url, transcription;
    private boolean img_validated, tr_validated;

    //Costruttore vuoto
    public Image() {
    }

    //Costruttore che prevede tutti i campi
    public Image(int id, int artwork_id, String img_url, String transcription, boolean img_validated, boolean tr_validated) {
        this.id = id;
        this.img_url = img_url;
        this.artwork_id = artwork_id;
        this.transcription = transcription;
        this.img_validated = img_validated;
        this.tr_validated = tr_validated;
    }

    //costruttore da oggetto ResultSet (query DB)
    public Image(ResultSet resultSet) throws SQLException {
        this.setId(resultSet.getInt("img_id"));
        this.setImg_url(resultSet.getString("image_url"));
        this.setArtwork_id(resultSet.getInt("artwork_id"));
        this.setTranscription(resultSet.getString("transcription"));
        this.setImg_validated(resultSet.getBoolean("img_validated"));
        this.setTr_validated(resultSet.getBoolean("tr_validated"));
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

    public boolean isImg_validated() { return img_validated; }

    public void setImg_validated(boolean img_validated){ this.img_validated = img_validated; }

    public boolean isTr_validated() { return tr_validated; }

    public void setTr_validated(boolean tr_validated){ this.tr_validated = tr_validated; }

    public List<Image> getPagesByArtworkId(int artId) throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM image WHERE artwork_id ="+artId+" AND img_validated = 1";
        ResultSet resultSet = statement.executeQuery(sql);
        List<Image> pages = new LinkedList<>();
        while (resultSet.next()) {
            Image img = new Image(resultSet);
            pages.add(img);
        }
        return pages;
    }

    public static Image getImageById(int imgId) throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM image WHERE img_id =" + imgId;
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next()) {
            Image img = new Image(resultSet);
            return img;
        }
        return null;
    }

    public static boolean insertTrascrizione(int imgId, String transcription) throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        PreparedStatement pstmt = connection.prepareStatement("UPDATE image SET transcription = (?) WHERE img_id = " + imgId);
        pstmt.setString(1, transcription); // this is your html string from step #1
        int res = pstmt.executeUpdate();
        System.out.println(res);
        return true;
    }

    public static String getTranscriptionByImgId(int idImg) throws SQLException {
        Image img = Image.getImageById(idImg);
        return img.getTranscription();
    }

    public static List<Image> getNotValidatedImages(int artId) throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM image WHERE img_validated = 0 AND image.artwork_id = "+ artId;
        ResultSet resultSet = statement.executeQuery(sql);
        List<Image> pages = new LinkedList<>();
        while (resultSet.next()) {
            Image img = new Image(resultSet);
            pages.add(img);
        }
        return pages;
    }

    public static List<Image> getTranscriptionsToValidate() throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM image WHERE tr_validated = 0 ";
        ResultSet resultSet = statement.executeQuery(sql);
        List<Image> unvalidatedTranscriptionList = new LinkedList<>();
        while (resultSet.next()) {
            Image img = new Image(resultSet);
            unvalidatedTranscriptionList.add(img);
        }
        return unvalidatedTranscriptionList;
    }
}
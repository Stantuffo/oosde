package com.univaq.oosde.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

public class User implements UserModel{
    private int id, tr_level;
    private String email, name, surname, birth_date, residency, fiscal_code, qualification, profession;
    private boolean transcriber;
    private boolean uploader;
    private boolean manager;
    private boolean administrator;
    private boolean request;
    private boolean downloader;

    //costruttore vuoto
    public User() {}

    //costruttore che prevede tutti i parametri
    public User(int id, String email, String nome, String cognome, String dataNascita, String residenza, String titoloStudio, String professione, String cf, boolean transcriber, boolean uploader, boolean manager, boolean administrator, boolean request, boolean downloader, int tr_level) {
        this.id = id;
        this.email = email;
        this.name = nome;
        this.surname = cognome;
        this.birth_date = dataNascita;
        this.residency = residenza;
        this.fiscal_code = cf;
        this.qualification = titoloStudio;
        this.profession = professione;
        this.transcriber = transcriber;
        this.uploader = uploader;
        this.manager = manager;
        this.administrator = administrator;
        this.request = request;
        this.downloader = downloader;
        this.tr_level = tr_level;
    }

    //costruttore da oggetto ResultSet (query DB)
    public User(ResultSet resultSet) throws SQLException {
        this.setId(resultSet.getInt("usr_id"));
        this.setEmail(resultSet.getString("email"));
        this.setName(resultSet.getString("name"));
        this.setSurname(resultSet.getString("surname"));
        this.setBirth_date(resultSet.getString("birth_date"));
        this.setResidency(resultSet.getString("residence"));
        this.setFiscal_code(resultSet.getString("fiscal_code"));
        this.setQualification(resultSet.getString("qualification"));
        this.setProfession(resultSet.getString("profession"));
        this.setTranscriber(resultSet.getBoolean("transcriber"));
        this.setUploader(resultSet.getBoolean("uploader"));
        this.setManager(resultSet.getBoolean("manager"));
        this.setAdministrator(resultSet.getBoolean("administrator"));
        this.setRequest(resultSet.getBoolean("request"));
        this.setDownloader(resultSet.getBoolean("downloader"));
        this.setTranscriberLevel(resultSet.getInt("tr_level"));
    }

    //Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getResidency() {
        return residency;
    }

    public void setResidency(String residency) {
        this.residency = residency;
    }

    public String getFiscal_code() {
        return fiscal_code;
    }

    public void setFiscal_code(String fiscal_code) {
        this.fiscal_code = fiscal_code;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public boolean isTranscriber() {
        return transcriber;
    }

    public void setTranscriber(boolean transcriber) {
        this.transcriber = transcriber;
    }

    public boolean isUploader() {
        return uploader;
    }

    public void setUploader(boolean uploader) {
        this.uploader = uploader;
    }

    public boolean isManager() {
        return manager;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }

    public boolean isAdministrator() {
        return administrator;
    }

    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
    }

    public boolean isRequest() {
        return request;
    }

    public void setRequest(boolean request) {
        this.request = request;
    }

    public boolean isDownloader() {
        return downloader;
    }

    public void setDownloader(boolean request) {
        this.downloader = downloader;
    }

    public int getTranscriberLevel(){
        return tr_level;
    }

    public void setTranscriberLevel(int tr_level) {
        this.tr_level = tr_level;
    }

    public static User getUserById(int usrId) throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM user WHERE usr_id = "+usrId;
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        User urs = new User(resultSet);
        return urs;
    }

    public static List<User> getTranscribers() throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM user WHERE transcriber = 1";
        ResultSet resultSet = statement.executeQuery(sql);
        List<User> transcribersList = new LinkedList<>();
        while (resultSet.next()) {
            User usr = new User(resultSet);
            transcribersList.add(usr);
        }
        return transcribersList;
    }

    public List<User> getUploaders() throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM users WHERE uploader = 1";
        ResultSet resultSet = statement.executeQuery(sql);
        List<User> uploaderList = new LinkedList<>();
        while (resultSet.next()) {
            User usr = new User(resultSet);
            uploaderList.add(usr);
        }
        return uploaderList;
    }

    public List<User> getManagers() throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM users WHERE manager = 1";
        ResultSet resultSet = statement.executeQuery(sql);
        List<User> managerList = new LinkedList<>();
        while (resultSet.next()) {
            User usr = new User(resultSet);
            managerList.add(usr);
        }
        return managerList;
    }

    public List<User> getAdministrators() throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM users WHERE administrator = 1";
        ResultSet resultSet = statement.executeQuery(sql);
        List<User> administratorList = new LinkedList<>();
        while (resultSet.next()) {
            User usr = new User(resultSet);
            administratorList.add(usr);
        }
        return administratorList;
    }

    public List<User> getRequestForDownloader() throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM users WHERE request = 1";
        ResultSet resultSet = statement.executeQuery(sql);
        List<User> requestList = new LinkedList<>();
        while (resultSet.next()) {
            User usr = new User(resultSet);
            requestList.add(usr);
        }
        return requestList;
    }

    public List<User> getDownloaders() throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM users WHERE download = 1";
        ResultSet resultSet = statement.executeQuery(sql);
        List<User> downloaderList = new LinkedList<>();
        while (resultSet.next()) {
            User usr = new User(resultSet);
            downloaderList.add(usr);
        }
        return downloaderList;
    }

    public ResultSet getTranscriptionList(int userId) throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();
        String sql = "SELECT a.art_id, a.title, COUNT(i.img_id) AS numero_pagine FROM artwork AS a INNER JOIN image AS i ON a.art_id = i.artwork_id INNER JOIN write_assignment AS w ON i.img_id = w.image_id WHERE w.user_id ='" + userId + "'GROUP BY a.art_id";
        ResultSet resultSet = statement.executeQuery(sql);
        return resultSet;
    }

    public TreeMap<Integer, String> getTranscriptionListByArtId(int artId, int userId) throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();
        TreeMap<Integer, String> imgMap = new TreeMap<>();
        String sql = "SELECT i.img_id, i.image_url FROM image AS i INNER JOIN write_assignment AS w ON i.img_id = w.image_id WHERE i.artwork_id ='" + artId + "'AND w.user_id = '" + userId + "'";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            imgMap.put(resultSet.getInt("img_id"), resultSet.getString("image_url"));
        }
        return imgMap;
    }

    public static boolean checkIfUserHasPageYet(int inputUser, int imgid) throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();
        String sql = "SELECT COUNT(w_a_id) AS esiste FROM write_assignment WHERE user_id ="+inputUser +" AND image_id ="+imgid;
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        return resultSet.getBoolean("esiste");
    }

    public static void assignTranscriptionToUser(int inputUser, int imgid) throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();
        String sql = "INSERT INTO write_assignment (image_id, user_id) VALUES ("+imgid+","+inputUser+")";
        statement.executeUpdate(sql);
    }
}
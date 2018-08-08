package com.univaq.oosde.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class user {
    private int id;
    private String email, nome, cognome, dataNascita, residenza, cf, titoloStudio, professione;
    private boolean transcriber;
    private boolean uploader;
    private boolean manager;
    private boolean administrator;
    private boolean viewer;
    private boolean request;
    private boolean downloader;

    //costruttore vuoto
    public user(){}

    //costruttore che prevede tutti i parametri
    public user(int id, String email, String nome, String cognome, String dataNascita, String residenza, String titoloStudio, String professione, String cf, boolean transcriber, boolean uploader, boolean manager, boolean administrator, boolean viewer, boolean request, boolean downloader) {
        this.id = id;
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.residenza = residenza;
        this.cf = cf;
        this.titoloStudio = titoloStudio;
        this.professione = professione;
        this.transcriber = transcriber;
        this.uploader = uploader;
        this.manager = manager;
        this.administrator = administrator;
        this.viewer = viewer;
        this.request = request;
        this.downloader = downloader;
    }

    //costruttore da oggetto ResultSet (query DB)
    public user(ResultSet resultSet) throws SQLException {
        this.setId(resultSet.getInt("usr_id"));
        this.setEmail(resultSet.getString("email"));
        this.setNome(resultSet.getString("name"));
        this.setCognome(resultSet.getString("surname"));
        this.setDataNascita(resultSet.getString("birth_date"));
        this.setResidenza(resultSet.getString("residence"));
        this.setCf(resultSet.getString("fiscal_code"));
        this.setTitoloStudio(resultSet.getString("qualification"));
        this.setProfessione(resultSet.getString("profession"));
        this.setTranscriber(resultSet.getBoolean("transcriber"));
        this.setUploader(resultSet.getBoolean("uploader"));
        this.setManager(resultSet.getBoolean("manager"));
        this.setAdministrator(resultSet.getBoolean("administrator"));
        this.setViewer(resultSet.getBoolean("viewer"));
        this.setRequest(resultSet.getBoolean("request"));
        this.setDownloader(resultSet.getBoolean("downloader"));
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(String datanascita) {
        this.dataNascita = datanascita;
    }

    public String getResidenza() {
        return residenza;
    }

    public void setResidenza(String residenza) {
        this.residenza = residenza;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getTitoloStudio() {
        return titoloStudio;
    }

    public void setTitoloStudio(String titoloStudio) {
        this.titoloStudio = titoloStudio;
    }

    public String getProfessione() {
        return professione;
    }

    public void setProfessione(String professione) {
        this.professione = professione;
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

    public boolean isViewer() {
        return viewer;
    }

    public void setViewer(boolean viewer) {
        this.viewer = viewer;
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

}
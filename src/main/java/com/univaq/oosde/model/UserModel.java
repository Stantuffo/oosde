package com.univaq.oosde.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.TreeMap;

public interface UserModel {

    int getId();

    void setId(int id);

    String getEmail();

    void setEmail(String email);

    String getName();

    void setName(String name);

    String getSurname();

    void setSurname(String surname);

    String getBirth_date();

    void setBirth_date(String birth_date);

    String getResidency();

    void setResidency(String residency);

    String getFiscal_code();

    void setProfession(String profession);

    boolean isTranscriber();

    void setTranscriber(boolean transcriber);

    boolean isUploader();

    void setUploader(boolean uploader);

    boolean isManager();

    void setManager(boolean manager);

    boolean isAdministrator();

    void setAdministrator(boolean administrator);

    boolean isRequest();

    void setRequest(boolean request);

    boolean isDownloader();

    void setDownloader(boolean request);

    int getTranscriberLevel();

    void setTranscriberLevel(int tr_level);

    List<User> getUploaders() throws SQLException;

    List<User> getManagers() throws SQLException;

    List<User> getAdministrators() throws SQLException;

    List<User> getRequestForDownloader() throws SQLException;

    List<User> getDownloaders() throws SQLException;

    ResultSet getTranscriptionList(int userId) throws SQLException;

    TreeMap<Integer, String> getTranscriptionListByArtId(int artId, int userId) throws SQLException;

}

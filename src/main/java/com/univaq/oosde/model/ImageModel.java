package com.univaq.oosde.model;

import java.sql.SQLException;
import java.util.List;

public interface ImageModel {

    int getId();

    void setId(int id);

    String getImg_url();

    void setImg_url(String img_url);

    int getArtwork_id();

    void setArtwork_id(int artwork_id);

    String getTranscription();

    void setTranscription(String transcription);

    boolean isImg_validated();

    void setImg_validated(boolean img_validated);

    boolean isTr_validated();

    void setTr_validated(boolean tr_validated);

    List<Image> getPagesByArtworkId(int artId) throws SQLException;

}

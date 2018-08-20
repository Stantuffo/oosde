package com.univaq.oosde.model;

import java.sql.SQLException;
import java.util.List;

public interface ArtworkModel {

    int getId();

    void setId(int id);

    int getYear();

    void setYear(int year);

    int getCat_id();

    void setCat_id(int cat_id);

    String getIsbn();

    void setIsbn(String isbn);

    String getTitle();

    void setTitle(String title);

    String getDescription();

    void setDescription(String description);

    String getLanguage();

    void setLanguage(String language);

    boolean isPublished();

    void setPublished(boolean published);

    List<Image> getArtworkPages(int artId) throws SQLException;
}

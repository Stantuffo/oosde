package com.univaq.oosde.model;

import java.sql.SQLException;
import java.util.List;

public interface AuthorModel {

    int getId();

    void setId(int id);

    String getName();

    void setName(String name);

    String getSurname();

    void setSurname(String surname);

    String getBirth_date();

    void setBirth_date(String birth_date);

    String getBirth_place();

    void setBirth_place(String birth_place);

    String getBirth_country();

    void setBirth_country(String birth_country);

    List<Author> getAuthorList() throws SQLException;
}

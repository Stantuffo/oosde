package com.univaq.oosde.model;

import java.sql.SQLException;
import java.util.List;

public interface CategoryModel {

    int getId();

    void setId(int id);

    String getName();

    void setName(String name);

    List<Category> getCategoryList() throws SQLException;

}

package org.example.dao;

import java.sql.SQLException;

//
public interface FoodDao {

    // creat tabel
    void createTable() throws SQLException;

    // adaugare date                      CREATE animals

    //gasire date                         READ animals

    //modificare date                     UPDATE animals

    //stergere date                       DELETE animals

    // sters tabel

    void dropTable() throws SQLException;



}

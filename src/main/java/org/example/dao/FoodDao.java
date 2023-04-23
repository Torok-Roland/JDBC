package org.example.dao;

import org.example.model.Food;

import java.sql.SQLException;

//
public interface FoodDao {

    // create tabel
    void createTable() throws SQLException;

    // adaugare date                      CREATE animals
    void createFood(Food food) throws SQLException;


    //gasire date                         READ animals

    //modificare date                     UPDATE animals

    //stergere date                       DELETE animals

    // sters tabel

    void dropTable() throws SQLException;


}

package org.example.dao;

import org.example.model.Food;
import java.util.List;
import java.sql.SQLException;

//
public interface FoodDao {

    // create tabel
    void createTable() throws SQLException;

    // adaugare date                      CREATE animals
    void createFood(Food food) throws SQLException;


    //gasire date                         READ animals
    List<Food> read();

    //modificare date                     UPDATE animals

    //stergere date                       DELETE animals

    // sters tabel

    void dropTable() throws SQLException;


}

package org.example.dao;

import org.example.model.Animal;

import java.sql.SQLException;

//Animal Data Access Object <- clasa pentru a accesa date din animals"
// manipulare structura baza de date (creare si stergere tabel)
// manipulare date - CRUD
public interface AnimalDao {

    // creat tabel
    void createTable() throws SQLException;

    // adaugare date                      CREATE animals
    void creatAnimals(Animal animal) throws SQLException;


    //gasire date                         READ animals

    //modificare date                     UPDATE animals

    //stergere date                       DELETE animals

    // sters tabel

    void dropTable() throws SQLException;


}

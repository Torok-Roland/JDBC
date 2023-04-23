package org.example.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class AnimalDaoImpl implements AnimalDao {

    private final Connection conection;

   public AnimalDaoImpl(Connection connection){
        this.conection = connection;
    }

    @Override

    public void createTable() throws SQLException {
        Statement statement = conection.createStatement();
                statement.execute("create table if not exists animals (" +
                        "id integer not null auto_increment, " +
                        "name varchar(100), " +
                        "species varchar (100), primary key(id))");
    }


    public void insertAnimals(String name, String species) throws SQLException{
        Statement statement = conection.createStatement();
        statement.execute("Insert into animals (name, species) values (\"Lucky\", \"Dog\")");
    }

    @Override
    public void dropTable() throws SQLException {
         Statement statement = conection.createStatement();
         statement.execute("drop table animals");
    }
}

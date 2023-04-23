package org.example.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class FoodDaoImpl implements FoodDao{

    private final Connection conection;

    public FoodDaoImpl(Connection conection) {
        this.conection = conection;
    }

    @Override
    public void createTable() throws SQLException {
        Statement statement = conection.createStatement();
        statement.execute("create table if not exists food (" +
                "id integer auto_increment, " +
                "name varchar (100)," +
                "description varchar(100)," +
                "calories_per_100 integer," +
                "expiration_date date," +
                "primary key(id))");

    }

    @Override
    public void dropTable() throws SQLException {
        Statement statement = conection.createStatement();
        statement.execute("drop table food");
    }
}

package org.example.dao;

import org.example.model.Animal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnimalDaoImpl implements AnimalDao {

    private final Connection connection;

    public AnimalDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override

    public void createTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("create table if not exists animals (" +
                "id integer not null auto_increment, " +
                "name varchar(100), " +
                "species varchar (100), primary key(id))");
    }


    public void createAnimals(Animal animal) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "Insert into animals (name, species) values (?,?)");

        preparedStatement.setString(1, animal.getName());
        preparedStatement.setString(2, animal.getSpecies());
        preparedStatement.execute();

    }

    public List<Animal> read() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("Select * From animals");
        List<Animal> animals = new ArrayList<>();
        while (rs.next()) {
            Animal animal = new Animal();
            animal.setId(rs.getInt(1));
            animal.setName(rs.getString(2));
            animal.setName(rs.getString(3));

            animals.add(animal);
        }
        return animals;
    }


    @Override
    public void dropTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("drop table animals");
    }
}

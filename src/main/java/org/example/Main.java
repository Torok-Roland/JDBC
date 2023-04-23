package org.example;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private final static Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        String dbLocation = "localhost:3306";
        String dbName = "jdbc_db";
        String dbUser = "root";
        String dbPassword = "Televiziune98!"; //parola voastra pentru baza de date

        //MySQLDATASOURCE <- vine din mysql conector si folosim ca sa configuram conexiunea cu baza de date
        MysqlDataSource dataSource = new MysqlDataSource();
        // Formatul pentru url-ul de conectare la baza de date
        // jdbc:mysql://<<locatia server-ului de baze de date>>/<<numele bazei de date>>
        // jdbc:mysql://localhost:3306/jdbc_db

        dataSource.setURL("jdbc:mysql://" + dbLocation + "/" + dbName);
        dataSource.setUser(dbUser);
        dataSource.setPassword(dbPassword);

        try {
            LOGGER.log(Level.INFO, "Trying to connect to DB");
            Connection connection = dataSource.getConnection();
            LOGGER.log(Level.INFO, "The connection succeed");

            //statement used for transferring sql commands to db server

            Statement statement = connection.createStatement();
            statement.execute("create table if not exists animals ( id integer not null auto_increment, name varchar(100), species varchar (100), primary key(id))");

            LOGGER.info("Create table animals was successful");

            // we can reuse statement object
            statement.execute("Insert into animals (name, species) values (\"Lucky\", \"Dog\")");
            LOGGER.info("Data insertion was successful");

            statement.execute("update animals set name = \"LuckysMother\" where id = 1");



            statement.execute("create table if not exists food (" +
                    "id integer auto_increment, " +
                    "name varchar (100)," +
                    " description varchar(100)," +
                    "calories_per_100 integer," +
                    "expiration_date date," +
                    "primary key(id))");
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into food(name, description, calories_per_100, expiration_date) values (?, ?, ?, ?)");
            preparedStatement.setString(1, "ciocolata");
            preparedStatement.setString(2, "ciocolata de casa");
            preparedStatement.setInt(3, 550);
            preparedStatement.setDate(4, Date.valueOf("2024-10-12"));

            preparedStatement.execute();

            preparedStatement.setString(1,"shaorma");
            preparedStatement.setString(2,"shaorma cu de toate");
            preparedStatement.setInt(3,850);
            preparedStatement.setDate(4, Date.valueOf("2024-01-01"));
            preparedStatement.execute();
            // intodeauna trebuie rulat .execute() daca vrem sa fie executat codul sql pe baza de date


            ResultSet rs = statement.executeQuery("SELECT * FROM animals");
            System.out.println("Animals: ");
            while (rs.next()){
                System.out.println("id: " + rs.getInt(1));
                System.out.println("name: " + rs.getString(2));
                System.out.println("Species: " + rs.getString(3));
            }

            System.out.println("**********************************************");

            ResultSet rs2 = statement.executeQuery("SELECT * FROM food order by calories_per_100");
            System.out.println("Foods: ");
            while (rs2.next()){

                System.out.print("id. " + rs2.getInt(1) + " - ");
                System.out.print(rs2.getString(2) + " - ");
                System.out.print(rs2.getString(3) + " - ");
                System.out.print(rs2.getInt(4) +"kcal per 100g - ");
                System.out.print("expiră la " + rs2.getDate(5));
                System.out.println();
            }

           // statement.execute("drop table food");
           // statement.execute("drop table animals");

//            System.out.println(rs.next());
//            System.out.println(rs.getInt(1));
//            System.out.println(rs.getString(2));
//            System.out.println(rs.getString(3));
//
//            System.out.println(rs.next());
//            System.out.println(rs.getInt(1));
//            System.out.println(rs.getString(2));
//            System.out.println(rs.getString(3));
//
//            System.out.println(rs.next());

        } catch (SQLException sqlException) {
////            sqlException.printStackTrace();
////           LOGGER.log(Level.SEVERE,"Error when connecting to database " + dbName + "from "
////                   +dbLocation + " with user " + dbUser);
            LOGGER.log(Level.SEVERE, sqlException.getMessage());
            sqlException.printStackTrace();

        }
    }
}
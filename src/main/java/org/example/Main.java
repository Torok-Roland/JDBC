package org.example;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.example.dao.AnimalDao;
import org.example.dao.AnimalDaoImpl;
import org.example.dao.FoodDao;
import org.example.dao.FoodDaoImpl;
import org.example.model.Animal;
import org.example.model.Food;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private final static Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        // git add .
        // git commit -m "add"
        // git push origin master / main

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

            AnimalDao animalDao = new AnimalDaoImpl(connection);
            FoodDao foodDao = new FoodDaoImpl(connection);

            //statement used for transferring sql commands to db server

            Statement statement = connection.createStatement();


            animalDao.createTable();
            foodDao.createTable();

            LOGGER.info("Tables creation was successful");


            // we can reuse statement object

            animalDao.createAnimals(new Animal(null,"Lucky", "dog"));
            animalDao.createAnimals(new Animal(null,"Rex", "dog"));
            LOGGER.info("Data insertion was successful");

            statement.execute("update animals set name = \"LuckysMother\" where id = 1");

                // De revenit!
//            Date expirationDate =
//            foodDao.createTable(new Food(null,"ciocolata", "ciocolata de casa", 550, Date.valueOf("2")));
//            PreparedStatement preparedStatement = connection.prepareStatement(
//                    "insert into food(name, description, calories_per_100, expiration_date) values (?, ?, ?, ?)");
//            preparedStatement.setString(1, "ciocolata");
//            preparedStatement.setString(2, "ciocolata de casa");
//            preparedStatement.setInt(3, 550);
//            preparedStatement.setDate(4, Date.valueOf("2024-10-12"));
//
//            preparedStatement.execute();
//
//            preparedStatement.setString(1,"shaorma");
//            preparedStatement.setString(2,"shaorma cu de toate");
//            preparedStatement.setInt(3,850);
//            preparedStatement.setDate(4, Date.valueOf("2024-01-01"));
//            preparedStatement.execute();
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

            animalDao.dropTable();
            foodDao.dropTable();
            LOGGER.info("Tables dropped successfully");


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
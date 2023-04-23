package org.example;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.example.dao.AnimalDao;
import org.example.dao.AnimalDaoImpl;
import org.example.dao.FoodDao;
import org.example.dao.FoodDaoImpl;
import org.example.model.Animal;
import org.example.model.Food;

import java.sql.*;
import java.util.List;
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

            animalDao.createAnimals(new Animal(null, "Lucky", "dog"));
            animalDao.createAnimals(new Animal(null, "Rex", "dog"));
            LOGGER.info("Data insertion was successful for Animals");

            statement.execute("update animals set name = \"LuckysMother\" where id = 1");


            animalDao.deleteAnimal(1);
            LOGGER.info("The method for delete an information from animal was successfully");


            // Adaugarea in tabelul the food:
            Date expirationDateForChocolate = Date.valueOf("2024-12-12");
            foodDao.createFood(new Food(null, "ciocolata", "ciocolata de casa", 550, expirationDateForChocolate));

            // Adaugarea in tabelul the food:
            Date expirationForShaorma = Date.valueOf("2023-05-01");
            foodDao.createFood(new Food(null, "shaorma", "shaorma cu de toate", 850, expirationForShaorma));
            LOGGER.info("Data insertion was successful for Foods");


            List<Animal> animals = animalDao.read();
            System.out.println("Animals from the list are: ");
            for(Animal animal : animals){
                System.out.println(animal);
            }
            LOGGER.info("The data from Animals table was read");
            System.out.println("**********************************************");

            ResultSet rs2 = statement.executeQuery("SELECT * FROM food order by calories_per_100");
            System.out.println("Foods: ");
            while (rs2.next()) {

                System.out.print("id. " + rs2.getInt(1) + " - ");
                System.out.print(rs2.getString(2) + " - ");
                System.out.print(rs2.getString(3) + " - ");
                System.out.print(rs2.getInt(4) + "kcal per 100g - ");
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
package org.example;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private final static Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
      String dbLocation = "localhost:3306";
      String dbName ="jdbc_db";
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
            LOGGER.log(Level.INFO,"The connection succeed");
        }  catch (SQLException sqlException){
            sqlException.printStackTrace();
           LOGGER.log(Level.SEVERE,"Error when connecting to database " + dbName + "from "
                   +dbLocation + " with user " + dbUser);
           LOGGER.log(Level.SEVERE, sqlException.getMessage());

        }
    }
}
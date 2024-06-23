package org.example.database;


import org.example.entity.Color;
import org.example.entity.Coordinates;
import org.example.entity.Location;
import org.example.entity.Person;
import org.example.exceptions.UserIsNotOwnerException;
import org.example.exceptions.WrongPasswordException;
import org.example.main.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;

public abstract class DatabaseConnection {
    protected Connection connection;

    public DatabaseConnection(String url, String login, String password) throws SQLException {
        this.connection = DriverManager.getConnection(url, login, password);
    }

    public abstract boolean authenticateUser(String login, String password) throws SQLException, WrongPasswordException;
    public abstract boolean addUser(String login, String password) throws SQLException;
    public abstract int addStudyGroup(String name,
                                      Coordinates coordinates,
                                      LocalDate date,
                                      double height,
                                      Integer weight,
                                      Color color,
                                      Location location) throws SQLException;

    public abstract boolean updateStudyGroup(Integer id,
                                             String name,
                                             Coordinates coordinates,
                                             LocalDate date,
                                             double height,
                                             Integer weight,
                                             Color color,
                                             Location location,
                                             String ownerLogin) throws SQLException, UserIsNotOwnerException;

    public abstract boolean removeById(Integer id, User user) throws SQLException;
    public abstract HashSet<Person> getAllStudyGroups() throws SQLException;
    public abstract int clearCollectionForUser(User user) throws SQLException;
    public abstract void setTables() throws SQLException;
}
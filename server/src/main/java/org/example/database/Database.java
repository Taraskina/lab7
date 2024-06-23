package org.example.database;

import java.sql.SQLException;

public abstract class Database {
    public abstract DatabaseConnection createConnection() throws SQLException;
}

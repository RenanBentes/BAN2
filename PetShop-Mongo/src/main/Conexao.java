package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "mongodb://localhost:27017";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}

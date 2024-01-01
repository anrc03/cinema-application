package com.enigma.configuration;

import java.sql.*;

public class DbConnector {
    private static Connection conn;

    public Connection startConnect() {
        String url = "jdbc:postgresql://localhost:5432/ciawi_cinema_db";
        String username = "postgres";
        String password = "adm1234";

        try {
            conn = DriverManager.getConnection(url, username, password);
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void closeConnection(ResultSet result, PreparedStatement ps, Connection conn) {
        try {
            result.close();
            ps.close();
            conn.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void closeConnection(PreparedStatement ps, Connection conn) {
        try {
            ps.close();
            conn.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

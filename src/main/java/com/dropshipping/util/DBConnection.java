package com.dropshipping.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:postgresql://aws-1-ap-southeast-1.pooler.supabase.com:5432/postgres?";
    private static final String USER = "postgres.dywodpuwqyfztfbkysaz"; 
    private static final String PASSWORD = "dnguynaries74";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
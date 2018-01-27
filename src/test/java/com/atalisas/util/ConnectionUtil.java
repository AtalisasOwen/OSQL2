package com.atalisas.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by 顾文涛 on 2017/12/12.
 */
public class ConnectionUtil {
    static String url;
    static String username;
    static String password;

    static {
        url = "jdbc:mysql://localhost:3306/books";
        username = "root";
        password = "123456";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }

    public static Connection getConnection(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}

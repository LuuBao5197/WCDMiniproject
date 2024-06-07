package group2.g2store;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Luu Bao
 */
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Scanner;

public class ConnectDB {

    public Connection cn;

    public Connection getConnect() {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=G2Store;encrypt=true;trustServerCertificate=true;";
        String username = "sa";
        String pass = "28032000";

        try {
            // load driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // connect to db
            cn = DriverManager.getConnection(url, username, pass);
            System.out.println("cn: "  + cn);
            System.out.println("Connect successfully");
        } catch (ClassNotFoundException ex) {
            System.out.println("Loi: " + ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("Khong the ket noi");
        }
        return cn;
    }
}


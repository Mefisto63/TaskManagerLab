package test;

import taskManager.manager.dbManager.ConnectionToDB;

import java.sql.*;

/**
 * Created by sergey on 11.02.15.
 */
public class testConnectToDB {

    public static void main(String[] args) throws SQLException {
        ConnectionToDB connectionToDB = new ConnectionToDB("TaskManagerData", "postgres", "963645987");
        connectionToDB.out();
    }
}

package taskManager.manager.dbManager;

import taskManager.tasks.Task;

import java.sql.*;

/**
 * Created by sergey on 11.02.15.
 */
public class ConnectionToDB {
    private Connection connection;

    public ConnectionToDB(String nameDb, String user, String pass){
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/" + nameDb, user, pass);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void out() throws SQLException {
        PreparedStatement ps;
        ps = connection.prepareCall("SELECT * FROM tasks");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Task task = null;
            System.out.println(rs.getLong(1) + rs.getString(2));
        }
    }

    public Connection getConnection() {
        return connection;
    }
}

package cinemaapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
//this a new comment
//github 
    private static Connection sConnection;
    
    public static Connection getInstance() throws ClassNotFoundException, SQLException {
        String host, db, user, password;
        
        host = "daneel";
        db = "N00130962";
        user = "N00130962";
        password = "N00130962";
        
        if (sConnection == null || sConnection.isClosed()) {
            String url = "jdbc:mysql://" + host + "/" + db;
            Class.forName("com.mysql.jdbc.Driver");
            sConnection = DriverManager.getConnection(url, user, password);
        }
        
        return sConnection;
    }
}
package bekks.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class Config {
    private final static String url="jdbc:postgresql://localhost:5432/postgres";
    private final static String username="postgres";
    private final static String password="12345";

    public static Connection getConnection() {
        Connection connection=null;
        try {
            connection= DriverManager.getConnection(url, username, password);
            System.out.println("Connected to database");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return connection;
    }
}

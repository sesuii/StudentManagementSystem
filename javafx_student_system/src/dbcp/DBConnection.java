package dbcp;

/*________________________
 @ Author: _Krill
 @ Data: 2021/10/30 21:41 
 @ Version: 1.0
__________________________*/

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public Connection databaseLink;
    public Connection getConnection(){
        String databaseName = "studentsystem";
        String user = "root";
        String password = "99999999";
        String url = "jdbc:mysql://127.0.0.1:3306/" + databaseName + "?allowLoadLocalInfile = true";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("failed to connect database");
            e.printStackTrace();
            e.getCause();
        }
        return databaseLink;
    }
}

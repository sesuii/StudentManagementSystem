package dbcp;

import java.sql.*;

/*________________________
 @ Author: _Krill
 @ Data: 2021/12/7 13:34 
 @ Version: 1.0
__________________________*/
public class DBCPUtil {
    public static DBConnection connectNow = new DBConnection();


    public static boolean verifyLogin(String sql) {
        Connection connectDB = connectNow.getConnection();
        try {
            PreparedStatement pstate = connectDB.prepareStatement(sql);
            ResultSet result = pstate.executeQuery();
            while (result.next()) {
                if(result.getInt(1) == 1) return true;
                    else return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int loadingFile(String sql) {
        Connection connectDB = connectNow.getConnection();
        int ret = -1;
        try {
            PreparedStatement pstate = connectDB.prepareStatement(sql);
            ret = pstate.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }
}

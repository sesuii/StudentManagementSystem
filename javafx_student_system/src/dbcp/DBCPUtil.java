package dbcp;

import com.krill_song.AdminViewData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*________________________
 @ Author: _Krill
 @ Data: 2021/12/7 13:34 
 @ Version: 1.0
__________________________*/
public class DBCPUtil {
    public static DBConnection connectNow = new DBConnection();
    public static void repleaseAll(Connection conn, Statement stmt, ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
            rs = null;
        }
        if (stmt != null) {
            stmt.close();
            stmt = null;
        }
        if (conn != null) {
            conn.close();
            conn = null;
        }
    }

    public static boolean verifyLogin(String sql) throws Exception {
        Connection connectDB = connectNow.getConnection();
        PreparedStatement pstate = null;
        ResultSet result = null;
        try {
             pstate = connectDB.prepareStatement(sql);
             result = pstate.executeQuery();
            while (result.next()) {
                if(result.getInt(1) == 1) return true;
                    else return false;
            }
        } finally {
                repleaseAll(connectDB, pstate, result);
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

    public static int insert(String sql, AdminViewData data) throws Exception {
        Connection connectDB = connectNow.getConnection();
        PreparedStatement pstate = null;
        System.out.println(data);
        pstate = connectDB.prepareStatement(sql);
        pstate.setString(1, data.getStu_id());
        pstate.setString(3, data.getStuName());
        pstate.setString(2, data.getForClass());
        pstate.setInt(4, -1);
        pstate.setString(5, "123456");
        return pstate.executeUpdate();
    }
    public static int delete(String sql, AdminViewData data) throws Exception {
        Connection connectDB = connectNow.getConnection();
        PreparedStatement pstate = null;
        pstate = connectDB.prepareStatement(sql);
        pstate.setString(1, data.getStu_id());
        return pstate.executeUpdate();
    }
    /**
    * @param: [sql]
    * @return: java.util.List<com.krill_song.AdminViewData>
    * @description: 导出数据库中数据到list
    * @date:2021/12/8
    **/
    public static List<AdminViewData> queryPlural(String sql) throws Exception {
        Connection conn = connectNow.getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        List<AdminViewData> list = new ArrayList<AdminViewData>();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new AdminViewData(rs.getString("account_id"), rs.getString("for_class"), rs.getString("stu_name"), rs.getInt("total_grade"),rs.getInt("classRank"),rs.getInt("majorRank")));
            }
        } finally {
            repleaseAll(conn, stmt, rs);
        }
        return list;
    }
}

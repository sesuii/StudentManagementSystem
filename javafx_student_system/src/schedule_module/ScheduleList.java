package schedule_module;

import dbcp.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import static schedule_module.ScheduleController.observableList;

/**
 * 
 */
public class ScheduleList {
     public static ArrayList<Schedule> list = new ArrayList<>();

    static {
        DBConnection connectNow = new DBConnection();
        Connection connectDB = connectNow.getConnection();
        String sql = "select * from schedule;";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while(result.next()) {
                list.add(new Schedule(result.getString("itemName"),
                        result.getString("itemRemark"),
                        result.getTimestamp("starttime").toLocalDateTime() ,
                        result.getTimestamp("endtime").toLocalDateTime(),
                        result.getTimestamp("remindtime").toLocalDateTime(), true)) ;
                observableList.add(result.getString("itemName") + "  开始时间 " + result.getString("starttime"));
            }
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * @param newSchedule 
     * @return
     */
    public static void addNew(Schedule newSchedule) {
        // TODO implement here
        observableList.add(0, newSchedule.getItemName() + "  开始时间 " + newSchedule.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm")));
        list.add(0, newSchedule);
    }
    /**
     * @param schedule
     * @return
     */
    public static void modify(Schedule schedule) {
        // TODO implement here
    }
    /**
     * @param deleteId
     * @return
     */
    public static void delete(int deleteId) {
        // TODO implement here
        list.remove(deleteId);
    }
}

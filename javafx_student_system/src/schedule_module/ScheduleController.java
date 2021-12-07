package schedule_module;

import com.jfoenix.controls.*;
import com.krill_song.StudentStage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import static schedule_module.ScheduleList.*;

/*________________________
 @ Author: _Krill
 @ Data: 2021/11/24 18:49 
 @ Version: 1.0
__________________________*/
public class ScheduleController implements Initializable {

    @FXML
    private JFXButton returnHomeButton;
    @FXML
    private JFXButton showDetailButton;
    @FXML
    private JFXButton deleteButton;
    @FXML
    Label labelMonth;
    @FXML
    GridPane gridPane;
    @FXML
    Label labelYear;

    @FXML
    private JFXListView<String> listViewSchedule;
    public static ObservableList<String> observableList = FXCollections.observableArrayList();
    public static LocalDate curClicked;
    private String selectedMonth;
    private  ArrayList<DateItem> dateList = new ArrayList<>();
    public static int clickedId = -1;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listViewSchedule.setItems(observableList);
        labelMonth.setText(String.valueOf(LocalDate.now().getMonth()));
        selectedMonth = String.valueOf(LocalDate.now().getMonth());
        // 把每一个DateItem加入到日历中
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                DateItem anchorPane = new DateItem();
                anchorPane.setPrefSize(200,200);
                anchorPane.setPadding(new Insets(10));
                JFXRippler rippler = new JFXRippler(anchorPane);
                rippler.setRipplerFill(Paint.valueOf("#CCCCCC"));
                gridPane.add(rippler, j, i);
                dateList.add(anchorPane);
            }
        }
        populateDate(YearMonth.now());

        listViewSchedule.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                clickedId = listViewSchedule.getSelectionModel().getSelectedIndex();
            }
        });
    }

    private void populateDate(YearMonth yearMonthNow){
        YearMonth yearMonth = yearMonthNow;
        LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);
        // 每个月从周一开始
        while (!calendarDate.getDayOfWeek().toString().equals("MONDAY") ) {
            calendarDate = calendarDate.minusDays(1);
        }
        // 先把之前的日期清空，再把当前日期填充到DateItem中
        for (DateItem anchorPane : dateList) {

            if (anchorPane.getChildren().size() != 0) {
                anchorPane.getChildren().clear();
            }
            anchorPane.setDate(calendarDate);
            Label label = new Label();
            label.setText(String.valueOf(calendarDate.getDayOfMonth()));
            label.setFont(Font.font(16));
            // 设置数字在日历格上的位置
            anchorPane.setTopAnchor(label, 5.0);
            anchorPane.setLeftAnchor(label, 5.0);
            anchorPane.getChildren().add(label);
            anchorPane.getStyleClass().remove("selectedDate");
            anchorPane.getStyleClass().remove("dateNow");
            if(anchorPane.getDate().equals(LocalDate.now())){ // 当前日期突出
                anchorPane.getStyleClass().add("dateNow");
            }
            anchorPane.setOnMouseClicked(event -> { // 点击触发事件
                System.out.println(anchorPane.getDate());
                for(DateItem dateItem : dateList){
                    dateItem.getStyleClass().remove("selectedDate");
                }
                anchorPane.getStyleClass().add("selectedDate");
                curClicked = anchorPane.getDate();
            });
            calendarDate = calendarDate.plusDays(1);
        }
    }
    private void changeCalendar(int year, String month){
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("yyyy MMMM")
                .toFormatter(Locale.ENGLISH);
         populateDate(YearMonth.parse(year + " " + month, formatter));
        selectedMonth = month;
    }
    @FXML
    void onButtonLastMonthClicked(ActionEvent event){
        LocalDateTime now = LocalDateTime.of(Integer.parseInt(labelYear.getText()), Month.valueOf(labelMonth.getText()), 1, 0, 0, 0);
        now = now.minusMonths(1);
        labelYear.setText(String.valueOf(now.getYear()));
        labelMonth.setText(String.valueOf(now.getMonth()));
        changeCalendar(now.getYear(), String.valueOf(labelMonth.getText()));
    }

    @FXML
    void onButtonNextMonthClicked(ActionEvent event){
        LocalDateTime now = LocalDateTime.of(Integer.parseInt(labelYear.getText()), Month.valueOf(labelMonth.getText()), 1, 0, 0, 0);
        now = now.plusMonths(1);
        labelYear.setText(String.valueOf(now.getYear()));
        labelMonth.setText(String.valueOf(now.getMonth()));
        changeCalendar(now.getYear(), String.valueOf(labelMonth.getText()));
    }

    /*
    * 添加新的日程
    * */
    @FXML
    void onButtonAddNewClicked(ActionEvent event) {
        Stage addNewStage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("resource/AddNewSchedule.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        addNewStage.initStyle(StageStyle.UNDECORATED);
        addNewStage.setTitle("Student");
        addNewStage.setScene(new Scene(root, 600, 450));
        addNewStage.centerOnScreen();
        addNewStage.show();
    }
    /*
     * 删除选中日程
     * */
    @FXML
    void deleteButtonOnAction(ActionEvent event) {
        System.out.println(clickedId);
        if(clickedId == -1) return;
        observableList.remove(clickedId);
        // 十分神奇的bug!!!在observableList移除最后一个元素后，clickedId自动从0变成-1，故加下面这句
        if(clickedId == -1) clickedId++;
        System.out.println(clickedId);
        delete(clickedId);
    }
    /*
     * 显示选择日程细节
     * */
    @FXML
    void showDetailButtonOnAction(ActionEvent event) {
        if(clickedId == -1) return;
        Schedule schedule = list.get(clickedId);
        JFXAlert alert = new JFXAlert(showDetailButton.getScene().getWindow());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);
        JFXDialogLayout layout = new JFXDialogLayout();
        Label label = new Label(schedule.getItemName());
        label.setFont(new Font("Cambria", 32));
        layout.setHeading(label);
        Label newContent = new Label("备注: " + schedule.getItemRemark()
                + "\n开始时间: " + schedule.getStartDate()
                + "\n结束时间: " + schedule.getEndDate()
                + "\n提醒时间: " + schedule.getReminderTime());
        newContent.setFont(new Font("Cambria", 16));
        layout.setBody(newContent);
        JFXButton closeButton = new JFXButton("确 认");
        closeButton.setPrefSize(150,55);
        closeButton.setFont(new Font("Cambria", 16));
        closeButton.getStyleClass().add("dialog-accept");
        closeButton.setOnAction(e -> alert.hideWithAnimation());
        layout.setActions(closeButton);
        alert.setContent(layout);
        alert.show();
    }

    @FXML
    void returnHomeButtonOnAction(ActionEvent event) {
        System.out.println("sad");
        Stage curStage = (Stage)showDetailButton.getScene().getWindow();
        StudentStage studentStage = new StudentStage();
        try {
            studentStage.start(curStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

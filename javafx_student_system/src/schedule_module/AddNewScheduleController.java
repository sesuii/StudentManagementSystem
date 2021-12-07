package schedule_module;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static schedule_module.ScheduleController.*;
import static schedule_module.ScheduleList.addNew;

public class AddNewScheduleController implements Initializable {

    private Schedule newSchedule;
    @FXML
    private JFXTextField itemName;

    @FXML
    private JFXTextField itemRemark;

    @FXML
    private JFXDatePicker beginDate;

    @FXML
    private JFXTimePicker beginTime;
    @FXML
    private JFXDatePicker endDate;
    @FXML
    private JFXTimePicker endTime;
    @FXML
    private JFXToggleButton isRemindTogButton;
    @FXML
    private HBox remindHBox;
    @FXML
    private JFXButton closeButton;
    @FXML
    private JFXButton okButton;
    @FXML
    void closeAddNewStage(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    /*
    * 将新增的事件放入Schedule
    * */
    @FXML
    void getNewScheduleButton(ActionEvent event) {
        String name = itemName.getText();
        String comment = itemRemark.getText();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String btime = beginDate.getValue()  + " " + beginTime.getValue();
        String etime = endDate.getValue() + " " + endTime.getValue();
        newSchedule = new Schedule(name, comment, LocalDateTime.parse(btime, dtf), LocalDateTime.parse(etime, dtf));
        if(isRemindTogButton.isSelected()) {
            newSchedule.setRemind(true);
            newSchedule.setReminderTime(LocalDateTime.parse(remindDate.getValue() + " " + remindTime.getValue(), dtf));
        }

        addNew(newSchedule);
        closeAddNewStage(event);
    }
    /*
    * 是否打开提醒
    * */
    private JFXTimePicker remindTime = new JFXTimePicker();
    private JFXDatePicker remindDate = new JFXDatePicker();
    @FXML
    void isReminding(ActionEvent event) {
        boolean isSelected = isRemindTogButton.isSelected();
        if(isSelected) {
            remindDate.setDefaultColor(Paint.valueOf("#0442bf"));
            remindTime.setDefaultColor(Paint.valueOf("#0442bf"));
            remindDate.setPrefSize(153, 23);
            remindTime.setPrefSize(153, 23);
            remindTime.setTranslateY(15);
            remindDate.setTranslateY(15);
            remindHBox.getChildren().addAll(remindDate, remindTime);
        }
        else {
            remindHBox.getChildren().remove(1, 3);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(curClicked != null) {
            beginDate.setValue(curClicked);
            beginTime.setValue(LocalTime.of(8,0));
            endDate.setValue(curClicked);
        }
    }
}

package com.krill_song;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import schedule_module.ScheduleStage;

import java.io.IOException;

/*________________________
 @ Author: _Krill
 @ Data: 2021/12/7 17:14 
 @ Version: 1.0
__________________________*/
public class StudentStageController {
    @FXML
    private HBox ScheduleButton;
    @FXML
    void ChangeToScheduleSceneOnAction(MouseEvent event) {
        Stage curStage = (Stage)ScheduleButton.getScene().getWindow();
        ScheduleStage scheduleStage = new ScheduleStage();
        try {
            scheduleStage.start(curStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

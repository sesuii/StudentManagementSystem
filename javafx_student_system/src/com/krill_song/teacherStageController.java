package com.krill_song;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

/*________________________
 @ Author: _Krill
 @ Data: 2021/12/2 16:48 
 @ Version: 1.0
__________________________*/
public class teacherStageController {

    @FXML
    private HBox setActivities;

    @FXML
    void setActivitiesOnAction(MouseEvent event) {
        Stage stage = (Stage)setActivities.getScene().getWindow();
        ActivityManage activityManage = new ActivityManage();
        try {
            activityManage.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.krill_song;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/*________________________
 @ Author: _Krill
 @ Data: 2021/12/20 13:37 
 @ Version: 1.0
__________________________*/
public class ActivityManage extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("resource/ActivityManageStage.fxml"));
        stage.setScene(new Scene(root, 1280, 720));
        stage.show();
    }
}

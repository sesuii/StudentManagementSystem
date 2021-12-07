package com.krill_song;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*________________________
 @ Author: _Krill
 @ Data: 2021/12/7 18:09 
 @ Version: 1.0
__________________________*/
public class StudentStage extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("resource/StudentStage.fxml"));
        stage.setTitle("成绩管理系统");
        stage.setScene(new Scene(root, 1280, 720));
        stage.centerOnScreen();
        stage.show();
    }
}

package com.krill_song;
/*________________________
 @ Author: _Krill
 @ Data: 2021/10/30 12:17 
 @ Version: 1.0
__________________________*/

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SysMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    public String name;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("resource/login.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("成绩管理系统登录");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}

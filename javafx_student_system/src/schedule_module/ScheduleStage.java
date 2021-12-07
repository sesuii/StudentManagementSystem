package schedule_module;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 入口
 */
public class ScheduleStage extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("resource/ScheduleStage.fxml"));
        stage.setTitle("Student Schedule");
        stage.setScene(new Scene(root, 1280, 720));
        stage.centerOnScreen();
        stage.show();
    }
}
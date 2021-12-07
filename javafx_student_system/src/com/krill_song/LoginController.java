package com.krill_song;

/*________________________
 @ Author: _Krill
 @ Data: 2021/10/30 22:16 
 @ Version: 1.0
__________________________*/
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dbcp.DBCPUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;

public class LoginController implements Initializable {
    @FXML
    private JFXTextField accountField;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private JFXComboBox<String> chooseIdentifybox;

    @FXML
    private JFXButton loginButton;

    @FXML
    private Label loginNote;

    @FXML
    private void loginButtonOnAction(ActionEvent event) {
        if (!accountField.getText().isEmpty() && !passwordField.getText().isEmpty() && !chooseIdentifybox.getItems().isEmpty()) {
            validateLogin();
        } else {
            loginNote.setText("请输入用户名和密码，并选择身份");
        }
    }
    /**
    * @param: []
    * @return: void
    * @description: 验证身份（账号和密码是否正确
    * @date:2021/12/7
    **/
    private void validateLogin() {
        String indentify = chooseIdentifybox.getSelectionModel().getSelectedItem();
        String loadFxml = null;
        if(indentify == "管理员") {
            loadFxml = "resource/adminSys.fxml";
            indentify = "adminuser";
        }
        else if(indentify == "学生") {
            loadFxml = "resource/StudentStage.fxml";
            indentify = "student_user";
        }
        else if(indentify == "教师") {
            loadFxml = "resource/teacherStage.fxml";
            indentify = "teacher_user";
        }
        String sql = "SELECT COUNT(1) FROM " + indentify +  " WHERE account_id = '" + accountField.getText() + "' and password = '" + passwordField.getText() + "';";
        if(!DBCPUtil.verifyLogin(sql)) {
            loginNote.setText("用户名或密码错误！请重新输入！");
            return;
        }
        try {
            Stage adminStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource(loadFxml));
            adminStage.setTitle("成绩管理系统");
            adminStage.setScene(new Scene(root, 1280, 720));
            adminStage.centerOnScreen();
            adminStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chooseIdentifybox.getItems().addAll("学生", "教师", "管理员");
    }
}

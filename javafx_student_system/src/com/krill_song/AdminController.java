package com.krill_song;

/*________________________
 @ Author: _Krill
 @ Data: 2021/10/31 22:03 
 @ Version: 1.0
__________________________*/

import dbcp.DBCPUtil;
import dbcp.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    @FXML
    private TableView<AdminTableView> formList;
    @FXML
    private TableColumn<AdminTableView, String> column_index;
    @FXML
    private TableColumn<AdminTableView, String> columnStuId;
    @FXML
    private TableColumn<AdminTableView, String> columnStuClass;
    @FXML
    private TableColumn<AdminTableView, String> columnStuName;
    @FXML
    private TableColumn<AdminTableView, Integer> columnStuTotalGrade;
    @FXML
    private TableColumn<AdminTableView, Integer> columnStuClassRank;
    @FXML
    private TableColumn<AdminTableView, Integer> columnStuMajorRank;
    @FXML
    private ComboBox<?> chooseTotalButton;
    @FXML
    private TextField searchStuField;
    @FXML
    private Button searchButton;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button refreshButton;
    @FXML
    private ComboBox<?> chooseClassButton;
    @FXML
    private Button loadingStudentButton;
    @FXML
    private Button chooseFileButton;
    @FXML
    private TextField csvFileTextField = null;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        iniTableView();
    }

    /**
    * @param: [javafx.event.ActionEvent]
    * @return: void
    * @description: 打开本地文件选择器，选择需要导入的.csv文件
    * @date:2021/12/7
    **/
    @FXML
    void chooseFile(ActionEvent event) {
        Stage primaryStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(primaryStage);
        System.out.println(file);
        if(file.exists()) csvFileTextField.setText(String.valueOf(file));

    }
    /**
    * @param: [javafx.event.ActionEvent]
    * @return: void
    * @description:点击导入学生池按钮，将文件导入到数据库中
    * @date:2021/12/7
    **/
    @FXML
    void loadingStudentOnAction(ActionEvent event) {
        if(csvFileTextField.getText().isEmpty()) return;
        String fileName = csvFileTextField.getText();
        fileName = fileName.replace('\\','/');
        System.out.println(fileName);
        String sql = "load data local infile " + "'" + fileName + "'" +
                " into table student_user fields terminated by',' lines terminated by '\\r\\n';";
        int loadDateCount = DBCPUtil.loadingFile(sql);
        System.out.println("导入数据成功！共导入 " + loadDateCount + "条数据");
    }

    private void iniTableView() {
        formList.setItems(AdminComonData.getData());
        DBConnection connectNow = new DBConnection();
        Connection connectDB = connectNow.getConnection();
        String showAll = "SELECT * FROM student_user;";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet ret = statement.executeQuery(showAll);
            while(ret.next()) {
                String stu_id = ret.getString(1);
                String forClass = ret.getString(2);
                String stu_name = ret.getString(3);
                AdminTableView temp = new AdminTableView(stu_id, forClass, stu_name);
                AdminComonData.getData().add(temp);
            }
            System.out.println("当前学生信息数：" + AdminComonData.getData().size());
        }
        catch(Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        System.out.println(AdminComonData.getData().size());
        column_index.setCellFactory(new Callback<TableColumn<AdminTableView,String>,TableCell<AdminTableView,String>>(){
            @Override
            public TableCell<AdminTableView, String> call(TableColumn<AdminTableView, String> param) {
                return new TableCell<AdminTableView, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if(getIndex()  < AdminComonData.getData().size())
                            setText(String.valueOf(getIndex() + 1));
                    }
                };
            }
        });
        columnStuId.setCellValueFactory(new PropertyValueFactory<>("stu_id"));
        columnStuClass.setCellValueFactory(new PropertyValueFactory<>("forClass"));
        columnStuName.setCellValueFactory(new PropertyValueFactory<>("stuName"));
        columnStuTotalGrade.setCellValueFactory(new PropertyValueFactory<>("totalGrade"));
        columnStuClassRank.setCellValueFactory(new PropertyValueFactory<>("classRank"));
        columnStuMajorRank.setCellValueFactory(new PropertyValueFactory<>("majorRank"));
    }


}

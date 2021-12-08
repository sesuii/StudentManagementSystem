package com.krill_song;

/*________________________
 @ Author: _Krill
 @ Data: 2021/10/31 22:03 
 @ Version: 1.0
__________________________*/

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dbcp.DBCPUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class AdminController implements Initializable {

    public static ObservableList<AdminViewData> adminViewData = FXCollections.observableArrayList();
    @FXML
    private FlowPane centerFlowPane;
    @FXML
    private Button loadingStudentButton;
    @FXML
    private Button chooseFileButton;
    @FXML
    private TextField csvFileTextField = null;
    @FXML
    private JFXTextField searchTextField;
    @FXML
    private JFXTextField newStudentIdText;
    @FXML
    private JFXTextField newStudentText;
    @FXML
    private JFXToggleButton isGroupTogButton;
    @FXML
    private JFXComboBox<String> NewStudentClassBox;
    private AdminViewData clickedRow = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        NewStudentClassBox.getItems().addAll("软件工程201", "软件工程202", "软件工程203", "软件工程204", "软件工程205", "软件工程206");
        iniTreeTableView();

    }
    /**
    * @param: [event]
    * @return: void
    * @description:添加学生信息
    * @date:2021/12/8
    **/
    @FXML
    void addNewOnAction(MouseEvent event) {
        if(NewStudentClassBox.getSelectionModel().isEmpty() || newStudentText.getText().isEmpty() || newStudentIdText.getText().isEmpty()) return;
        String forClass = NewStudentClassBox.getSelectionModel().getSelectedItem();
        AdminViewData newData = new AdminViewData(newStudentIdText.getText(), newStudentText.getText(), forClass, -1, -1, -1);
        adminViewData.add(newData);
        String sql = "insert into student_user() Values(?, ?, ?, ?, ?);";
        try {
            int updateData = DBCPUtil.insert(sql, newData);
            System.out.println("添加学生数：" + updateData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        newStudentText.clear();
        newStudentIdText.clear();
        NewStudentClassBox.getSelectionModel().clearSelection();
    }
    /**
    * @param: [event]
    * @return: void
    * @description:删除选中学生
    * @date:2021/12/8
    **/
    @FXML
    void deleteStudentOnAction(MouseEvent event) {
        if(clickedRow != null && adminViewData.contains(clickedRow)) adminViewData.remove(clickedRow);
        String sql = "delete from student_user where account_id = ?";
        try {
            DBCPUtil.delete(sql, clickedRow);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void refreshViewOnAction(MouseEvent event) {
        loadViewDate();
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
        if (file.exists()) csvFileTextField.setText(String.valueOf(file));
    }

    /**
     * @param: [event]
     * @return: void
     * @description: 获取并导入 csv 文件到数据库
     * @date:2021/12/8
     **/
    @FXML
    void loadingStudentOnAction(ActionEvent event) {
        if (csvFileTextField.getText().isEmpty()) return;
        String fileName = csvFileTextField.getText();
        fileName = fileName.replace('\\', '/');
        System.out.println(fileName);
        String sql = "load data local infile " + "'" + fileName + "'" +
                " into table student_user fields terminated by',' lines terminated by '\\r\\n';";
        int loadDateCount = DBCPUtil.loadingFile(sql);
        System.out.println("导入数据成功！共导入 " + loadDateCount + "条数据");
    }

    private void iniTreeTableView() {
        JFXTreeTableColumn<AdminViewData, String> studentIdColumn = new JFXTreeTableColumn<>("学号");
        JFXTreeTableColumn<AdminViewData, String> forClassColumn = new JFXTreeTableColumn<>("班级");
        JFXTreeTableColumn<AdminViewData, String> studentNameColumn = new JFXTreeTableColumn<>("姓名");
        JFXTreeTableColumn<AdminViewData, Number> totalGradeColumn = new JFXTreeTableColumn<>("总成绩");
        JFXTreeTableColumn<AdminViewData, Number> classRankColumn = new JFXTreeTableColumn<>("班级排名");
        JFXTreeTableColumn<AdminViewData, Number> majorRankColumn = new JFXTreeTableColumn<>("专业排名");
        iniStringColumn(studentIdColumn, forClassColumn, studentNameColumn);
        iniNumberColumn(totalGradeColumn, classRankColumn, majorRankColumn);

        // 载入数据
        loadViewDate();

        TreeItem<AdminViewData> root = new RecursiveTreeItem<>(adminViewData, RecursiveTreeObject::getChildren);
        JFXTreeTableView<AdminViewData> treeView = new JFXTreeTableView<>(root);
        treeView.setShowRoot(false);
        treeView.getColumns().setAll(studentIdColumn, forClassColumn, studentNameColumn, totalGradeColumn, classRankColumn, majorRankColumn);
        treeView.prefWidthProperty().bind(centerFlowPane.widthProperty());
        treeView.prefHeightProperty().bind(centerFlowPane.heightProperty());
        centerFlowPane.getChildren().add(treeView);
        treeView.getStylesheets().add(AdminController.class.getResource("/styles/studentStyle.css").toExternalForm());
        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                TreeItem<AdminViewData> currentSelectItem = (TreeItem<AdminViewData>) newValue;
                if (currentSelectItem != null) {
                    clickedRow = currentSelectItem.getValue();
                }
            }
        });
        // 检索表treeView中的数据
        searchTextField.textProperty().addListener((o, oldVal, newVal) -> {
            treeView.setPredicate(adminViewProp -> {
                AdminViewData data = adminViewProp.getValue();
                return data.stu_id.get().contains(newVal)
                        || data.forClass.get().contains(newVal)
                        || data.stuName.get().contains(newVal);
            });
        });

        isGroupTogButton.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(t1) {
                    new Thread(() -> treeView.group(forClassColumn)).start();
                }
                else treeView.unGroup(forClassColumn);
            }
        });
    }
    /**
    * @param: [com.jfoenix.controls.JFXTreeTableColumn<com.krill_song.AdminViewData,java.lang.Number>...]
    * @return: void
    * @description: 初始化表中的Number类及其子类的属性列
    * @date:2021/12/8
    **/
    private void iniNumberColumn(JFXTreeTableColumn<AdminViewData, Number> ...column) {
        for (int i = 0; i < column.length; i++) {
            column[i].setPrefWidth(160);
            int finalI = i;
            column[i].setCellValueFactory((TreeTableColumn.CellDataFeatures<AdminViewData, Number> param) -> {
                if (column[finalI].validateValue(param)) {
                    return param.getValue().getValue().getIndexForInt(finalI);
                } else {
                    return column[finalI].getComputedValue(param);
                }
            });
        }
    }
    /**
    * @param: [com.jfoenix.controls.JFXTreeTableColumn<com.krill_song.AdminViewData,java.lang.String>...]
    * @return: void
    * @description: 初始化表中的String类的属性列
    * @date:2021/12/8
    **/
    private void iniStringColumn(JFXTreeTableColumn<AdminViewData, String>... column) {
        for (int i = 0; i < column.length; i++) {
            column[i].setPrefWidth(160);
            if (i > 0) column[i].setSortable(false);
            int finalI = i;
            column[i].setCellValueFactory((TreeTableColumn.CellDataFeatures<AdminViewData, String> param) -> {
                if (column[finalI].validateValue(param)) {
                    return param.getValue().getValue().getIndex(finalI);
                } else {
                    return column[finalI].getComputedValue(param);
                }
            });
        }
    }

    /**
    * @param: []
    * @return: void
    * @description: 载入初始数据
    * @date:2021/12/8
    **/
    private void loadViewDate() {
        String sql = "select *, RANK() OVER(PARTITION BY for_class ORDER BY total_grade DESC) AS classRank, RANK() OVER(ORDER BY total_grade DESC) AS majorRank from student_user;";
        try {
            ArrayList<AdminViewData> list = (ArrayList<AdminViewData>) DBCPUtil.queryPlural(sql);
            for (AdminViewData data : list) {
                if(!adminViewData.contains(data)) adminViewData.add(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



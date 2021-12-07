package com.krill_song;

import all_users.StudentUser;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

/*________________________
 @ Author: _Krill
 @ Data: 2021/11/1 23:45 
 @ Version: 1.0
__________________________*/
public class AdminTableView implements Serializable {
    SimpleStringProperty stu_id;
    SimpleStringProperty stuName;
    SimpleStringProperty forClass;
    SimpleIntegerProperty totalGrade;
    SimpleIntegerProperty classRank;
    SimpleIntegerProperty majorRank;
    public AdminTableView() {
    }
    public AdminTableView(String stu_id, String forClass, String stuName) {
        this.stu_id = new SimpleStringProperty(stu_id);
        this.forClass = new SimpleStringProperty(forClass);
        this.stuName = new SimpleStringProperty(stuName);
        totalGrade = new SimpleIntegerProperty(0);
        classRank = new SimpleIntegerProperty(0);
        majorRank = new SimpleIntegerProperty( 0);
    }

    public String getStu_id() {
        return stu_id.get();
    }

    public SimpleStringProperty stu_idProperty() {
        return stu_id;
    }

    public void setStu_id(String stu_id) {
        this.stu_id.set(stu_id);
    }

    public String getStuName() {
        return stuName.get();
    }

    public SimpleStringProperty stuNameProperty() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName.set(stuName);
    }

    public String getForClass() {
        return forClass.get();
    }

    public SimpleStringProperty forClassProperty() {
        return forClass;
    }

    public void setForClass(String forClass) {
        this.forClass.set(forClass);
    }

    public int getTotalGrade() {
        return totalGrade.get();
    }

    public SimpleIntegerProperty totalGradeProperty() {
        return totalGrade;
    }

    public void setTotalGrade(int totalGrade) {
        this.totalGrade.set(totalGrade);
    }

    public int getClassRank() {
        return classRank.get();
    }

    public SimpleIntegerProperty classRankProperty() {
        return classRank;
    }

    public void setClassRank(int classRank) {
        this.classRank.set(classRank);
    }

    public int getMajorRank() {
        return majorRank.get();
    }

    public SimpleIntegerProperty majorRankProperty() {
        return majorRank;
    }

    public void setMajorRank(int majorRank) {
        this.majorRank.set(majorRank);
    }


}

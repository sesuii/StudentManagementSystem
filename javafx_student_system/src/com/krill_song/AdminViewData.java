package com.krill_song;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

/*________________________
 @ Author: _Krill
 @ Data: 2021/11/1 23:45 
 @ Version: 1.0
__________________________*/
public class AdminViewData extends RecursiveTreeObject<AdminViewData> {
    SimpleStringProperty stu_id;
    SimpleStringProperty stuName;
    SimpleStringProperty forClass;
    SimpleIntegerProperty totalGrade;
    SimpleIntegerProperty classRank;
    SimpleIntegerProperty majorRank;

    public AdminViewData(String stu_id, String forClass, String stuName, int totalGrade, int classRank, int majorRank) {
        this.stu_id = new SimpleStringProperty(stu_id);
        this.forClass = new SimpleStringProperty(forClass);
        this.stuName = new SimpleStringProperty(stuName);
        if(totalGrade == -1) this.totalGrade = null;
            else this.totalGrade = new SimpleIntegerProperty(totalGrade);
        if(classRank == -1) this.classRank = null;
            else this.classRank = new SimpleIntegerProperty(classRank);
        if(majorRank == -1) this.majorRank = null;
            else this.majorRank = new SimpleIntegerProperty(majorRank);
    }
    public ObservableValue<String> getIndex(int finalI) {
        switch (finalI) {
            case 0:
                return stu_id;
            case 1:
                return forClass;
            case 2:
                return stuName;
            default:
                return null;
        }
    }

    public ObservableValue<Number> getIndexForInt(int finalI) {
        switch (finalI){
            case 0: return totalGrade;
            case 1: return classRank;
            case 2: return majorRank;
            default: return null;
        }
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

    @Override
    public String toString() {
        return "AdminViewData{" +
                "stu_id=" + stu_id +
                ", stuName=" + stuName +
                ", forClass=" + forClass +
                ", totalGrade=" + totalGrade +
                ", classRank=" + classRank +
                ", majorRank=" + majorRank +
                '}';
    }
}

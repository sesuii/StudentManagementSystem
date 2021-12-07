package com.krill_song;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/*________________________
 @ Author: _Krill
 @ Data: 2021/11/4 0:26 
 @ Version: 1.0
__________________________*/
public class AdminComonData {
    private static ObservableList<AdminTableView> data;
    public static ObservableList<AdminTableView> getData() {
        return data == null ? data = FXCollections.observableArrayList() : data;
    }
}

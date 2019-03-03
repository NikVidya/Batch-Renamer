package com.github.nikvidya.batchrenamer.main;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Model {
    private ObservableList<File> fileList;
    private String path;

    public Model(){
        fileList = FXCollections.observableArrayList();
        path = "";
    }

    public ObservableList<File> getFileList() {
        if (fileList == null) {
            fileList = FXCollections.observableArrayList();
        }
        return fileList;
    }

    /**
     * Returns a list of file names from the file list
     * @return ArrayList<String>
     */
    public ArrayList<String> getFileListNames() {
        ArrayList<String> res = new ArrayList<>();
        for (Iterator<File> f = fileList.iterator(); f.hasNext(); ) {
            res.add(f.next().getName());
        }
        return res;
    }
    public void setFileList(List<File> l) {
        fileList = FXCollections.observableArrayList(l);
    }
    public boolean checkFileListNull() {
        return fileList == null;
    }
    public String getPath() {
        if (path == null) {
            path = "";
        }
        return path;
    }
    public void setPath() {
        if (!checkFileListNull()) {
            path = fileList.get(0).getAbsolutePath().substring(0, fileList.get(0).getAbsolutePath().lastIndexOf(File.separator));
        }
    }
}

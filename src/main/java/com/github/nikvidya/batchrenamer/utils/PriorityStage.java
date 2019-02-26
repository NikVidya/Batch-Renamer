package com.github.nikvidya.batchrenamer.utils;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.*;

/**
 * Superclass for windows that do not return control to the caller until closed
 */
public abstract class PriorityStage {

    protected Stage stage;

    public PriorityStage(String title, int w, int h) {
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setWidth(w);
        stage.setHeight(h);
    }
    public PriorityStage(String title, int w, int h, Boolean absoluteWidth){
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        if (absoluteWidth) {
            stage.setWidth(w);
            stage.setHeight(h);
        } else {
            stage.setMaxWidth(w);
            stage.setMaxHeight(h);
        }
    }
    public PriorityStage(String title) {
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setWidth(Constants.dimens.WIN_W * 0.75);
        stage.setHeight(Constants.dimens.WIN_H * 0.75);
    }
    public PriorityStage(String title, Boolean absoluteWidth){
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        if (absoluteWidth) {
            stage.setWidth(Constants.dimens.WIN_W * 0.75);
            stage.setHeight(Constants.dimens.WIN_H * 0.75);
        } else {
            stage.setMaxWidth(Constants.dimens.WIN_W * 0.75);
            stage.setMaxHeight(Constants.dimens.WIN_H * 0.75);
        }
    }
    public PriorityStage() {
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("priority stage");
        stage.setWidth(Constants.dimens.WIN_W * 0.75);
        stage.setHeight(Constants.dimens.WIN_H * 0.75);
    }
    public PriorityStage(boolean absoluteWidth) {
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("priority stage");
        if (absoluteWidth) {
            stage.setWidth(Constants.dimens.WIN_W * 0.75);
            stage.setHeight(Constants.dimens.WIN_H * 0.75);
        } else {
            stage.setMaxWidth(Constants.dimens.WIN_W * 0.75);
            stage.setMaxHeight(Constants.dimens.WIN_H * 0.75);
        }
    }

    /**
     * Sets the layout and scene, and then displays the stage via showAndWait()
     */
    public abstract void show();
//    { Example
//        VBox layout = new VBox(10);
//        Scene scene = new Scene(layout);
//        stage.setScene(scene);
//        stage.showAndWait();
//    }
}

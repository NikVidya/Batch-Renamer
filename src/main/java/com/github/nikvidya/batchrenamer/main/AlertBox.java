package com.github.nikvidya.batchrenamer.main;

import com.github.nikvidya.batchrenamer.utils.Constants;
import com.github.nikvidya.batchrenamer.utils.PriorityStage;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Alert box for errors and other messages
 */
public class AlertBox extends PriorityStage {
    public AlertBox() {
        super("Alert", (int) Math.floor(Constants.dimens.WIN_W * 0.5), (int) Math.floor(Constants.dimens.WIN_H * 0.5), false);
    }

    @Override
    public void show() {
        VBox layout = new VBox();

        Label label = new Label("Alert");
        Button okButton = new Button("OK");
        okButton.setOnAction(e -> stage.close());

        layout.setSpacing(10);
        layout.setPadding(new Insets(15, 15, 15, 15));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(label, okButton);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void show(String message) {
        VBox layout = new VBox();

        Label label = new Label(message);
        Button okButton = new Button("OK");
        okButton.setOnAction(e -> stage.close());

        layout.setSpacing(10);
        layout.setPadding(new Insets(15, 15, 15, 15));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(label, okButton);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.showAndWait();
    }
}

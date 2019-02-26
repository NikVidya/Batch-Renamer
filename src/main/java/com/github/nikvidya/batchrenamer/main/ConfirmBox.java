package com.github.nikvidya.batchrenamer.main;

import com.github.nikvidya.batchrenamer.utils.Constants;
import com.github.nikvidya.batchrenamer.utils.PriorityStage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * Confirmation box so the user is not locked into a choice right away
 */
public class ConfirmBox  extends PriorityStage {
    private boolean answer;
    public ConfirmBox() {
        super("Alert", (int) Math.floor(Constants.dimens.WIN_W * 0.5), (int) Math.floor(Constants.dimens.WIN_H * 0.5), false);
    }
    @Override
    public void show() {
        BorderPane layout = new BorderPane();
        HBox buttonLayout = new HBox();

        Label label = new Label("Alert");
        Button okButton = new Button("OK");
        okButton.setOnAction(e -> {
            answer = true;
            stage.close();
        });
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> {
            answer = false;
            stage.close();
        });

        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.setSpacing(10);
        buttonLayout.getChildren().addAll(okButton, cancelButton);

        layout.setPadding(new Insets(15, 15, 15, 15));
        layout.setCenter(label);
        layout.setBottom(buttonLayout);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.showAndWait();
    }
    public boolean show(String message) {
        BorderPane layout = new BorderPane();
        HBox buttonLayout = new HBox();

        Label label = new Label(message);
        label.setPadding(new Insets(10,10,10,10));
        Button okButton = new Button("OK");
        okButton.setOnAction(e -> {
            answer = true;
            stage.close();
        });
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> {
            answer = false;
            stage.close();
        });

        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.setSpacing(10);
        buttonLayout.getChildren().addAll(okButton, cancelButton);

        layout.setPadding(new Insets(15, 15, 15, 15));
        layout.setCenter(label);
        layout.setBottom(buttonLayout);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.showAndWait();
        return answer;
    }
}

package com.github.nikvidya.batchrenamer.toolmenus;

import com.github.nikvidya.batchrenamer.utils.PriorityStage;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * Settings menu
 */
public class SettingsMenu extends PriorityStage {
    public SettingsMenu() {
        super("Settings");
    }

    public void show() {
        // Layout of the scene
        BorderPane bp = new BorderPane();

        // Settings menus
        VBox leftMenu = new VBox();
        leftMenu.setFillWidth(false);
        VBox generalMenu = new VBox();
        VBox appearanceMenu = new VBox();
        bp.setLeft(leftMenu);
        bp.setCenter(generalMenu);

        // Settings menu items
        TreeItem<String> generalRoot = new TreeItem<String>("General");
        generalRoot.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> bp.setCenter(generalMenu));
        TreeItem<String> appearanceRoot = new TreeItem<String>("Appearance");
        appearanceRoot.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> bp.setCenter(appearanceMenu));


        // Assemble the settings menu tree
        TreeItem<String> settingsRoot = new TreeItem<String>("Settings");
        settingsRoot.getChildren().add(generalRoot);
        settingsRoot.getChildren().add(appearanceRoot);
        settingsRoot.setExpanded(true);
        TreeView<String> tree = new TreeView<String>(settingsRoot);
        tree.setShowRoot(false);

        // Add functionality to tree items
        generalRoot.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> System.out.println("Clicked"));

        leftMenu.getChildren().add(tree);
        generalMenu.getChildren().add(new Button("General"));
        appearanceMenu.getChildren().add(new Button("Appearance"));

        Scene scene = new Scene(bp);
        stage.setScene(scene);
        stage.showAndWait();
    }
}

package com.github.nikvidya.batchrenamer.main;

import com.github.nikvidya.batchrenamer.toolmenus.SettingsMenu;
import com.github.nikvidya.batchrenamer.utils.Constants;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Entry point for the program
 * TODO: Make user-defined variables
 * TODO: Offload main functions to other classes according to MVC
 * TODO: Add feedback and preview panels so user knows how the renaming scheme will look
 * TODO: Add undo button to reverse the rename
 * TODO: Add tooltips (instead of long bracketed notes in the labels)
 */
public class Main extends Application {
    // layout and interfaces
    private BorderPane layout;
    private VBox controlPane;
    private SettingsMenu settingsMenu;

    // top menu bar
    private MenuBar menuBar;
    private Menu fileMenu;
    private MenuItem settingsMenuItem;
    private MenuItem exitMenuItem;

    // file chooser
    private Button fileChooseButton;
    private Label filePathLabel;
    private FileChooser fileChooser;
    private List<File> fileList;

    // main controls
    private TextField mainTextField;
    private TextField extensionTextField;
    private Button renameButton;

    String filePath;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Batch Renamer");

        // Settings menu popup
        settingsMenu = new SettingsMenu();

        // Top menu items
        // File Menu
        fileMenu = new Menu("File");
        settingsMenuItem = new MenuItem("Settings...");
        settingsMenuItem.setOnAction(e -> settingsMenu.show());
        exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(e -> primaryStage.close());

        // Assemble File Menu
        fileMenu.getItems().add(settingsMenuItem);
        fileMenu.getItems().add(new SeparatorMenuItem());
        fileMenu.getItems().add(exitMenuItem);

        // Top menu bar
        menuBar = new MenuBar();
        menuBar.getMenus().add(fileMenu);

        // Controls
        fileChooseButton = new Button("Select Files");
        filePathLabel = new Label("");
        fileChooser = new FileChooser();
        fileChooser.setTitle("Select files to batch rename");
        fileChooseButton.setOnAction(e -> {
            fileList = fileChooser.showOpenMultipleDialog(primaryStage);
            if (fileList != null) {
                filePath = fileList.get(0).getAbsolutePath().substring(0, fileList.get(0).getAbsolutePath().lastIndexOf(File.separator));
                filePathLabel.setText("Files in: " + filePath);
                for (Iterator<File> f = fileList.iterator(); f.hasNext(); ) {
                    String name = f.next().getName();
                    System.out.println(name);
                }
            }
        });

        // Main text field, what to rename files to
        Label mainTextFieldLabel = new Label("Rename files according to this scheme (file names will iterate by number): ");
        mainTextField = new TextField();
        mainTextField.setMaxWidth(Constants.dimens.WIN_W * 0.75);
        Label extensionTextFieldLabel = new Label("Using this extension (leave blank to leave extensions as-is):");

        // Extension text field, to set a new file extension (optional)
        extensionTextField = new TextField("");
        extensionTextField.setMaxWidth(Constants.dimens.WIN_W * 0.3);

        // Confirm rename
        renameButton = new Button("Rename Files");
        renameButton.setOnAction(e -> {
            ConfirmBox confirm = new ConfirmBox();
            if (confirm.show("Rename the selected files?")) {
                // temporary iterator to give distinct file names
                int iterator = 0;
                // iterate through the file list
                if (fileList != null) {
                    for (Iterator<File> f = fileList.iterator(); f.hasNext(); ) {
                        // so we're not calling f.next() more than once per iteration
                        File currentFile = f.next();
                        // find the file extension, if it exists
                        String[] fileExtensionArray = currentFile.getName().split("\\.");
                        String extension;
                        // if there was no extension, do not set a default
                        if (fileExtensionArray.length < 2) {
                            extension = "";
                        } else {
                            extension = fileExtensionArray[fileExtensionArray.length - 1];
                        }
                        // if user has set a new extension, use that minus any . characters
                        if (!extensionTextField.getText().equals("")) {
                            if (extensionTextField.getText().charAt(0) == ('.')) {
                                extension = extensionTextField.getText().substring(1); // "tar.gz" instead of ".tar.gz"
                            } else {
                                extension = extensionTextField.getText();
                            }
                        }
                        File n = new File(filePath + File.separator + mainTextField.getText() + iterator + "." + extension);
                        if (currentFile.renameTo(n)) {
                            System.out.println("Success");
                        } else {
                            AlertBox alert = new AlertBox();
                            alert.show("Error in renaming files");
                        }
                        iterator++;
                    }
                } else {
                    AlertBox alert = new AlertBox();
                    alert.show("No files selected, please select files");
                }
            }
        });

        // Assemble main stage
        layout = new BorderPane();
        layout.setTop(menuBar);

        controlPane = new VBox();
        controlPane.setPadding(new Insets(15, 15, 15, 15));
        controlPane.setSpacing(10);

        HBox fileChooseLayout = new HBox();
        fileChooseLayout.setSpacing(10);
        fileChooseLayout.getChildren().addAll(fileChooseButton, filePathLabel);

        controlPane.getChildren().addAll(fileChooseLayout, mainTextFieldLabel, mainTextField, extensionTextFieldLabel, extensionTextField, renameButton);

        layout.setCenter(controlPane);

        primaryStage.setScene(new Scene(layout, Constants.dimens.WIN_W, Constants.dimens.WIN_H));
        primaryStage.show();
    }
}

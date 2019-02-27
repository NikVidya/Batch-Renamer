package com.github.nikvidya.batchrenamer.main;

import com.github.nikvidya.batchrenamer.toolmenus.SettingsMenu;
import com.github.nikvidya.batchrenamer.utils.Constants;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

/**
 * Entry point for the program, acts as controller for the program
 * TODO: Make user-defined variables
 * TODO: Add feedback and preview panels so user knows how the renaming scheme will look (View)
 * TODO: Add undo button to reverse the rename
 * TODO: Add tooltips (instead of long bracketed notes in the labels)
 */
public class Main extends Application {
    private Model model;

    // layout and interfaces
    private BorderPane layout;
    private VBox controlPane;
    private VBox filePreviewPane;
    private HBox filePreviewListPane;
    private SettingsMenu settingsMenu;
    private ListView<File> fileListView;
    private ListView<String> previewListView;

    // top menu bar
    private MenuBar menuBar;
    private Menu fileMenu;
    private MenuItem settingsMenuItem;
    private MenuItem exitMenuItem;

    // file chooser
    private Button fileChooseButton;
    private Label filePathLabel;
    private FileChooser fileChooser;

    // main controls
    private TextField mainTextField;
    private TextField extensionTextField;
    private Button renameButton;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Batch Renamer");

        model = new Model();


        // Top menu items
        // File Menu
        fileMenu = new Menu("File");
        settingsMenuItem = new MenuItem("Settings...");
        settingsMenuItem.setOnAction(e -> settingsMenu.show());
        exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(e -> primaryStage.close());

        // Settings menu
        settingsMenu = new SettingsMenu();

        // Assemble File Menu
        fileMenu.getItems().add(settingsMenuItem);
        fileMenu.getItems().add(new SeparatorMenuItem());
        fileMenu.getItems().add(exitMenuItem);

        // Top menu bar
        menuBar = new MenuBar();
        menuBar.getMenus().add(fileMenu);

        // File rename previews
        fileListView = new ListView<>();
        fileListView.setMaxWidth(150);
        fileListView.setCellFactory(lv -> new ListCell<File>() {
            @Override
            public void updateItem(File item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });
        previewListView = new ListView<>();
        previewListView.setMaxWidth(150);
        previewListView.setCellFactory(lv -> new ListCell<String>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item);
                }
            }
        });

        // Controls
        fileChooseButton = new Button("Select Files");
        filePathLabel = new Label("");
        fileChooser = new FileChooser();
        fileChooser.setTitle("Select files to batch rename");
        fileChooseButton.setOnAction(e -> {
            model.setFileList(fileChooser.showOpenMultipleDialog(primaryStage));
            if (model.getFileList() != null) {
                model.setPath();
                filePathLabel.setText(model.getFileList().size() + " Files in: " + model.getPath());
                for (Iterator<File> f = model.getFileList().iterator(); f.hasNext(); ) {
                    String name = f.next().getName();
                    System.out.println(name);
                }
                // Populate the ListViews
                fileListView.setItems(model.getFileList());
                UpdatePreviewList();
            }
        });

        // Main text field, what to rename files to
        Label mainTextFieldLabel = new Label("Rename files according to this scheme (file names will iterate by number): ");
        mainTextField = new TextField();
        mainTextField.setMaxWidth(Constants.dimens.WIN_W * 0.75);
        mainTextField.setOnKeyPressed(e -> UpdatePreviewList());
        Label extensionTextFieldLabel = new Label("Using this extension (leave blank to leave extensions as-is):");

        // Extension text field, to set a new file extension (optional)
        extensionTextField = new TextField("");
        extensionTextField.setMaxWidth(Constants.dimens.WIN_W * 0.3);

        // Confirm rename
        renameButton = new Button("Rename Files");
        renameButton.setOnAction(e -> {
            ConfirmBox confirm = new ConfirmBox();
            if (confirm.show("Rename the selected files?")) {
                // temporary iterator to provide distinct file names
                int iterator = 0;
                // iterate through the file list
                if (!model.checkFileListNull()) {
                    for (Iterator<File> f = model.getFileList().iterator(); f.hasNext(); ) {
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
                        File n = new File(model.getPath() + File.separator + mainTextField.getText() + iterator + "." + extension);
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

        controlPane.getChildren().addAll(fileChooseButton, filePathLabel, mainTextFieldLabel, mainTextField, extensionTextFieldLabel, extensionTextField, renameButton);

        filePreviewListPane = new HBox();
        filePreviewListPane.setPadding(new Insets(10, 10, 10, 10));
        filePreviewListPane.setSpacing(10);
        filePreviewListPane.getChildren().addAll(fileListView, previewListView);

        layout.setCenter(controlPane);
        layout.setRight(filePreviewListPane);

        primaryStage.setScene(new Scene(layout, Constants.dimens.WIN_W, Constants.dimens.WIN_H));
        primaryStage.show();
    }

    private void UpdatePreviewList() {
        ObservableList<String> previewList = FXCollections.observableArrayList(model.getFileListNames());
        // temporary iterator to provide distinct file names
        int iterator = 0;
        // iterate through the file list
        for (Iterator<String> f = previewList.iterator(); f.hasNext(); ) {
            // so we're not calling f.next() more than once per iteration
            String currentFile = f.next();
            // find the file extension, if it exists
            String[] fileExtensionArray = currentFile.split("\\.");
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
            File n = new File(model.getPath() + File.separator + mainTextField.getText() + iterator + "." + extension);
            currentFile = mainTextField.getText() + iterator + "." + extension;
            iterator++;
        }
        previewListView.setItems(previewList);
    }
}

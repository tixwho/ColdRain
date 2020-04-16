package gui.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import gui.MainApp;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import toolkit.LogMaker;

public class RootLayoutController {

    // Reference to the main application
    private MainApp mainApp;

    // textarea
    @FXML
    private TextArea console;
    private PrintStream ps;
    @FXML
    private ToggleGroup modeGroup;

    // initialize always called
    public void initialize() {
        ps = new PrintStream(new Console(console));
        System.setOut(ps);
        LogMaker.logs("hi");
    }
    
    //redirect output to textarea.
    public class Console extends OutputStream {
        private TextArea console;

        public Console(TextArea console) {
            this.console = console;
        }

        public void appendText(String valueOf) {
            Platform.runLater(() -> console.appendText(valueOf));
        }

        public void write(int b) throws IOException {
            appendText(String.valueOf((char) b));
        }
    }
    




    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    
    
    /*
     * Button Functions Below
     */
    
    /**
     * Creates an empty address book.
     */
    @FXML
    private void handleNew() {

    }

    /**
     * Opens a FileChooser to let the user select an address book to load.
     */
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter =
            new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.loadComparisonFromXML(file);
        }
    }

    /**
     * Saves the file to the person file that is currently open. If there is no open file, the "save
     * as" dialog is shown.
     */
    @FXML
    private void handleSave() {
        File personFile = mainApp.getPersonFilePath();
        if (personFile != null) {
            mainApp.saveComparisonToXML(personFile);
        } else {
            handleSaveAs();
        }
    }

    /**
     * Opens a FileChooser to let the user select a file to save to.
     */
    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter =
            new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.saveComparisonToXML(file);
        }
    }

    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout() {
        LogMaker.logs("about!");
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.initOwner(this.mainApp.getPrimaryStage());
        alert.setTitle("Error Saving");
        alert.setHeaderText("About Info");
        alert.setContentText("Author: Marco Jakob\nWebsite: http://code.makery.ch");
        alert.showAndWait();
    }


    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }

    /*
     * Mode Selection Functions Below. Linked to MainApp.
     */
    
    @FXML
    private void showOverviewPage() {
        mainApp.showOverviewPage();
    }
    
    @FXML
    private void showDatabasePage() {
        mainApp.showDatabasePage();
    }


}

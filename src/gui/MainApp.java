package gui;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;
import gui.controller.DatabaseController;
import gui.controller.OverviewController;
import gui.controller.RootLayoutController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import toolkit.LogMaker;

public class MainApp extends Application{
    
    private Stage primaryStage;
    private BorderPane rootLayout;
    /*Constructor*/
    public MainApp() {
        
    }
    
    /*Expansions*/
    
    public void loadComparisonFromXML(File file) {
        
    }
    
    public void saveComparisonToXML(File file) {
        
    }
    
    /**
     * Returns the person file preference, i.e. the file that was last opened.
     * The preference is read from the OS specific registry. If no such
     * preference can be found, null is returned.
     * 
     * @return
     */
    public File getPersonFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /**
     * Sets the file path of the currently loaded file. The path is persisted in
     * the OS specific registry.
     * 
     * @param file the file or null to remove the path
     */
    public void setPersonFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Update the stage title.
            primaryStage.setTitle("AddressApp - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Update the stage title.
            primaryStage.setTitle("AddressApp");
        }
    }
    
    /*Getters*/
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    
    /* Initializatiing Methods*/



    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

        initRootLayout();
        showOverviewPage();
    }
    
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class
                    .getResource("controller/RootLayout.fxml"));//changed to fxml folder after allset.
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            System.out.println("TEST " + this);
            controller.setMainApp(this);

            primaryStage.show();
            
            /*
            // My test: adding functions to menu.
            // Result: Passing a stage is ok, but can not pass controller (NPE)
            // Reason: primaryStage can't be show before passing into controller.
            RootLayoutController controller= loader.getController();
            controller.setMainStage(this.primaryStage);
            */
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //TODO add method.
    public void showOverviewPage() {
        LogMaker.logs("Overview!");
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("controller/Overview.fxml"));
            VBox personOverview = (VBox) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);

            // Give the controller access to the main app.
            OverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //TODO add method.
    public void showDatabasePage() {
        LogMaker.logs("Database!");
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("controller/Database.fxml"));
            AnchorPane databaseOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(databaseOverview);

            // Give the controller access to the main app.
            DatabaseController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }

}

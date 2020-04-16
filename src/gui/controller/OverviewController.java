package gui.controller;

import gui.MainApp;

public class OverviewController {
    
 // Reference to the main application
    @SuppressWarnings("unused")
    private MainApp mainApp;
    
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

}

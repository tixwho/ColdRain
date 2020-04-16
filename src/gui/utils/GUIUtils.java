package gui.utils;

import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;

public class GUIUtils {
    
    public static String getAudioText(Toggle inputToggle) {
        RadioButton selectedRadioButton = (RadioButton) inputToggle;
        String textValue = selectedRadioButton.getText();
        return textValue;
    }

}

package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ErrorScreenController {

    @FXML
    private Button exitButton;

    // Method to exit the program when the error screen is exited
    @FXML
    void exitProgram(ActionEvent event) {
    	System.exit(0);
    }
}
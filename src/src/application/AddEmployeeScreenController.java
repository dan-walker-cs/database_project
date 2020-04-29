package application;

import java.io.IOException;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddEmployeeScreenController {
	// employee information text fields
    @FXML
    private TextField firstNameText;
    @FXML
    private TextField dobText;
    @FXML
    private TextField ssnText;
    @FXML
    private TextField middleInitialText;
    @FXML
    private TextField lastNameText;
    @FXML
    private TextField salaryText;
    @FXML
    private TextField addressText;
    @FXML
    private TextField superssnText;
    @FXML
    private TextField dnoText;
    @FXML
    private TextField sexText;
    @FXML
    private TextField emailText;
    
    @FXML
    private Button confirmButton;
    
    // key-value store to hold employee table information
	static HashMap<String, String> employeeInformation = new HashMap<String, String>();
    
    // this method stores the information from the addemployee screen locally
    @FXML
    void confirmEmployeeInformation(ActionEvent event) {
    	// store information from text fields
    	employeeInformation.put("fname", firstNameText.getText());
    	employeeInformation.put("minit", middleInitialText.getText());
    	employeeInformation.put("lname", lastNameText.getText());
    	employeeInformation.put("ssn", ssnText.getText());
    	employeeInformation.put("bdate", dobText.getText());
    	employeeInformation.put("address", addressText.getText());
    	employeeInformation.put("sex", sexText.getText());
    	employeeInformation.put("salary", salaryText.getText());
    	employeeInformation.put("superssn", superssnText.getText());
    	employeeInformation.put("dno", dnoText.getText());
    	employeeInformation.put("email", emailText.getText());
    	    	
    	displayAdditionalEmployeeInformationScreen(event);
    }
    
    // this method displays the additionalemployeeinformation screen
    @FXML
    void displayAdditionalEmployeeInformationScreen(ActionEvent event) {
    	try {
			Parent additionalEmployeeInformationParent = FXMLLoader.load(getClass().getResource("AdditionalEmployeeInformationScreen.fxml"));
			Scene additonalEmployeeInformationScene = new Scene(additionalEmployeeInformationParent, 600, 600);
			Stage applicationStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			
			applicationStage.setScene(additonalEmployeeInformationScene);
			applicationStage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static HashMap<String, String> getEmployeeInformation() {
    	return employeeInformation;
    }
}


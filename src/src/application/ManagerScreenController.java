package application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ManagerScreenController {
	@FXML
	private Button submitButton;
	@FXML
	private Button clearButton;
    @FXML
    private TextField ssnTextField;
    static QueryHandler sql_db = new QueryHandler();

    // This method controls the action of the Clear Button
	@FXML
	void clearTextField(ActionEvent event) {
		ssnTextField.clear();
	}
	
	// This method controls the action of the Submit Button
	@FXML
	void verifySSN(ActionEvent event) {
		boolean error = true;
		
		try {
			sql_db.initiateConnection();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		
		// collects the input from the SSN textfield
		String targetSSN = ssnTextField.getText();
		// holds the query to retrieve all employees without supervisors (aka managers)
		String ssnVerificationQuery = "select * from employee where superssn is null";
		// holds query results
		ArrayList<String> queryReport = new ArrayList<String>(sql_db.verifySSN(ssnVerificationQuery));
		
		// compares each collected query result against the target ssn
		for(int i = 0; i < queryReport.size(); i++) {
			// targetSSN is a manager's ssn
			if(targetSSN.equals(queryReport.get(i))) {
				displayAddEmployeeScreen(event);
				error = false;
			}
		}

		if(error)
			// popup error and close program
			displayErrorScreen(event);
	}
	
	// Method to display the addemployee screen
	@FXML
	void displayAddEmployeeScreen(ActionEvent event) {
		try {
			Parent addEmployeeParent = FXMLLoader.load(getClass().getResource("AddEmployeeScreen.fxml"));
			Scene addEmployeeScene = new Scene(addEmployeeParent, 600, 600);
			Stage applicationStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			
			applicationStage.setScene(addEmployeeScene);
			applicationStage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Method to display errors on the ManagerScreen
	@FXML
	void displayErrorScreen(ActionEvent event) {
		try {
			Parent errorScreenParent = FXMLLoader.load(getClass().getResource("ErrorScreen.fxml"));
			Scene errorScreenScene = new Scene(errorScreenParent, 600, 200);
			Stage applicationStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			
			applicationStage.setScene(errorScreenScene);
			applicationStage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


package application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdditionalEmployeeInformationScreenController {
	// text fields
    @FXML
    private TextField projectNameText;
    @FXML
    private TextField projectHoursText;
    @FXML
    private GridPane dependentFields;
    @FXML
    private TextField ssnText;
    @FXML
    private TextField nameText;
    @FXML
    private TextField sexText;
    @FXML
    private TextField dobText;
    @FXML
    private TextField relationshipText;
    
    // buttons
    @FXML
    private Button finishedButton;
    @FXML
    private Button finishedButton2;
    @FXML
    private Button addButton;
    @FXML
    private Button addButton2;
    @FXML
    private RadioButton dependentCheck;
    @FXML
    private RadioButton noDependentCheck;

    // error message
    @FXML
    private Text errorMessageText;
    
    // key-value store to hold current employee projects/dependents
    HashMap<String, String> employeeProject;
    HashMap<String, String> employeeDependent;
    static ArrayList<HashMap<String, String>> projects = new ArrayList<HashMap<String, String>>();
    static ArrayList<HashMap<String, String>> dependents = new ArrayList<HashMap<String, String>>();
    
    // running total of hours that current employee is working on projects
    int totalEmployeeHours = 0;
    
    // communication link to the database
    static QueryHandler sql_db = new QueryHandler();
    
    // Logic for the 'add' button
    @FXML
    void addEmployeeProject(ActionEvent event) {
    	// method control flow
    	boolean error = false;
    	// reset error message invisible
    	errorMessageText.setVisible(false);
    	
    	// tracks total hours worked by employee
    	totalEmployeeHours += Integer.parseInt(projectHoursText.getText());
    	// employee hours cannot exceed 40
    	if (totalEmployeeHours > 40) {
    		// show error message
    		projectErrorMessage();
    		// don't let erroneous data be added to hashMap
    		error = true;
    		// reset total hours
    		totalEmployeeHours = 0;
    		// reset hash map values
    		employeeProject.clear();
    		// clear text fields
    		projectNameText.clear();
    		projectHoursText.clear();
    	}
    	
    	// if no errors, add employee project data to hash map
    	if(!error) {
    		employeeProject = new HashMap<String, String>();
    		employeeProject.put("essn", AddEmployeeScreenController.getEmployeeInformation().get("ssn"));
    		employeeProject.put("pno", projectNameText.getText());
    		employeeProject.put("hours", projectHoursText.getText());
    		projects.add(employeeProject);
    	}
    	
    	// clear text fields
    	projectNameText.clear();
    	projectHoursText.clear();
    }
    
    // logic for the 'finished' button
    @FXML
    void finishedAddingProjects(ActionEvent event) {
    	// disable previous section's nodes
    	addButton.setDisable(true);
    	finishedButton.setDisable(true);
    	projectNameText.setDisable(true);
    	projectHoursText.setDisable(true);
    	
    	// enable new section's nodes
    	dependentCheck.setDisable(false);
    	noDependentCheck.setDisable(false);
    }
    
    // logic for the 'dependent' portion of the screen
    @FXML
    void dependentChecking(ActionEvent event) {
    	// toggles the visibility of adding dependent information
    	if(dependentCheck.isSelected()) {
    		dependentFields.setVisible(true);
    		addButton2.setVisible(true);
    		finishedButton2.setDisable(false);
    	} else if (noDependentCheck.isSelected()) {
    		dependentFields.setVisible(false);
    		finishedButton2.setDisable(false);
    	}
    }
    
    // logic for 'add' button in 'dependent' section
    @FXML
    void dependentAdd(ActionEvent event) {
    	employeeDependent = new HashMap<String, String>();
    	// add dependents to hashmap
    	employeeDependent.put("essn", AddEmployeeScreenController.getEmployeeInformation().get("ssn"));
    	employeeDependent.put("dependent_name", nameText.getText());
    	employeeDependent.put("sex", sexText.getText());
    	employeeDependent.put("bdate", dobText.getText());
    	employeeDependent.put("relationship", relationshipText.getText());
    	dependents.add(employeeDependent);
    	
    	// clear text fields
    	nameText.clear();
    	sexText.clear();
    	dobText.clear();
    	relationshipText.clear();
    }
    
    // logic for 'finished' button in 'dependent' section
    @FXML
    void dependentFinished(ActionEvent event) {
    	try {
			sql_db.initiateConnection();
			sql_db.updateEmployee(AddEmployeeScreenController.getEmployeeInformation());
			sql_db.updateWorksOn(projects);
			sql_db.updateDependent(dependents);
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	displaySummaryScreen(event);
    }
    
    // logic for the summary screen
    @FXML
    void displaySummaryScreen(ActionEvent event) {
    	try {
			Parent summaryParent = FXMLLoader.load(getClass().getResource("SummaryScreen.fxml"));
			Scene summaryScene = new Scene(summaryParent, 600, 600);
			Stage applicationStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			
			applicationStage.setScene(summaryScene);
			applicationStage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    // method to control error message visibility
    @FXML
    private void projectErrorMessage() {
    	// show error message
    	errorMessageText.setVisible(true);
    }
    
    // shares the key-value store of employee projects
    public static ArrayList<HashMap<String, String>> getEmployeeProjects() {
    	return projects;
    }
    
    // shares the key-value store of employee dependents
    public static ArrayList<HashMap<String, String>> getEmployeeDependents() {
    	return dependents;
    }
}

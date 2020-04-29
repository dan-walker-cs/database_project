package application;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;

public class SummaryScreenController {
	// text areas
    @FXML
    private TextArea dependent_nameText;
    @FXML
    private TextArea ssnText;
    @FXML
    private TextArea fnameText;
    @FXML
    private TextArea dependent_bdateText;
    @FXML
    private TextArea dependent_sexText;
    @FXML
    private TextArea minitText;
    @FXML
    private TextArea addressText;
    @FXML
    private TextArea emailText;
    @FXML
    private TextArea bdateText;
    @FXML
    private TextArea hoursText;
    @FXML
    private TextArea relationshipText;
    @FXML
    private TextArea lnameText;
    @FXML
    private TextArea salaryText;
    @FXML
    private Button endButton;
    @FXML
    private TextArea superssnText;
    @FXML
    private TextArea dnoText;
    @FXML
    private TextArea sexText;
    @FXML
    private TextArea projectText;
    @FXML
    private Text projectNum;
    @FXML
    private Text projectHours;
    
    // tabs
    @FXML
    private Tab employeeTab;
    @FXML
    private Tab projectTab;
    @FXML
    private Tab dependentTab;
    
    @FXML
    private GridPane gridPane;
    @FXML
    private GridPane gridPane2;

    // counters for tab events
    static private int projectsTabCount = 0;
    static private int dependentsTabCount = 0;
    
    // logic for displaying employee information
    @FXML
    void displayEmployeeInformation(Event event) {
    	// get entered employee information
    	HashMap<String, String> info = AddEmployeeScreenController.getEmployeeInformation();
    	// display to text areas
    	fnameText.setText(info.get("fname"));
    	minitText.setText(info.get("minit"));
    	lnameText.setText(info.get("lname"));
    	ssnText.setText(info.get("ssn"));
    	bdateText.setText(info.get("bdate"));
    	addressText.setText(info.get("address"));
    	sexText.setText(info.get("sex"));
    	salaryText.setText(info.get("salary"));
    	superssnText.setText(info.get("superssn"));
    	dnoText.setText(info.get("dno"));
    	emailText.setText(info.get("email"));
    }
    
    // logic for displaying employee's projects' information
    @FXML
    void displayProjectInformation(Event event) {
    	// tab already populated
    	if(projectsTabCount != 0)
    		return;
    	
    	// get entered project information
    	ArrayList<HashMap<String, String>> projects = AdditionalEmployeeInformationScreenController.getEmployeeProjects();
    	
    	// for each project entered
    	for(int i = 0; i < projects.size(); i++) {
    		// if no projects entered
    		if(projects.isEmpty()) {
    			break;
    		// for the first project (text areas already in window)
    		} else if(i == 0) {
    			projectText.setText(projects.get(i).get("pno"));
    			hoursText.setText(projects.get(i).get("hours"));
    			continue;
    		}
    		// dynamically add more rows to the display for multiple projects
    		gridPane.addRow(2*i, new Text("Project #: "), new TextArea(projects.get(i).get("pno")));
    		gridPane.addRow((2*i) + 1, new Text("Hours: "), new TextArea(projects.get(i).get("hours")));
    	}
    	// fix row sizing
    	for(int i = 0; i < gridPane2.getRowCount(); i++) {
    		RowConstraints constraint = new RowConstraints();
    		constraint.setPercentHeight(100.0 / (gridPane.getRowCount()));
    		gridPane.getRowConstraints().add(constraint);
    	}
    	
    	projectsTabCount += 1;
    }
    
    // logic for displaying employee's dependents' information
    @FXML 
    void displayDependentInformation(Event event) {
    	// tab already populated
    	if(dependentsTabCount != 0) 
    		return;
    	
    	// get entered dependent information
    	ArrayList<HashMap<String, String>> dependents = AdditionalEmployeeInformationScreenController.getEmployeeDependents();
    	
    	// for each dependent entered
    	for(int i = 0; i < dependents.size(); i++) {
    		// if no dependents entered
    		if(dependents.isEmpty()) {
    			break;
    		// for the first dependent entered (text areas already present in pane)
    		} else if(i == 0) {
    			dependent_nameText.setText(dependents.get(i).get("dependent_name"));
    			dependent_sexText.setText(dependents.get(i).get("sex"));
    			dependent_bdateText.setText(dependents.get(i).get("bdate"));
    			relationshipText.setText(dependents.get(i).get("relationship"));
    			continue;
    		}
    		
    		// dynamically add new rows for multiple dependents
    		gridPane2.addRow(4*i, new Text("Name: "), new TextArea(dependents.get(i).get("dependent_name")));
    		gridPane2.addRow((4*i) + 1, new Text("Sex: "), new TextArea(dependents.get(i).get("sex")));
    		gridPane2.addRow((4*i) + 2, new Text("DOB: "), new TextArea(dependents.get(i).get("bdate")));
    		gridPane2.addRow((4*i) + 3, new Text("Relationship: "), new TextArea(dependents.get(i).get("relationship")));
    	}
    	// fix row sizing
    	for(int i = 0; i < gridPane2.getRowCount(); i++) {
    		RowConstraints constraint = new RowConstraints();
    		constraint.setPercentHeight(100.0 / (gridPane2.getRowCount()));
    		//gridPane2.getRowConstraints().add(constraint);
    	}
    	
    	dependentsTabCount += 1;
    }
    
    // logic for the button
    @FXML
    void endProgramButton(ActionEvent event) {
    	System.exit(0);
    }
}


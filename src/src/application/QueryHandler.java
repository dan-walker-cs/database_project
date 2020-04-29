package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class QueryHandler {
	private static String DRIVERNAME = "oracle.jdbc.driver.OracleDriver";
	private static String DBINFO = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
	private static String DBUSERNAME = "dwalke20";
	private static String DBPASSW = "itheephi";
	Connection conn;
	
	// method to initiate connection with the DB
	public void initiateConnection() throws SQLException, IOException {
		try {
			Class.forName(DRIVERNAME);
		} catch (ClassNotFoundException e) {
			System.out.println("Driver could not be loaded.");
			e.printStackTrace();
		}
		
		this.conn = DriverManager.getConnection(DBINFO, DBUSERNAME, DBPASSW);
	}
	
	// method to verify employee management status from ssn textfield
	public ArrayList<String> verifySSN(String ssnQuery) {
		// Arraylist object to hold the formatted result set returned by the DB query
		ArrayList<String> formattedResult = new ArrayList<String>();
				
		// try/catch block for SQLexceptions
		// creates a new statement
		try (Statement stmt = conn.createStatement()) {
			// captures the result set received from the executed query
			ResultSet queryResults = stmt.executeQuery(ssnQuery);
					
			// parses the result set into the arraylist object
			while (queryResults.next()) {
				// columns of interest are last name and ssn
				formattedResult.add(queryResults.getString("ssn"));
			}
		} catch (SQLException e) {
			System.out.println("Query failed.");
			e.printStackTrace();
		}
		// return formatted results of query
		return formattedResult;
	}
	
	// update employee table with new employee
	public void updateEmployee(HashMap<String, String> employeeInformation) {
		// extract information to store
		String fname = employeeInformation.get("fname");
		String minit = employeeInformation.get("minit");
		String lname = employeeInformation.get("lname");
		String ssn = employeeInformation.get("ssn");
		String bdate = employeeInformation.get("bdate");
		String address = employeeInformation.get("address");
		String sex = employeeInformation.get("sex");
		String salary = employeeInformation.get("salary");
		String superssn = employeeInformation.get("superssn");
		String dno = employeeInformation.get("dno");
		String email = employeeInformation.get("email");
		
		// update the employee table
		String updateQuery = " insert into employee (fname, minit, lname, ssn, bdate, address, sex, salary, superssn, dno, email)" + 
				" values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement stmt = conn.prepareStatement(updateQuery);
			stmt.setString(1, fname);
			stmt.setString(2, minit);
			stmt.setString(3,  lname);
			stmt.setString(4, ssn);
			stmt.setString(5,  bdate);
			stmt.setString(6,  address);
			stmt.setString(7,  sex);
			stmt.setString(8,  salary);
			stmt.setString(9,  superssn);
			stmt.setString(10, dno);
			stmt.setString(11,  email);
			stmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return;
	}
	
	// update works_on table information
	public void updateWorksOn(ArrayList<HashMap<String, String>> projects) {
		// employee ssn
		String essn = AddEmployeeScreenController.getEmployeeInformation().get("ssn");
		// insertion for each project assigned to employee
		for(int i = 0; i < projects.size(); i++) {
			// extract information from projects arraylist
			String pno = projects.get(i).get("pno");
			String hours = projects.get(i).get("hours");
			
			// update the works_on table
			String updateQuery = " insert into works_on (essn, pno, hours)" + " values (?, ?, ?)";
			
			try {
				PreparedStatement stmt = conn.prepareStatement(updateQuery);
				stmt.setString(1, essn);
				stmt.setString(2, pno);
				stmt.setString(3,  hours);
				stmt.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return;
	}
	
	// update dependent table information
	public void updateDependent(ArrayList<HashMap<String, String>> dependents) {
		// employee ssn
		String essn = AddEmployeeScreenController.getEmployeeInformation().get("ssn");
		// insertion for each dependent assigned to employee
		for(int i = 0; i < dependents.size(); i++) {
			// extract information from dependents arraylist
			String dependent_name = dependents.get(i).get("dependent_name");
			String sex = dependents.get(i).get("sex");
			String bdate = dependents.get(i).get("bdate");
			String relationship = dependents.get(i).get("relationship");
					
			// update the dependent table
			String updateQuery = " insert into dependent (essn, dependent_name, sex, bdate, relationship)" + " values (?, ?, ?, ?, ?)";
					
			try {
				PreparedStatement stmt = conn.prepareStatement(updateQuery);
				stmt.setString(1, essn);
				stmt.setString(2, dependent_name);
				stmt.setString(3,  sex);
				stmt.setString(4,  bdate);
				stmt.setString(5, relationship);
				stmt.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		closeConnection();
		return; 
	}
	
	// closes the connection to the data server
	void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

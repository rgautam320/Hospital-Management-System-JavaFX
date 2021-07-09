package patient;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.Profile;
import database.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class PatientDashboard implements Initializable{
	@FXML
	private Label username, accountType, nameLabel;
	
	@FXML
	private BorderPane borderPane;
	
	Authentication authentication = new Authentication();
	
	String userString = "";
	String fullname = "";
	
	public void userInfo(String name, String user, String type) {
		username.setText(user);
		accountType.setText(type);
		nameLabel.setText(name);
		
		userString = user;
		fullname = name;
	}
	
	public void showDoctors() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_patient/Doctor_List.fxml"));
		BorderPane pane = loader.load();
		borderPane.setCenter(pane);
	}
	
	public void takeAppointment() throws IOException, SQLException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_patient/Appointment.fxml"));
		BorderPane pane = loader.load();
		borderPane.setCenter(pane);
		
		PatientAppointment appointment = (PatientAppointment)loader.getController();
		appointment.getAppointments(userString);
		appointment.onGetUsername(userString, fullname);
	}
	
	public void showProfile() throws IOException, SQLException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Profile.fxml"));
		BorderPane pane = loader.load();
		borderPane.setCenter(pane);
		
		Profile profile = (Profile)loader.getController();
		profile.getUserInfo(userString);
	}
	
	public void logout(@SuppressWarnings("exports") ActionEvent event) {
		authentication.logoutFunction(event);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			showDoctors();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}

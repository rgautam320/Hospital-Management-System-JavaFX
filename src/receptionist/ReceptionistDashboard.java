package receptionist;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class ReceptionistDashboard implements Initializable{
	@FXML
	private Label username, accountType, nameLabel;
	
	@FXML
	private BorderPane borderPane;
		
	@FXML
	private Button patientButton, workerButton;
	
	Authentication authentication = new Authentication();

	
	String userString = "";
	String fullname = "";
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			appointmentHandle();
		} catch (IOException | SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void userInfo(String name, String user, String type) {
		username.setText(user);
		accountType.setText(type);
		nameLabel.setText(name);
		
		userString = user;
		fullname = name;
	}
	
	public void appointmentHandle() throws IOException, SQLException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_receptionist/Appointment.fxml"));
		BorderPane pane = loader.load();
		borderPane.setCenter(pane);
		
		ReceptionistAppointment appointment = (ReceptionistAppointment)loader.getController();
		appointment.getAppointments();
		appointment.onGetUsername(userString, fullname);
	}
	
	public void patientHandle() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_receptionist/Patient_List.fxml"));
		BorderPane pane = loader.load();
		borderPane.setCenter(pane);
	}
	
	public void workerHandle() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_receptionist/Worker_List.fxml"));
		BorderPane pane = loader.load();
		borderPane.setCenter(pane);
	}
	
	public void showProfile() throws SQLException, IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Profile.fxml"));
		BorderPane pane = loader.load();
		borderPane.setCenter(pane);
		
		Profile profile = (Profile)loader.getController();
		profile.getUserInfo(userString);
	}
	
	public void logout(@SuppressWarnings("exports") ActionEvent event) {
		authentication.logoutFunction(event);
	}

}

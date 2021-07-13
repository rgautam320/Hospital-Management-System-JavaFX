package patient;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import admin.AdminDashboard;
import database.UserModel;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DoctorController implements Initializable{
	@FXML
	private TableView<UserModel> tv;
	@FXML
	private TableColumn<UserModel, Integer> tv_id;

	@FXML 
	private TableColumn<UserModel, String> tvFullname, tvUsername, tvPhone, tvEmail, tvQualification, tvExpertise, tvEnroll, tvDOB, tvAddress, tvGender;
	
	AdminDashboard adminDashboard = new AdminDashboard();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		try {
			ObservableList<UserModel> doctorList = adminDashboard.getUserList("Doctor");
			
			tvUsername.setCellValueFactory(new PropertyValueFactory<UserModel, String>("username"));
			tvFullname.setCellValueFactory(new PropertyValueFactory<UserModel, String>("fullname"));
			tvDOB.setCellValueFactory(new PropertyValueFactory<UserModel, String>("dob"));
			tvPhone.setCellValueFactory(new PropertyValueFactory<UserModel, String>("phone"));
			tvEmail.setCellValueFactory(new PropertyValueFactory<UserModel, String>("email"));
			tvAddress.setCellValueFactory(new PropertyValueFactory<UserModel, String>("address"));
			tvQualification.setCellValueFactory(new PropertyValueFactory<UserModel, String>("qualification"));
			tvExpertise.setCellValueFactory(new PropertyValueFactory<UserModel, String>("expertise"));
			tvEnroll.setCellValueFactory(new PropertyValueFactory<UserModel, String>("enroll"));
			tvGender.setCellValueFactory(new PropertyValueFactory<UserModel, String>("gender"));
			
			tv.setItems(doctorList);
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
}

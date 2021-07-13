package receptionist;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import admin.AdminDashboard;
import database.DB;
import database.UserModel;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class PatientController implements Initializable{
	@FXML
	private TableView<UserModel> tv;
	@FXML
	private TableColumn<UserModel, Integer> tvID;
	@FXML
	private TableColumn<UserModel, String> tvFullname, tvUsername, tvPhone, tvPassword, tvEmail, tvEnroll, tvDOB, tvAddress, tvGender;
	
	@FXML
	private TextField tfID, tfUsername, tfFullname, tfEmail, tfPhone, tfAddress;
	
	@FXML
	private PasswordField tfPassword;
	
	@FXML
	private DatePicker tfEnroll, tfDOB;
	
	@FXML
	private ComboBox<String> tfGender;
	
	@FXML
	private Label warning;
	
	AdminDashboard adminDashboard = new AdminDashboard();
	
	Connection connection = DB.DBConnection();
	
	private String[] genders = {"Male", "Female", "Others"};
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		tfGender.getItems().addAll(genders);
		try {
			ObservableList<UserModel> patientList = adminDashboard.getUserList("Patient");
			
			tvID.setCellValueFactory(new PropertyValueFactory<UserModel, Integer>("id"));
			tvUsername.setCellValueFactory(new PropertyValueFactory<UserModel, String>("username"));
			tvFullname.setCellValueFactory(new PropertyValueFactory<UserModel, String>("fullname"));
			tvDOB.setCellValueFactory(new PropertyValueFactory<UserModel, String>("dob"));
			tvPhone.setCellValueFactory(new PropertyValueFactory<UserModel, String>("phone"));
			tvEmail.setCellValueFactory(new PropertyValueFactory<UserModel, String>("email"));
			tvAddress.setCellValueFactory(new PropertyValueFactory<UserModel, String>("address"));
			tvEnroll.setCellValueFactory(new PropertyValueFactory<UserModel, String>("enroll"));
			tvGender.setCellValueFactory(new PropertyValueFactory<UserModel, String>("gender"));
			tvPassword.setCellValueFactory(new PropertyValueFactory<UserModel, String>("password"));
			
			tv.setItems(patientList);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void onInsert() throws SQLException {
		PreparedStatement preparedStatement = null;
		try {
			if(tfID.getText() != "" && tfUsername.getText() != "" && tfPassword.getText() != "")
			{
				String query = "INSERT INTO user(id, username, password, fullname, email, type, phone, address, dob, enroll, gender) values ('" + tfID.getText() + "', '" 
						+ tfUsername.getText() + "', '" 
						+ tfPassword.getText() + "', '"
						+ tfFullname.getText() + "', '" 
						+ tfEmail.getText() + "', '" 
						+ "Patient" + "', '" 
						+ tfPhone.getText() + "', '" 
						+ tfAddress.getText() + "', '" 
						+ tfDOB.getValue() + "', '" 
						+ tfEnroll.getValue() + "', '" 
						+ tfGender.getValue() + "')";

				
				preparedStatement = connection.prepareStatement(query);
				
				preparedStatement.executeUpdate();
				
				warning.setText("");

			} else {
				warning.setText("Please, fill the form properly.");
			}
			
		} catch (Exception e) {
			warning.setText("Constraint Failed");
		} finally {
			if(preparedStatement != null)
			{
				preparedStatement.close();
			}
			
			setEmpty();
			
			ObservableList<UserModel> patientList = adminDashboard.getUserList("Patient");
			
			showInTable();
			
			tv.setItems(patientList);
		}
	}
	
	
	public void onUpdate() throws SQLException {
		try {
			if(tfID.getText() != "" && tfUsername.getText() != "" && tfPassword.getText() != "" && tfFullname.getText() != "")
			{
				String queryString = "UPDATE user SET username = ?, password = ?, fullname = ?, gender = ?, email = ?, phone = ?, address = ?, dob = ?, enroll = ? WHERE id = ?";
				
				PreparedStatement preparedStatement = connection.prepareStatement(queryString);
				
				preparedStatement.setString(1, tfUsername.getText());
				preparedStatement.setString(2, tfPassword.getText());
				preparedStatement.setString(3, tfFullname.getText());
				preparedStatement.setString(4, tfGender.getValue());
				preparedStatement.setString(5, tfEmail.getText());
				preparedStatement.setString(6, tfPhone.getText());
				preparedStatement.setString(7, tfAddress.getText());
				preparedStatement.setString(8, tfDOB.getValue().toString());
				preparedStatement.setString(9, tfEnroll.getValue().toString());
				preparedStatement.setInt(10, Integer.parseInt(tfID.getText()));
				
				preparedStatement.executeUpdate();
				
				warning.setText("");

			} else {
				warning.setText("Please, fill the form properly.");
			}
		} catch (Exception e) {
			
		} finally {
			setEmpty();
			
			ObservableList<UserModel> patientList = adminDashboard.getUserList("Patient");
			
			showInTable();
			
			tv.setItems(patientList);
		}
	
	}
	
	public void onDelete() throws SQLException {
		try {
			String queryString = "DELETE FROM user WHERE id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(queryString);
			preparedStatement.setString(1, tfID.getText());
			
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			
		} finally {
			
			setEmpty();
			
			ObservableList<UserModel> patientList = adminDashboard.getUserList("Patient");
			
			showInTable();
			
			tv.setItems(patientList);
		}
	}
	
	public void setEmpty() {
		tfID.setText("");
		tfUsername.setText("");
		tfPassword.setText("");
		tfFullname.setText("");
		tfEmail.setText("");
		tfAddress.setText("");
		tfPhone.setText("");
		tfDOB.setValue(LocalDate.now());
		tfEnroll.setValue(LocalDate.now());
		
		warning.setText("");
		
		tfID.setEditable(true);
	}
	
	public void handleMouseAction() {
		try {
			UserModel users = tv.getSelectionModel().getSelectedItem();
			tfID.setText(Integer.toString(users.getId()));
			tfID.setText(Integer.toString(users.getId()));
			tfUsername.setText(users.getUsername());
			tfPassword.setText(users.getPassword());
			tfFullname.setText(users.getFullname());
			tfEmail.setText(users.getEmail());
			tfPhone.setText(users.getPhone());
			tfAddress.setText(users.getAddress());
			tfDOB.setValue(LocalDate.parse(users.getDob()));
			tfGender.setValue(users.getGender());
			tfEnroll.setValue(LocalDate.parse(users.getEnroll()));
			
			tfID.setEditable(false);
		
		} catch (Exception e) {
			
		}
	}
	
	public void showInTable() {
		tvID.setCellValueFactory(new PropertyValueFactory<UserModel, Integer>("id"));
		tvUsername.setCellValueFactory(new PropertyValueFactory<UserModel, String>("username"));
		tvPassword.setCellValueFactory(new PropertyValueFactory<UserModel, String>("password"));
		tvFullname.setCellValueFactory(new PropertyValueFactory<UserModel, String>("fullname"));
		tvDOB.setCellValueFactory(new PropertyValueFactory<UserModel, String>("dob"));
		tvPhone.setCellValueFactory(new PropertyValueFactory<UserModel, String>("phone"));
		tvEmail.setCellValueFactory(new PropertyValueFactory<UserModel, String>("email"));
		tvAddress.setCellValueFactory(new PropertyValueFactory<UserModel, String>("address"));
		tvEnroll.setCellValueFactory(new PropertyValueFactory<UserModel, String>("enroll"));
		tvGender.setCellValueFactory(new PropertyValueFactory<UserModel, String>("gender"));
	}
}

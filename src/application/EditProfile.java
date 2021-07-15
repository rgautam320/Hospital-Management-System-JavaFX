package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import database.DB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class EditProfile implements Initializable{
	@FXML
	private TextField tfUsername, tfFullname, tfEmail, tfPhone, tfAddress;
	
	@FXML
	private PasswordField tfPassword;
	
	@FXML
	private DatePicker tfDOB;
	
	@FXML
	private Label warning;
	
	@FXML
	private ComboBox<String> tfGender;
	
	private String[] genders = {"Male", "Female", "Others"};
	
	private int id;
	
	Connection connection = DB.DBConnection();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		tfGender.getItems().addAll(genders);
	}
	
	public void getUserInfo(Integer identity, String user, String fullname, String password, String email, String phone, String dob, String address, String gender) {
		tfUsername.setText(user);
		tfFullname.setText(fullname);
		tfPassword.setText(password);
		tfEmail.setText(email);
		tfPhone.setText(phone);
		tfAddress.setText(address);
		tfGender.setValue(gender);
		
		if(dob != null)
		{
			tfDOB.setValue(LocalDate.parse(dob));
		} else {
			tfDOB.setValue(LocalDate.of(2000, 01, 01));
		}
		
		id = identity;
	}
	
	public void editProfileAction(ActionEvent event) throws SQLException {
		try {
			if(tfUsername.getText() != "" && tfPassword.getText() != "" && tfFullname.getText() != "")
			{
				String queryString = "UPDATE user SET username = ?, password = ?, fullname = ?, gender = ?, email = ?, phone = ?, address = ?, dob = ? WHERE id = ?";
				
				PreparedStatement preparedStatement = connection.prepareStatement(queryString);
				
				preparedStatement.setString(1, tfUsername.getText());
				preparedStatement.setString(2, tfPassword.getText());
				preparedStatement.setString(3, tfFullname.getText());
				preparedStatement.setString(4, tfGender.getValue());
				preparedStatement.setString(5, tfEmail.getText());
				preparedStatement.setString(6, tfPhone.getText());
				preparedStatement.setString(7, tfAddress.getText());
				preparedStatement.setString(8, tfDOB.getValue().toString());
				preparedStatement.setInt(9, id);
				
				preparedStatement.executeUpdate();
				
				warning.setText("");

			} else {
				warning.setText("Please, fill the form properly.");
			}
		} catch (Exception e) {
			
		} finally {
			((Node)event.getSource()).getScene().getWindow().hide();
		}
	}
}

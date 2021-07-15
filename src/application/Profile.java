package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import database.Authentication;
import database.DB;
import database.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Profile implements Initializable{
	@FXML
	private Label lUsername, lFullname, lEmail, lPhone, lDOB, lED, lType, lAddress, lGender;
	
	String username = "";
	String fullname = "";
	String email = "";
	String phone = "";
	String dob = "";
	String address = "";
	String gender = "";
	String password = "";
	int id;
	
	Connection conn;
	
	Authentication authentication = new Authentication();
	
	public Profile() {
		conn = DB.DBConnection();
		if(conn == null) 
		{
			System.exit(1);
		}
		
		if(!authentication.isDBConnected())
		{
			System.out.println("Database not Connected");
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}

	public void getUserInfo(String user) throws SQLException {
		
		Connection connection = DB.DBConnection();
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		String queryString = "SELECT * FROM user WHERE username = '" + user + "'";
		
		try {
			preparedStatement = connection.prepareStatement(queryString);
			resultSet = preparedStatement.executeQuery();

			UserModel info = new UserModel(
					resultSet.getInt("id"), 
					resultSet.getString("username"), 
					resultSet.getString("password"), 
					resultSet.getString("fullname"), 
					resultSet.getString("email"), 
					resultSet.getString("phone"),
					resultSet.getString("address"),
					resultSet.getString("dob"),
					resultSet.getString("type"),
					resultSet.getString("enroll"),
					resultSet.getString("qualification"),
					resultSet.getString("expertise"),
					resultSet.getString("gender")
					);
			
			username = info.getUsername();
			fullname = info.getFullname();
			email = info.getEmail();
			phone = info.getPhone();
			dob = info.getDob();
			address = info.getAddress();
			gender = info.getGender();
			password = info.getPassword();
			id = info.getId();
			
			lUsername.setText(info.getUsername());
			lFullname.setText(info.getFullname());
			lEmail.setText(info.getEmail());
			lPhone.setText(info.getPhone());
			lAddress.setText(info.getAddress());
			lDOB.setText(info.getDob());
			lED.setText(info.getEnroll());
			lType.setText(info.getType());
			lGender.setText(info.getGender());
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			preparedStatement.close();
			resultSet.close();
		}
	}
	
	public void editProfile(ActionEvent event) throws SQLException, IOException {
		((Node)event.getSource()).getScene().getWindow();
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EditProfile.fxml"));
		Pane root = loader.load();
		Scene scene = new Scene(root);
		
		EditProfile editProfile = (EditProfile)loader.getController();
		editProfile.getUserInfo(id, username, fullname, password, email, phone, dob, address, gender);
		
		stage.setScene(scene);
		stage.setTitle("Edit Profile");
		stage.getIcons().add(new Image("logo.png"));
		stage.setMaximized(false);
		stage.show();
	}
	
}

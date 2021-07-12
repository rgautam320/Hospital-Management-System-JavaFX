package doctor;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import database.AppointmentModel;
import database.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import patient.PatientDashboard;

public class DoctorAppointment implements Initializable{
	@FXML
	private TableView<AppointmentModel> tv;
	@FXML
	private TableColumn<AppointmentModel, Integer> tvID;
	@FXML
	private TableColumn<AppointmentModel, String> tvFullname, tvPhone, tvDOB, tvAddress, tvGender, tvEmail, tvAD, tvIssue, tvStatus, tvPrescription;
	@FXML
	private TextField tfAddress, tfEmail, tfIssue, tfPhone, tfPrescription;
	@FXML
	private DatePicker tfDOB, tfAD;
	@FXML
	private ComboBox<String> tfGender, tfStatus;
	@FXML
	private Label warning;
	
	private String[] genders = {"Male", "Female", "Others"};
	private String[] status = {"Pending", "Approved", "Declined"};
	
	PatientDashboard patientDashboard = new PatientDashboard();
	
	Connection connection = DB.DBConnection();
	
	String usernameString = "";
	String fullnameString = "";
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		tfGender.getItems().addAll(genders);
		tfStatus.getItems().addAll(status);
	}
	
	public void onGetUsername(String user, String fullname) {
		usernameString = user;
		fullnameString = fullname;
	}
	
	@SuppressWarnings("exports")
	public ObservableList<AppointmentModel> getAppointments() throws SQLException{
		ObservableList<AppointmentModel> appointments = FXCollections.observableArrayList();
		
		Connection connection = DB.DBConnection();
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		String queryString = "SELECT * FROM appointment";
		
		try {
			preparedStatement = connection.prepareStatement(queryString);
			resultSet = preparedStatement.executeQuery();

			while(resultSet.next())
			{
				AppointmentModel appoints = new AppointmentModel(
						resultSet.getInt("id"), 
						resultSet.getString("username"), 
						resultSet.getString("fullname"), 
						resultSet.getString("phone"), 
						resultSet.getString("email"), 
						resultSet.getString("gender"), 
						resultSet.getString("DOB"), 
						resultSet.getString("address"), 
						resultSet.getString("AD"), 
						resultSet.getString("issue"), 
						resultSet.getString("status"), 
						resultSet.getString("prescription"));

				if(appoints.getStatus().equals("Approved"))
				{
					appointments.add(appoints);
				}
			}
			
			showInTable();
			
			tv.setItems(appointments);
		} catch (Exception e) {
			warning.setText("Couldn't Fetch Data");
			System.out.println(e.getMessage());
		} finally {
			preparedStatement.close();
			resultSet.close();
		}
		return appointments;
	}
	
	public void onUpdate() throws SQLException {
		if(
				tfGender.getValue() != "Gender" && 
				tfPhone.getText() != "" && 
				tfEmail.getText() != "" && 
				tfDOB.getValue() != null && 
				tfAddress.getText() != "" && 
				tfAD.getValue() != null &&
				tfIssue.getText() != "" &&
				tfStatus.getValue() != "Status"
		  )
		{
			String queryString = "UPDATE appointment SET "
					+ "gender = ?, "
					+ "phone = ?, "
					+ "email = ?, "
					+ "dob = ?, "
					+ "address = ?, "
					+ "ad = ?, "
					+ "issue = ?, "
					+ "status = ?, "
					+ "prescription = ? "
					+ "WHERE id = ?";
			
			PreparedStatement preparedStatement = connection.prepareStatement(queryString);
			
			AppointmentModel model = tv.getSelectionModel().getSelectedItem();
			
			preparedStatement.setString(1, tfGender.getValue());
			preparedStatement.setString(2, tfPhone.getText());
			preparedStatement.setString(3, tfEmail.getText());
			preparedStatement.setString(4, tfDOB.getValue().toString());
			preparedStatement.setString(5, tfAddress.getText());
			preparedStatement.setString(6, tfAD.getValue().toString());
			preparedStatement.setString(7, tfIssue.getText());
			preparedStatement.setString(8, tfStatus.getValue());
			preparedStatement.setString(9, tfPrescription.getText());
			preparedStatement.setInt(10, tvID.getCellData(model));
			
			preparedStatement.executeUpdate();
			
			warning.setText("");
			
			setEmpty();
			
			ObservableList<AppointmentModel> appointmentsList = getAppointments();
			
			showInTable();
			
			tv.setItems(appointmentsList);

		} else {
			warning.setText("Please, fill the form properly.");
		}

	}
	
	public void showInTable() {
		tvID.setCellValueFactory(new PropertyValueFactory<AppointmentModel, Integer>("id"));
		tvFullname.setCellValueFactory(new PropertyValueFactory<AppointmentModel, String>("fullname"));
		tvPhone.setCellValueFactory(new PropertyValueFactory<AppointmentModel, String>("phone"));
		tvEmail.setCellValueFactory(new PropertyValueFactory<AppointmentModel, String>("email"));
		tvGender.setCellValueFactory(new PropertyValueFactory<AppointmentModel, String>("gender"));
		tvEmail.setCellValueFactory(new PropertyValueFactory<AppointmentModel, String>("email"));
		tvDOB.setCellValueFactory(new PropertyValueFactory<AppointmentModel, String>("dob"));
		tvAddress.setCellValueFactory(new PropertyValueFactory<AppointmentModel, String>("address"));
		tvAD.setCellValueFactory(new PropertyValueFactory<AppointmentModel, String>("ad"));
		tvIssue.setCellValueFactory(new PropertyValueFactory<AppointmentModel, String>("issue"));
		tvStatus.setCellValueFactory(new PropertyValueFactory<AppointmentModel, String>("status"));
		tvPrescription.setCellValueFactory(new PropertyValueFactory<AppointmentModel, String>("prescription"));
	
	}
	
	public void handleMouseAction() {
		AppointmentModel model = tv.getSelectionModel().getSelectedItem();
			
		tfGender.setValue(model.getGender());
		tfEmail.setText(model.getEmail());
		tfPhone.setText(model.getPhone());
		tfDOB.setValue(LocalDate.parse(model.getDob()));
		tfAddress.setText(model.getAddress());
		tfAD.setValue(LocalDate.parse(model.getAd()));
		tfIssue.setText(model.getIssue());
		tfStatus.setValue(model.getStatus());
		tfPrescription.setText(model.getPrescription());
	}
	
	public void setEmpty() {
		tfAddress.setText("");
		tfEmail.setText("");
		tfIssue.setText("");
		tfPhone.setText("");
		tfDOB.setValue(null);
		tfAD.setValue(null);
		tfPrescription.setText("");
		warning.setText("");
	}
}

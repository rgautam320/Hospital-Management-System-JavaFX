package database;

public class AppointmentModel {
	private int id;
	private String username;
	private String fullname;
	private String phone;
	private String email;
	private String gender;
	private String dob;
	private String address;
	private String ad;
	private String issue;
	private String status;
	private String prescription;
	public AppointmentModel(int id, String username, String fullname, String phone, String email, String gender,
			String dob, String address, String ad, String issue, String status, String prescription) {
		super();
		this.id = id;
		this.username = username;
		this.fullname = fullname;
		this.phone = phone;
		this.email = email;
		this.gender = gender;
		this.dob = dob;
		this.address = address;
		this.ad = ad;
		this.issue = issue;
		this.status = status;
		this.prescription = prescription;
	}
	public int getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public String getFullname() {
		return fullname;
	}
	public String getPhone() {
		return phone;
	}
	public String getEmail() {
		return email;
	}
	public String getGender() {
		return gender;
	}
	public String getDob() {
		return dob;
	}
	public String getAddress() {
		return address;
	}
	public String getAd() {
		return ad;
	}
	public String getIssue() {
		return issue;
	}
	public String getStatus() {
		return status;
	}
	public String getPrescription() {
		return prescription;
	}
	
}

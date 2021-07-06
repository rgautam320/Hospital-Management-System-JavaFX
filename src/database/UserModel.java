package database;

public class UserModel {
	private int id;
	private String username;
	private String password;
	private String fullname;
	private String email;
	private String phone;
	private String address;
	private String dob;
	private String type;
	private String enroll;
	private String qualification;
	private String expertise;
	private String gender;
	public UserModel(int id, String username, String password, String fullname, String email, String phone,
			String address, String dob, String type, String enroll, String qualification, String expertise, String gender) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.dob = dob;
		this.type = type;
		this.enroll = enroll;
		this.qualification = qualification;
		this.expertise = expertise;
		this.gender = gender;
	}
	public int getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getFullname() {
		return fullname;
	}
	public String getEmail() {
		return email;
	}
	public String getPhone() {
		return phone;
	}
	public String getAddress() {
		return address;
	}
	public String getDob() {
		return dob;
	}
	public String getType() {
		return type;
	}
	public String getEnroll() {
		return enroll;
	}
	public String getQualification() {
		return qualification;
	}
	public String getExpertise() {
		return expertise;
	}
	public String getGender() {
		return gender;
	}
}

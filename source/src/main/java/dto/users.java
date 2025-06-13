package dto;

public class users {
	private int id;
	private String user_id;
	private String user_name;
	private String password;
	private String mail_address;
	public users(int id, String user_id, String user_name, String password, String mail_address) {
		this.id = id;
		this.user_id = user_id;
		this.user_name = user_name;
		this.password = password;
		this.mail_address = mail_address;
	}
	
	public users() {
		this(0, "","","","");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMail_address() {
		return mail_address;
	}

	public void setMail_address(String mail_address) {
		this.mail_address = mail_address;
	}
}

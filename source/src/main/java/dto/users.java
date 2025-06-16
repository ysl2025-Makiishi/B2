package dto;

import java.util.Date;


//ユーザーのデータを格納するテーブル
public class users {
	private int id;
	private String user_id;
	private String user_name;
	private String password;
	private Date created_at;
	private Date updated_at;
	
	public users(int id, String user_id, String user_name, String password, Date created_at, Date updated_at) {
		this.id = id;
		this.user_id = user_id;
		this.user_name = user_name;
		this.password = password;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}
	
	public users() {
		this(0, "","","", null, null);
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
	
	public Date getCreated_at() {
		return created_at;
	}
	
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
	
	public Date getUpdated_at() {
		return updated_at;
	}
	
	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
}

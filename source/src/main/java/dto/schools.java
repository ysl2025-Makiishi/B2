package dto;

import java.util.Date;

//学校名を格納するテーブル
public class schools {
	private int id;
	private String school_name;
	private Date created_at;
	private Date updated_at;
	
	public schools(int id, String school_name, Date created_at, Date updated_at) {
		this.id = id;
		this.school_name = school_name;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}
	
	public schools() {
		this(0, "", null, null);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSchool_name() {
		return school_name;
	}

	public void setSchool_name(String school_name) {
		this.school_name = school_name;
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

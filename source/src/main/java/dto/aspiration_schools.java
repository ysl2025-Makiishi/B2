package dto;

import java.util.Date;

//志望校の名前を格納するテーブル
public class aspiration_schools {
	private int id;
	private String aspiration_school_name;	//志望校名
	private Date created_at;
	private Date updated_at;
	
	public aspiration_schools(int id, String aspiration_school_name, Date created_at, Date updated_at) {
		this.id = id;
		this.aspiration_school_name = aspiration_school_name;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}
	
	public aspiration_schools() {
		this(0, "", null, null);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAspiration_school_name() {
		return aspiration_school_name;
	}

	public void setAspiration_school_name(String aspiration_school_name) {
		this.aspiration_school_name = aspiration_school_name;
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

package dto;

import java.util.Date;

//科目名を格納するテーブル
public class subjects {
	private int id;
	private String subject_name;
	private Date created_at;
	private Date updated_at;
	
	public subjects(int id, String subject_name, Date created_at, Date updated_at) {
		this.id = id;
		this.subject_name = subject_name;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}
	
	public subjects() {
		this(0,"",null,null);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubject_name() {
		return subject_name;
	}

	public void setSubject_name(String subject_name) {
		this.subject_name = subject_name;
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

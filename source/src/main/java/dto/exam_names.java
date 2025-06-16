package dto;

import java.util.Date;

//試験名を格納するテーブル
public class exam_names {
	private int id;
	private String exam_names;
	private Date created_at;
	private Date updated_at;
	
	public exam_names(int id, String exam_names, Date created_at, Date updated_at) {
		this.id = id;
		this.exam_names = exam_names;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}
	
	public exam_names() {
		this(0,"",null,null);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getExam_names() {
		return exam_names;
	}

	public void setExam_names(String exam_names) {
		this.exam_names = exam_names;
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

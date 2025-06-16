package dto;

import java.util.Date;

//成績（学校のほう）を格納するテーブル
public class gpas {
	private int id;
	private int student_id;
	private int subject_id;
	private int gpa;
	private Date created_at;
	private Date updated_at;
	
	public gpas(int id, int student_id, int subject_id, int gpa, Date created_at, Date updated_at) {
		this.id = id;
		this.student_id = student_id;
		this.subject_id = subject_id;
		this.gpa = gpa;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}
	
	public gpas() {
		this(0,0,0,0,null,null);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStudent_id() {
		return student_id;
	}

	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}

	public int getSubject_id() {
		return subject_id;
	}

	public void setSubject_id(int subject_id) {
		this.subject_id = subject_id;
	}

	public int getGpa() {
		return gpa;
	}

	public void setGpa(int gpa) {
		this.gpa = gpa;
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

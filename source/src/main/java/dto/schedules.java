package dto;

import java.util.Date;

//スケジュールを格納するテーブル
public class schedules {
	private int id;
	private int text_id;
	private int pages;
	private int student_id;
	private int subject_id;
	private Date created_at;
	private Date updated_at;
	
	public schedules(int id, int text_id, int pages, int student_id, int subject_id, Date created_at, Date updated_at) {
	    this.id = id;
	    this.text_id = text_id;
	    this.pages = pages;
	    this.student_id = student_id;
	    this.subject_id = subject_id;
	    this.created_at = created_at;
	    this.updated_at = updated_at;
	}

	
	public schedules() {
	    this(0, 0, 0, 0, 0, null, null);
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getText_id() {
		return text_id;
	}

	public void setText_id(int text_id) {
		this.text_id = text_id;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
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

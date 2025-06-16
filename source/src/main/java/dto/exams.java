package dto;

import java.util.Date;

//試験を格納するテーブル
public class exams {
	private int id;
	private int exam_name_id;
	private Date exam_date;
	private int exam_score_id;
	private Date created_at;
	private Date updated_at;
	
	public exams(int id, int exam_name_id, Date exam_date, int exam_score_id, Date created_at, Date updated_at) {
		this.id = id;
		this.exam_name_id = exam_name_id;
		this.exam_date = exam_date;
		this.exam_score_id = exam_score_id;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}
	
	public exams() {
		this(0,0,null,0,null,null);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getExam_name_id() {
		return exam_name_id;
	}

	public void setExam_name_id(int exam_name_id) {
		this.exam_name_id = exam_name_id;
	}

	public Date getExam_date() {
		return exam_date;
	}

	public void setExam_date(Date exam_date) {
		this.exam_date = exam_date;
	}

	public int getExam_score_id() {
		return exam_score_id;
	}

	public void setExam_score_id(int exam_score_id) {
		this.exam_score_id = exam_score_id;
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

package dto;

import java.util.Date;

//試験の点数を格納する点数
public class exam_scores {
	private int id;
	private int exam_id;
	private int student_id;
	private int subject_id;
	private int score;
	private double average_score;	//平均点
	private double deviation_value;	//偏差値
	private Date created_at;
	private Date updated_at;
	
	public exam_scores(int id, int exam_id, int student_id, int subject_id, int score, double average_score,
			double deviation_value, Date created_at, Date updated_at) {
		this.id = id;
		this.exam_id = exam_id;
		this.student_id = student_id;
		this.subject_id = subject_id;
		this.score = score;
		this.average_score = average_score;
		this.deviation_value = deviation_value;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}

	public exam_scores() {
		this(0,0,0,0,0,0,0,null,null);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getExam_id() {
		return exam_id;
	}

	public void setExam_id(int exam_id) {
		this.exam_id = exam_id;
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public double getAverage_score() {
		return average_score;
	}

	public void setAverage_score(double average_score) {
		this.average_score = average_score;
	}

	public double getDeviation_value() {
		return deviation_value;
	}

	public void setDeviation_value(double deviation_value) {
		this.deviation_value = deviation_value;
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

package dto;

import java.util.Date;

//授業報告書を格納するテーブル
public class reports {
	private int id;
	private int student_id;
	private int subject_id;
	private String did_last_time;	//前回やったこと
	private String do_next_time;	//次回やること
	private String homework;
	private String note;			//備考
	private Date created_at;
	private Date updated_at;		//実施日の欄はここからとってくる（初回は登録日？）
	
	public reports(int id, int student_id, int subject_id, String did_last_time, String do_next_time, String homework,
			String note, Date created_at, Date updated_at) {
		this.id = id;
		this.student_id = student_id;
		this.subject_id = subject_id;
		this.did_last_time = did_last_time;
		this.do_next_time = do_next_time;
		this.homework = homework;
		this.note = note;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}
	
	public reports() {
		this(0,0,0,"","","","",null,null);
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

	public String getDid_last_time() {
		return did_last_time;
	}

	public void setDid_last_time(String did_last_time) {
		this.did_last_time = did_last_time;
	}

	public String getDo_next_time() {
		return do_next_time;
	}

	public void setDo_next_time(String do_next_time) {
		this.do_next_time = do_next_time;
	}

	public String getHomework() {
		return homework;
	}

	public void setHomework(String homework) {
		this.homework = homework;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

package dto;

import java.util.Date;

//テキストのデータを格納
public class texts {
	private int id;
	private String text_name;
	private int subject_id;
	private int personality_id;
	private int pages;
	private String notes;		//備考
	private Date created_at;
	private Date updated_at;
	
	public texts(int id, String text_name, int subject_id, int personality_id, int pages, String notes, Date created_at,
			Date updated_at) {
		this.id = id;
		this.text_name = text_name;
		this.subject_id = subject_id;
		this.personality_id = personality_id;
		this.pages = pages;
		this.notes = notes;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}
	
	public texts() {
		this(0,"",0,0,0,"",null,null);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText_name() {
		return text_name;
	}

	public void setText_name(String text_name) {
		this.text_name = text_name;
	}

	public int getSubject_id() {
		return subject_id;
	}

	public void setSubject_id(int subject_id) {
		this.subject_id = subject_id;
	}

	public int getPersonality_id() {
		return personality_id;
	}

	public void setPersonality_id(int personality_id) {
		this.personality_id = personality_id;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
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

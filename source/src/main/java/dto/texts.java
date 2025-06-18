package dto;

import java.util.Date;

public class texts {
	private int id;
	private String textName;
	private int subjectId;
	private int personalityId;
	private int pages;
	private String note;
	private Date createdAt;
	private Date updatedAt;

	public texts() {}

	public texts(int id, String textName, int subjectId, int personalityId, int pages, String note, Date createdAt, Date updatedAt) {
		this.id = id;
		this.textName = textName;
		this.subjectId = subjectId;
		this.personalityId = personalityId;
		this.pages = pages;
		this.note = note;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	// Getter / Setter
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

	public String getTextName() { return textName; }
	public void setTextName(String textName) { this.textName = textName; }

	public int getSubjectId() { return subjectId; }
	public void setSubjectId(int subjectId) { this.subjectId = subjectId; }

	public int getPersonalityId() { return personalityId; }
	public void setPersonalityId(int personalityId) { this.personalityId = personalityId; }

	public int getPages() { return pages; }
	public void setPages(int pages) { this.pages = pages; }

	public String getNote() { return note; }
	public void setNote(String note) { this.note = note; }

	public Date getCreatedAt() { return createdAt; }
	public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

	public Date getUpdatedAt() { return updatedAt; }
	public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}

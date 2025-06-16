package dto;

import java.util.Date;

//性格名を格納するテーブル
public class personalities {
	private int id;
	private String personality_name;
	private Date created_at;
	private Date updated_at;
	
	public personalities(int id, String personality_name, Date created_at, Date updated_at) {
		this.id = id;
		this.personality_name = personality_name;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}
	
	public personalities() {
		this(0, "", null, null);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPersonality_name() {
		return personality_name;
	}

	public void setPersonality_name(String personality_name) {
		this.personality_name = personality_name;
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

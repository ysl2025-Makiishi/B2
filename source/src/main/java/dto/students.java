package dto;

import java.util.Date;

//生徒の情報を格納するテーブル
public class students {
	private int id;
	private String name;
	private String furigana;
	private int school_id;
	private Date birthday;
	private String gender;
	private int aspiration_school1_id; // 志望校（3つ登録できるようにするので3つ用意）
	private int aspiration_school2_id;
	private int aspiration_school3_id;
	private int personality_id;
	private Date created_at;
	private Date updated_at;

	public students(int id, String name, String furigana, int school_id, String school_name, Date birthday,
			String gender, int aspiration_school1_id, int aspiration_school2_id, int aspiration_school3_id,
			int personality_id, Date created_at, Date updated_at) {
		this.id = id;
		this.name = name;
		this.furigana = furigana;
		this.school_id = school_id;
		this.birthday = birthday;
		this.gender = gender;
		this.aspiration_school1_id = aspiration_school1_id;
		this.aspiration_school2_id = aspiration_school2_id;
		this.aspiration_school3_id = aspiration_school3_id;
		this.personality_id = personality_id;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}

	public students() {
		this(0, "", "", 0, null, null, "", 0, 0, 0, 0, null, null);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFurigana() {
		return furigana;
	}

	public void setFurigana(String furigana) {
		this.furigana = furigana;
	}

	public int getSchool_id() {
		return school_id;
	}
<<<<<<< HEAD

	public String getSchool_name() {
		return school_name;
	}

	public void setSchool_name(String school_name) {
		this.school_name = school_name;
	}

=======
	
>>>>>>> 2c41478e3611510be5856f1b4feb698c94e0e5f4
	public void setSchool_id(int school_id) {
		this.school_id = school_id;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAspiration_school1_id() {
		return aspiration_school1_id;
	}

	public void setAspiration_school1_id(int aspiration_school1_id) {
		this.aspiration_school1_id = aspiration_school1_id;
	}

	public int getAspiration_school2_id() {
		return aspiration_school2_id;
	}

	public void setAspiration_school2_id(int aspiration_school2_id) {
		this.aspiration_school2_id = aspiration_school2_id;
	}

	public int getAspiration_school3_id() {
		return aspiration_school3_id;
	}

	public void setAspiration_school3_id(int aspiration_school3_id) {
		this.aspiration_school3_id = aspiration_school3_id;
	}

	public int getPersonality_id() {
		return personality_id;
	}

	public void setPersonality_id(int personality_id) {
		this.personality_id = personality_id;
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

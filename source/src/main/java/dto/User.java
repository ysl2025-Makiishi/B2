package dto;

import java.io.Serializable;
import java.sql.Timestamp; // ← DATETIME/TIMESTAMP カラムに対応
// import java.time.LocalDateTime;  // Java 8 以降はこちらでも OK

/** users テーブル用 DTO */
public class User implements Serializable {

	private int id;
	private String userId;
	private String password;
	private Timestamp createdAt;
	private Timestamp updatedAt;

	// ---- コンストラクタ ----
	public User(int id, String userId, String password, Timestamp createdAt, Timestamp updatedAt) {
		this.id = id;
		this.userId = userId;
		this.password = password;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	// デフォルトコンストラクタ
	public User() {
		this(0, "", "", null, null);
	}

	// ---- getter / setter ----
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String uid) {
		this.userId = uid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String pw) {
		this.password = pw;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp ts) {
		this.createdAt = ts;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp ts) {
		this.updatedAt = ts;
	}

}

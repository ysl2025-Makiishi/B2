package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dto.students;


public class RegistDAO {
		// 引数cardで指定されたレコードを登録し、成功したらtrueを返す
		public boolean insert(students card) {
			Connection conn = null;
			boolean result = false;

			try {
				// JDBCドライバを読み込む
				Class.forName("com.mysql.cj.jdbc.Driver");

				// データベースに接続する
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/webapp2?"
						+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
						"root", "password");

				// SQL文を準備する
				String sql = "INSERT INTO Bc VALUES (0, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement pStmt = conn.prepareStatement(sql);

				// SQL文を完成させる
				if (card.getName() != null) {
					pStmt.setString(1, card.getName());
				} else {
					pStmt.setString(1, "");
				}
				if (card.getFurigana() != null) {
					pStmt.setString(2, card.getFurigana());
				} else {
					pStmt.setString(2, "");
				}
				if (card.getSchool_id() != 0) {
					pStmt.setInt(3, card.getSchool_id());
				} else {
					pStmt.setInt(3, 0);
				}
				if (card.getBirthday() != null) {
					pStmt.setDate(4, new java.sql.Date(card.getBirthday().getTime()));
				} else {
					pStmt.setString(4, "");
				}
				if (card.getGender() != null) {
					pStmt.setString(5, card.getGender());
				} else {
					pStmt.setString(5, "");
				}
				if (card.getAspiration_school1_id() != 0) {
					pStmt.setInt(6, card.getAspiration_school1_id());
				} else {
					pStmt.setInt(6, 0);
				}
				if (card.getAspiration_school2_id() != 0) {
					pStmt.setInt(7, card.getAspiration_school2_id());
				} else {
					pStmt.setInt(7, 0);
				}
				if (card.getAspiration_school3_id() != 0) {
					pStmt.setInt(8, card.getAspiration_school3_id());
				} else {
					pStmt.setInt(8, 0);
				}
				if (card.getPersonality_id() != 0) {
					pStmt.setInt(9, card.getPersonality_id());
				} else {
					pStmt.setInt(9, 0);
				}
				if (card.getCreated_at() != null) {
					pStmt.setDate(10, new java.sql.Date(card.getCreated_at().getTime()));
				} else {
					pStmt.setDate(10, null);
				}
				if (card.getUpdated_at() != null) {
					pStmt.setDate(11, new java.sql.Date(card.getCreated_at().getTime()));
				} else {
					pStmt.setDate(11, null);
				}

				// SQL文を実行する
				if (pStmt.executeUpdate() == 1) {
					result = true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				// データベースを切断
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}

			// 結果を返す
			return result;
		}
}

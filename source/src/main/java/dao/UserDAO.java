package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.IdPw; // ← フォーム入力を受け取るだけならこの DTO で流用可
// もし User DTO を使うなら import dto.User; に差し替え、下の型も変更

public class UserDAO {

	/** 引数 DTO の user_id / password が一致すれば true */
	public boolean isLoginOK(IdPw idpw) {

		Connection conn = null;
		boolean loginResult = false;

		try {
			// JDBC ドライバ読み込み
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続（スキーマ b2）
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/b2?" + "characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Tokyo",
					"root", "password");

			// SELECT 文を準備
			String sql = "SELECT COUNT(*) FROM users WHERE user_id = ? AND password = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, idpw.getId()); // または dto.getUserId()
			pStmt.setString(2, idpw.getPw()); // または dto.getPassword()

			// 実行して結果取得
			ResultSet rs = pStmt.executeQuery();
			rs.next();

			// 一致行が 1 ならログイン成功
			if (rs.getInt("COUNT(*)") == 1) {
				loginResult = true;
			}

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			loginResult = false;
		} finally {
			// 接続クローズ
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return loginResult;
	}
}

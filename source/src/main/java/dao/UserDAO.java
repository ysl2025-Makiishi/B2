package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.users;

public class UserDAO {

	/** 引数 DTO の user_id と password が一致する行があれば true */
	public boolean isLoginOK(users user) {
		Connection conn = null;
		boolean loginResult = false;

		try {
			// JDBC ドライバ
			Class.forName("com.mysql.cj.jdbc.Driver");

			// DB 接続
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/webapp2?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SELECT
			String sql = "SELECT COUNT(*) FROM users WHERE user_id = ? AND password = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, user.getUser_id());
			pStmt.setString(2, user.getPassword());

			ResultSet rs = pStmt.executeQuery();
			rs.next();

			// 一致行が 1 件なら成功
			if (rs.getInt(1) == 1) {
				loginResult = true;
			}

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			loginResult = false;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					loginResult = false;
				}
			}
		}
		return loginResult;
	}
}

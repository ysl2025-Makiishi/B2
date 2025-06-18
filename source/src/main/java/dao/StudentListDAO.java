package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.students;

public class StudentListDAO {

    // 生徒一覧を全件取得するメソッド
	public List<students> findAll() {
	    List<students> studentList = new ArrayList<>();
	    Connection conn = null;

	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        conn = DriverManager.getConnection(
	                "jdbc:mysql://localhost:3306/B2?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Tokyo",
	                "root", "password"
	        );

	        String sql = "SELECT name, gender, school_id FROM students ORDER BY id ASC";
	        PreparedStatement pStmt = conn.prepareStatement(sql);
	        ResultSet rs = pStmt.executeQuery();

	        while (rs.next()) {
	            students s = new students();
	            s.setName(rs.getString("name"));
	            s.setGender(rs.getString("gender"));
	            s.setSchool_name("仮の学校名");
	            studentList.add(s);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        studentList = null;
	    } finally {
	        try {
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return studentList;
	}
}

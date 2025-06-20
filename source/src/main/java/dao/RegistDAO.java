package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import dto.aspiration_schools;
import dto.schools;
import dto.students;

public class RegistDAO {
	
	//データベース接続
	 static {
	        try {
	            // MySQL用ドライバクラス名（JDBC 8以降）
	            Class.forName("com.mysql.cj.jdbc.Driver");
	        } catch (ClassNotFoundException e) {
	            // ドライバが見つからない場合、詳細を表示
	            e.printStackTrace();
	        }
	    }
	private Connection  getConnection() throws SQLException {
		String url = "jdbc:mysql://localhost:3306/B2?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Tokyo";
		String user = "root";
		String password = "password";
		return DriverManager.getConnection(url, user, password);
	}
	
	//studentsテーブルへの挿入
	public int insertStudent(students student) {
		String sql = "INSERT INTO students (name, furigana, school_id, birthday,"
				+ " gender, aspiration_school1_id, aspiration_school2_id, "
				+ "aspiration_school3_id, personality_id, created_at, updated_at)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
		
		int generatedId = -1;
		
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, student.getName());
            ps.setString(2, student.getFurigana());
            ps.setInt(3, student.getSchool_id());
            ps.setDate(4, new java.sql.Date(student.getBirthday().getTime()));
            ps.setString(5, student.getGender());
            ps.setInt(6, student.getAspiration_school1_id());
            ps.setInt(7, student.getAspiration_school2_id());
            ps.setInt(8, student.getAspiration_school3_id());
            ps.setInt(9, student.getPersonality_id()); // 空欄の場合は0やnull管理
            ps.executeUpdate();
            
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                generatedId = keys.getInt(1);
            }
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return generatedId;
	}
	
	//schoolsテーブルへの挿入（既にあるかチェック）
	public int insertOrGetSchoolId(schools school) {
		int schoolId = -1;
		
		try (Connection conn = getConnection()) {
			//存在チェック
			String checkSql = "SELECT id FROM schools WHERE school_name = ?";
			try(PreparedStatement checkPs = conn.prepareStatement(checkSql)) {
				checkPs.setString(1, school.getSchool_name());
				
				ResultSet rs = checkPs.executeQuery();
				if(rs.next()) {
					return rs.getInt("id");
				}
			}
			
			//存在しないなら挿入
			String insertSql = "INSERT INTO schools (school_name) VALUES (?)";
			try (PreparedStatement insertPs = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
				insertPs.setString(1, school.getSchool_name());
				insertPs.executeUpdate();
				
				ResultSet keys = insertPs.getGeneratedKeys();
				if (keys.next()) {
					schoolId = keys.getInt(1);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return schoolId;
	}
	
	//aspiration_schoolsテーブルへの挿入（既にあるかチェック）
	public int insertOrGetAspirationSchoolId(aspiration_schools asp) {
		int aspirationSchoolsId = -1;
		
		try (Connection conn = getConnection()) {
			//存在チェック
			
			String checkSql = "SELECT id FROM aspiration_schools WHERE "
					+ "aspiration_school_name = ?";
			
			try (PreparedStatement checkPs = conn.prepareStatement(checkSql)) {
				checkPs.setString(1, asp.getAspiration_school_name());
				ResultSet rs = checkPs.executeQuery();
				if (rs.next()) {
					return rs.getInt("id");
				}
			}
			
			// 存在しないなら挿入
			String insertSql = "INSERT INTO aspiration_schools (aspiration_school_name) VALUES (?)";
			
			try (PreparedStatement insertPs = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
				insertPs.setString(1, asp.getAspiration_school_name());
				insertPs.executeUpdate();
				ResultSet keys = insertPs.getGeneratedKeys();
				
				if(keys.next()) {
					aspirationSchoolsId = keys.getInt(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return aspirationSchoolsId;
	}
	
	
	//gpaテーブルへの挿入（複数科目）
	public void insertGrades(int studentId, Map<Integer, Integer> subjectGrades) {
		String sql = "INSERT INTO gpas (student_id, subject_id, gpa) VALUES"
				+ " (?, ?, ?)";
		
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			for (Map.Entry<Integer, Integer> entry : subjectGrades.entrySet()) {
				ps.setInt(1, studentId);
                ps.setInt(2, entry.getKey());   // subject_id
                ps.setInt(3, entry.getValue()); // score
                ps.addBatch();
			}
			
			ps.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Studentのidを取得するメソッド
//	public int getLastInsertedStudentId() {
//		int id = -1;
//		String sql = "SELECT LAST_INSERT_ID()";
//		
//		try(Connection conn = getConnection();
//			PreparedStatement ps = conn.prepareStatement(sql);
//			ResultSet rs = ps.executeQuery()) {
//				if(rs.next()) {
//					id = rs.getInt(1);
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		return id;
//	}
}

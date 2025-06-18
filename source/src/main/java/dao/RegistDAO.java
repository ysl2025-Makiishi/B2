package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Map;

public class RegistDAO {
	
	//生徒・学校・成績の一括登録
	public boolean registAll(String name, String furigana, String schoolName, 
			Date birthday, String gender, String aspiration1, 
			String aspiration2, String aspiration3, 
			Map<Integer, Integer> gpasMap // subject_id →gpa
			) {
		Connection conn = null;
		boolean success = false;
		
		try {
			conn = getConnection();
			conn.setAutoCommit(false);	//トランザクション開始
			
			int school_id = insertOrGetSchoolId(conn, schoolName);	//学校を登録または取得
			int student_id = insertStudent(conn, name, furigana, school_id, birthday, gender,
                    aspiration1, aspiration2, aspiration3);
			
			insertGpas(conn, student_id, gpasMap);	//成績9件を登録
			
			conn.commit();  //全て成功したらコミット
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			
			try {
				if(conn != null) conn.rollback();   //エラー時はロールバック 
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		
		return success;
	}
	
	//学校を登録orID取得
	private int insertOrGetSchoolId(Connection conn, String schoolName) throws SQLException {
		int id = 0;
		String sql = "SELECT id FROM schools WHERE school_name = ?";
		
		try (PreparedStatement stmt = conn.prepareStatement(sql)){
			stmt.setString(1, schoolName);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) return rs.getInt("id");
		}
		
		//登録がなければinsert
		sql = "INSERT INTO schools (school_name, created_at, updated_at) VALUES (?, NOW(), NOW())";
		
		try(PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, schoolName);
			stmt.executeUpdate();
			
			ResultSet rs =stmt.getGeneratedKeys();
			if(rs.next()) id = rs.getInt(1);
		}
		
		return id;
	}
	
	//生徒を登録し、生成を返す
	private int insertStudent(Connection conn, String name, String furigana,
			int school_id, Date birthday, String gender, String asp1, String asp2, 
			String asp3) throws SQLException {
		String sql = "INSERT INTO students " + "(name, furigana, school_id, "
				+ "birthday, gender, personality_id, aspiration_school1_id, "
				+ "aspiration_school2_id, aspiration_school3_id, created_at, "
				+ "updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, name);
            stmt.setString(2, furigana);
            stmt.setInt(3, school_id);
            stmt.setDate(4, new java.sql.Date(birthday.getTime()));
            stmt.setString(5, gender);
            stmt.setInt(6, 0); // 性格IDは未入力
            stmt.setString(7, asp1);
            stmt.setString(8, asp2);
            stmt.setString(9, asp3);
            stmt.setDate(10, null);
            stmt.setDate(11, null);
            
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            
            if (rs.next()) return rs.getInt(1);
		}
		
		return 0; 
	}
	
	//GPAを複数件登録
	private void insertGpas(Connection conn, int student_id, Map<Integer, Integer> gpasMap) throws SQLException {
		String sql = "INSERT INTO gpas(student_id, subject_id, gpa, created_at, updated_at) VALUES (?, ?, ?, NOW(), NOW())";
		
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			for(Map.Entry<Integer, Integer> entry : gpasMap.entrySet()) {
				stmt.setInt(1, student_id);
				stmt.setInt(2, entry.getKey()); 	//subject_id
				stmt.setInt(3, entry.getValue()); 	//gpa
				stmt.executeUpdate();
			}
		}
	}
	
	private Connection getConnection() throws SQLException {
	    String url = "jdbc:mysql://localhost:3306/b2"; // ←DB名
	    String user = "root";   // ←ユーザー名
	    String password = "password"; // ←パスワード
	    return DriverManager.getConnection(url, user, password);
	}
}

package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonalityDAO {
	
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
		String url = "jdbc:mysql://localhost:3306/b2?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Tokyo";
		String user = "root";
		String password = "password";
		return DriverManager.getConnection(url, user, password);
	}
	
	public Integer getPersonalityIdByName(String name) {
        Integer personalityId = null;
        String sql = "SELECT id FROM personalities WHERE personality_name = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                personalityId = rs.getInt("id");
                System.out.println("性格IDを取得: " + personalityId + "（性格名: " + name + "）");
            } else {
                System.out.println("性格名が見つかりません: " + name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return personalityId;
    }

    public boolean updateStudentPersonality(int studentId, int personalityId) {
        String sql = "UPDATE students SET personality_id = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setInt(1, personalityId);
            ps.setInt(2, studentId);
            int result = ps.executeUpdate();
            System.out.println("更新件数: " + result);
            return result == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}



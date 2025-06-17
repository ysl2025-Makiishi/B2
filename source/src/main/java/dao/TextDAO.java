package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.texts;

public class TextDAO {
    private final String JDBC_URL = "jdbc:mysql://localhost:3306/webapp2?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9";
    private final String DB_USER = "root";
    private final String DB_PASS = "password";

    public List<texts> findBySubjectAndPersonality(int subjectId, int personalityId) {
        List<texts> textList = new ArrayList<>();
        Connection conn = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

            String sql = "SELECT id, text_name, subject_id, personality_id, pages, note " +
                         "FROM texts WHERE subject_id = ? AND personality_id = ?";

            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, subjectId);
            pStmt.setInt(2, personalityId);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                texts text = new texts(
                    rs.getInt("id"),
                    rs.getString("text_name"),
                    rs.getInt("subject_id"),
                    rs.getInt("personality_id"),
                    rs.getInt("pages"),
                    rs.getString("note"),
                    rs.getDate("created_at"),
                    rs.getDate("updated_at")
                	
                );
                textList.add(text);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            textList = null;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return textList;
    }
}

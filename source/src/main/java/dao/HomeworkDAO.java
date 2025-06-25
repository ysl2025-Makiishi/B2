package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HomeworkDAO {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/b2?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Tokyo";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "password"; 
    
 // HomeworkDAO.java に追加
    public boolean existsHomework(int studentId, int subjectId) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            String sql = "SELECT COUNT(*) FROM homeworks WHERE student_id = ? AND subject_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, studentId);
                stmt.setInt(2, subjectId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    
    public void insertHomework(int studentId, int subjectId, int pagesPerDay) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            String sql = "INSERT INTO homeworks (student_id, subject_id, homework) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, studentId);
                stmt.setInt(2, subjectId);
                stmt.setInt(3, pagesPerDay);  // int型のページ数をそのままセット
                stmt.executeUpdate();
            }
        }
    }
    
    public void updateHomework(int studentId, int subjectId, int pagesPerDay) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            String sql = "UPDATE homeworks SET homework = ? WHERE student_id = ? AND subject_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, pagesPerDay);      // 更新後のページ数
                stmt.setInt(2, studentId);
                stmt.setInt(3, subjectId);
                stmt.executeUpdate();
            }
        }
    }
}


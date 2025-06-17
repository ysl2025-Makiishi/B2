package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.students;

public class SearchDAO {
    private final String JDBC_URL = "jdbc:mysql://localhost:3306/webapp2?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9";
    private final String DB_USER = "root";
    private final String DB_PASS = "password";

    public List<students> searchStudent(String name, String furigana, String school) {
        List<students> studentList = new ArrayList<>();
        Connection conn = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

            String sql = "SELECT s.id, s.name, s.furigana, s.school_id, s.birthday, s.gender, " +
                         "s.aspiration_school1_id, s.aspiration_school2_id, s.aspiration_school3_id, " +
                         "s.personality_id, s.created_at, s.updated_at " +
                         "FROM students s " +
                         "LEFT JOIN schools sch ON s.school_id = sch.id " +
                         "WHERE s.name LIKE ? AND s.furigana LIKE ? AND (sch.name LIKE ? OR ? = '%') " +
                         "ORDER BY s.id";

            PreparedStatement pStmt = conn.prepareStatement(sql);

            pStmt.setString(1, name != null && !name.isEmpty() ? "%" + name + "%" : "%");
            pStmt.setString(2, furigana != null && !furigana.isEmpty() ? "%" + furigana + "%" : "%");
            String schoolName = school != null && !school.isEmpty() ? "%" + school + "%" : "%";
            pStmt.setString(3, schoolName);
            pStmt.setString(4, schoolName); // OR ? = '%' の部分用

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                students s = new students(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("furigana"),
                    rs.getInt("school_id"),
                    rs.getDate("birthday"),
                    rs.getString("gender"),
                    rs.getInt("aspiration_school1_id"),
                    rs.getInt("aspiration_school2_id"),
                    rs.getInt("aspiration_school3_id"),
                    rs.getInt("personality_id"),
                    rs.getDate("created_at"),
                    rs.getDate("updated_at")
                );
                studentList.add(s);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            studentList = null;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return studentList;
    }}

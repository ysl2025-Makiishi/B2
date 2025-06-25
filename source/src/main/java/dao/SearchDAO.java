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
    private final String JDBC_URL = "jdbc:mysql://localhost:3306/b2?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9";
    private final String DB_USER = "root";
    private final String DB_PASS = "password";

    public List<students> searchStudent(String name, String furigana, String school) {
        List<students> studentList = new ArrayList<>();
        Connection conn = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

            StringBuilder sql = new StringBuilder(
                "SELECT s.id, s.name, s.furigana, s.school_id, sch.name AS school_name, s.birthday, s.gender, " +
                "s.aspiration_school1_id, s.aspiration_school2_id, s.aspiration_school3_id, " +
                "s.personality_id, s.created_at, s.updated_at " +
                "FROM students s " +
                "LEFT JOIN schools sch ON s.school_id = sch.id " +
                "WHERE 1=1"
            );

            List<String> params = new ArrayList<>();

            if (name != null && !name.isEmpty()) {
                sql.append(" AND s.name LIKE ?");
                params.add("%" + name + "%");
            }
            if (furigana != null && !furigana.isEmpty()) {
                sql.append(" AND s.furigana LIKE ?");
                params.add("%" + furigana + "%");
            }
            if (school != null && !school.isEmpty()) {
                sql.append(" AND sch.name LIKE ?");
                params.add("%" + school + "%");
            }

            sql.append(" ORDER BY s.id");

            PreparedStatement pStmt = conn.prepareStatement(sql.toString());

            for (int i = 0; i < params.size(); i++) {
                pStmt.setString(i + 1, params.get(i));
            }

            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                students s = new students(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("furigana"),
                    rs.getInt("school_id"),
                    rs.getString("school_name"),
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
    }
}

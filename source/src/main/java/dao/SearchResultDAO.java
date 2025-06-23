package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dto.students;
public class SearchResultDAO {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/B2?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Tokyo";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "password";
    public static List<students> searchByName(int id, String name, String furigana, String schoolName,
                                              String sort, int limit, int offset) {
        List<students> resultList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuilder sql = new StringBuilder("""
            SELECT students.*, schools.school_name
            FROM students
            LEFT JOIN schools ON students.school_id = schools.id
            WHERE 1=1
        """);
        List<Object> paramList = new ArrayList<>();
        if (name != null && !name.isEmpty()) {
            sql.append(" AND students.name LIKE ?");
            paramList.add("%" + name + "%");
        }
        if (furigana != null && !furigana.isEmpty()) {
            sql.append(" AND students.furigana LIKE ?");
            paramList.add("%" + furigana + "%");
        }
        if (schoolName != null && !schoolName.isEmpty()) {
            sql.append(" AND schools.school_name LIKE ?");
            paramList.add("%" + schoolName + "%");
        }
        switch (sort) {
        case "nameAsc":
            sql.append(" ORDER BY students.furigana COLLATE utf8mb4_unicode_ci ASC");
            break;
        case "nameDesc":
            sql.append(" ORDER BY students.furigana COLLATE utf8mb4_unicode_ci DESC");
            break;
        case "createdAsc":
            sql.append(" ORDER BY students.created_at ASC");
            break;
        case "createdDesc":
            sql.append(" ORDER BY students.created_at DESC");
            break;
        case "updatedAsc":
            sql.append(" ORDER BY students.updated_at ASC");
            break;
        case "updatedDesc":
            sql.append(" ORDER BY students.updated_at DESC");
            break;
        default:
            sql.append(" ORDER BY students.created_at DESC");
            break;
    }
        sql.append(" LIMIT ? OFFSET ?");
        paramList.add(limit);
        paramList.add(offset);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
            pstmt = conn.prepareStatement(sql.toString());
            // プレースホルダに値を設定
            for (int i = 0; i < paramList.size(); i++) {
                Object param = paramList.get(i);
                if (param instanceof String) {
                    pstmt.setString(i + 1, (String) param);
                } else if (param instanceof Integer) {
                    pstmt.setInt(i + 1, (Integer) param);
                }
            }
            rs = pstmt.executeQuery();
            while (rs.next()) {
                students s = new students();
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                s.setFurigana(rs.getString("furigana"));
                s.setSchool_id(rs.getInt("school_id"));
                s.setSchool_name(rs.getString("school_name"));
                s.setBirthday(rs.getDate("birthday"));
                s.setGender(rs.getString("gender"));
                s.setAspiration_school1_id(rs.getInt("aspiration_school1_id"));
                s.setAspiration_school2_id(rs.getInt("aspiration_school2_id"));
                s.setAspiration_school3_id(rs.getInt("aspiration_school3_id"));
                s.setPersonality_id(rs.getInt("personality_id"));
                s.setCreated_at(rs.getTimestamp("created_at"));
                s.setUpdated_at(rs.getTimestamp("updated_at"));
                resultList.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return resultList;
    }
    public static int countByName(String name, String furigana, String schoolName) {
        int count = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuilder sql = new StringBuilder("""
            SELECT COUNT(*) AS total
            FROM students
            LEFT JOIN schools ON students.school_id = schools.id
            WHERE 1=1
        """);
        List<Object> paramList = new ArrayList<>();
        if (name != null && !name.isEmpty()) {
            sql.append(" AND students.name LIKE ?");
            paramList.add("%" + name + "%");
        }
        if (furigana != null && !furigana.isEmpty()) {
            sql.append(" AND students.furigana LIKE ?");
            paramList.add("%" + furigana + "%");
        }
        if (schoolName != null && !schoolName.isEmpty()) {
            sql.append(" AND schools.school_name LIKE ?");
            paramList.add("%" + schoolName + "%");
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
            pstmt = conn.prepareStatement(sql.toString());
            for (int i = 0; i < paramList.size(); i++) {
                Object param = paramList.get(i);
                if (param instanceof String) {
                    pstmt.setString(i + 1, (String) param);
                }
            }
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return count;
    }
    
    public static boolean deleteById(int studentId) {
        Connection conn = null;
        boolean success = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
            conn.setAutoCommit(false);

            String[] deleteSqls = {
                "DELETE FROM reports WHERE student_id = ?",
                "DELETE FROM schedules WHERE student_id = ?",
                "DELETE FROM gpas WHERE student_id = ?",
                "DELETE FROM levels WHERE student_id = ?",
                "DELETE FROM exam_scores WHERE student_id = ?",
                "DELETE FROM homeworks WHERE student_id = ?",
                "DELETE FROM students WHERE id = ?"
            };

            for (String sql : deleteSqls) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, studentId);
                    ps.executeUpdate();
                }
            }

            conn.commit();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (Exception rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
        } finally {
            try { if (conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return success;
    }
}
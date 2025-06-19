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

        String baseSql = """
            SELECT students.*, schools.school_name
            FROM students
            LEFT JOIN schools ON students.school_id = schools.id
            WHERE students.name LIKE ?
              AND students.furigana LIKE ?
              AND schools.school_name LIKE ?
        """;

        String orderBy;
        switch (sort) {
        case "nameAsc":
            orderBy = " ORDER BY students.furigana COLLATE utf8mb4_unicode_ci ASC";
            break;
        case "nameDesc":
            orderBy = " ORDER BY students.furigana COLLATE utf8mb4_unicode_ci DESC";
            break;
        case "createdAsc":
            orderBy = " ORDER BY students.created_at ASC";
            break;
        case "createdDesc":
        default:
            orderBy = " ORDER BY students.created_at DESC";
            break;
    }

        // LIMIT と OFFSET を追加
        String sql = baseSql + orderBy + " LIMIT ? OFFSET ?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/B2?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Tokyo",
                "root", "password"
            );
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + name + "%");
            pstmt.setString(2, "%" + furigana + "%");
            pstmt.setString(3, "%" + schoolName + "%");
            pstmt.setInt(4, limit);
            pstmt.setInt(5, offset);

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

    /**
     * 総件数取得用（ページネーションのため）
     */
    public static int countByName(String name, String furigana, String schoolName) {
        int count = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = """
            SELECT COUNT(*) AS total
            FROM students
            LEFT JOIN schools ON students.school_id = schools.id
            WHERE students.name LIKE ?
              AND students.furigana LIKE ?
              AND schools.school_name LIKE ?
        """;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/B2?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Tokyo",
                "root", "password"
            );

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + name + "%");
            pstmt.setString(2, "%" + furigana + "%");
            pstmt.setString(3, "%" + schoolName + "%");

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
    
 // 追加：生徒をIDで削除するメソッド
    public static boolean deleteById(int studentId) {
        Connection conn = null;
        boolean success = false;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

            String sql = "DELETE FROM students WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, studentId);

            int rowsAffected = ps.executeUpdate();
            success = (rowsAffected > 0);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return success;
    }
    
    
}


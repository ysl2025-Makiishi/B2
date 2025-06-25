package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dto.texts;

public class TextDAO {

    static {
        try {
            // MySQL JDBCドライバ読み込み
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/b2?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Tokyo";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "password";

    // 教科と性格でテキスト取得
    public List<texts> getTextsBySubjectAndPersonality(int subjectId, int personalityId) {
        List<texts> resultList = new ArrayList<>();
        String sql = "SELECT * FROM texts WHERE subject_id = ? AND personality_id = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, subjectId);
            ps.setInt(2, personalityId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                texts t = new texts();
                t.setId(rs.getInt("id"));
                t.setTextName(rs.getString("text_name"));
                t.setSubjectId(rs.getInt("subject_id"));
                t.setPersonalityId(rs.getInt("personality_id"));
                t.setPages(rs.getInt("pages"));
                t.setNote(rs.getString("note"));
                t.setCreatedAt(rs.getTimestamp("created_at"));
                t.setUpdatedAt(rs.getTimestamp("updated_at"));
                resultList.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultList;
    }

    // ID指定でテキスト取得
    public texts findById(int id) {
        texts t = null;
        String sql = "SELECT * FROM texts WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                t = new texts();
                t.setId(rs.getInt("id"));
                t.setTextName(rs.getString("text_name"));
                t.setSubjectId(rs.getInt("subject_id"));
                t.setPersonalityId(rs.getInt("personality_id"));
                t.setPages(rs.getInt("pages"));
                t.setNote(rs.getString("note"));
                t.setCreatedAt(rs.getTimestamp("created_at"));
                t.setUpdatedAt(rs.getTimestamp("updated_at"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return t;
    }

    // 新規登録
    public boolean insert(texts t) {
        String sql = "INSERT INTO texts (text_name, subject_id, personality_id, pages, note, created_at, updated_at) VALUES (?, ?, ?, ?, ?, NOW(), NOW())";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, t.getTextName());
            ps.setInt(2, t.getSubjectId());
            ps.setInt(3, t.getPersonalityId());
            ps.setInt(4, t.getPages());
            ps.setString(5, t.getNote());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 更新
    public boolean update(texts t) {
        String sql = "UPDATE texts SET text_name = ?, subject_id = ?, personality_id = ?, pages = ?, note = ?, updated_at = NOW() WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, t.getTextName());
            ps.setInt(2, t.getSubjectId());
            ps.setInt(3, t.getPersonalityId());
            ps.setInt(4, t.getPages());
            ps.setString(5, t.getNote());
            ps.setInt(6, t.getId());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 削除
    public boolean delete(int id) {
        String sql = "DELETE FROM texts WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 生徒の選出テキスト登録処理
    public boolean registerSelectedText(int studentId, int subjectId, int textId) {
        String sql = """
            INSERT INTO schedules (student_id, subject_id, text_id, pages, created_at, updated_at)
            VALUES (?, ?, ?, (SELECT pages FROM texts WHERE id = ?), NOW(), NOW())
            ON DUPLICATE KEY UPDATE text_id = VALUES(text_id), updated_at = NOW()
        """;

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setInt(2, subjectId);
            ps.setInt(3, textId);
            ps.setInt(4, textId);

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

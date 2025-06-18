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

    private Connection getConnection() throws SQLException {
    	String url = "jdbc:mysql://localhost:3306/student";
    	String user = "root";
    	String password = "password";
        return DriverManager.getConnection(url, user, password);
    }

    // 全件取得
    public List<texts> findBySubjectAndPersonality(int subjectId, int personalityId) {
        List<texts> list = new ArrayList<>();
        String sql = "SELECT * FROM texts WHERE subject_id = ? AND personality_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
        	 // ★ 検索条件のデバッグ表示
            System.out.println("★SQL実行: subjectId = " + subjectId + ", personalityId = " + personalityId);

            stmt.setInt(1, subjectId);
            stmt.setInt(2, personalityId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                texts text = new texts();
                text.setId(rs.getInt("id"));
                text.setTextName(rs.getString("text_name"));
                text.setSubjectId(rs.getInt("subject_id"));
                text.setPersonalityId(rs.getInt("personality_id"));
                text.setPages(rs.getInt("pages"));
                text.setNote(rs.getString("note"));
                text.setCreatedAt(rs.getTimestamp("created_at"));
                text.setUpdatedAt(rs.getTimestamp("updated_at"));
                list.add(text);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("★SQLエラー：" + e.getMessage());
        }
     // ★ 検索結果の件数を出力
        System.out.println("★取得件数：" + list.size());

        return list;
    }


    // IDで1件取得
    public texts findById(int id) {
        String sql = "SELECT * FROM texts WHERE id = ?";
        texts text = null;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                text = new texts();
                text.setId(rs.getInt("id"));
                text.setTextName(rs.getString("text_name"));
                text.setSubjectId(rs.getInt("subject_id"));
                text.setPersonalityId(rs.getInt("personality_id"));
                text.setPages(rs.getInt("pages"));
                text.setNote(rs.getString("note"));
                text.setCreatedAt(rs.getTimestamp("created_at"));
                text.setUpdatedAt(rs.getTimestamp("updated_at"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return text;
    }

    // 登録
    public boolean insert(texts text) {
        String sql = "INSERT INTO texts (text_name, subject_id, personality_id, pages, note, created_at, updated_at) VALUES (?, ?, ?, ?, ?, NOW(), NOW())";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, text.getTextName());
            stmt.setInt(2, text.getSubjectId());
            stmt.setInt(3, text.getPersonalityId());
            stmt.setInt(4, text.getPages());
            stmt.setString(5, text.getNote());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 更新
    public boolean update(texts text) {
        String sql = "UPDATE texts SET text_name = ?, subject_id = ?, personality_id = ?, pages = ?, note = ?, updated_at = NOW() WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, text.getTextName());
            stmt.setInt(2, text.getSubjectId());
            stmt.setInt(3, text.getPersonalityId());
            stmt.setInt(4, text.getPages());
            stmt.setString(5, text.getNote());
            stmt.setInt(6, text.getId());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 削除
    public boolean delete(int id) {
        String sql = "DELETE FROM texts WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

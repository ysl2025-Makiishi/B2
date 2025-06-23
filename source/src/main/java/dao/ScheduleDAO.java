package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dto.schedules;

public class ScheduleDAO {

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/B2?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Tokyo",
            "root",
            "password"  // パスワードは適宜変更してください
        );
    }

    /**
     * schedulesテーブルにスケジュールを登録するメソッド
     * @param schedule 登録したいScheduleオブジェクト
     * @return 登録成功ならtrue、失敗ならfalse
     */

    public boolean upsertPages(schedules schedule) {
        String updateSql = "UPDATE schedules SET pages = ? WHERE student_id = ? AND subject_id = ?";
        String insertSql = "INSERT INTO schedules (student_id, subject_id, pages) VALUES (?, ?, ?)";

        try (Connection conn = getConnection()) {
            // 1. UPDATE を試みる
            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                updateStmt.setInt(1, schedule.getPages());
                updateStmt.setInt(2, schedule.getStudent_id());
                updateStmt.setInt(3, schedule.getSubject_id());

                int updated = updateStmt.executeUpdate();

                // 更新できたら true を返す
                if (updated > 0) {
                    return true;
                }
            }

            // 2. UPDATE が 0 行 → INSERT を試みる
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setInt(1, schedule.getStudent_id());
                insertStmt.setInt(2, schedule.getSubject_id());
                insertStmt.setInt(3, schedule.getPages());

                int inserted = insertStmt.executeUpdate();
                return inserted > 0;
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

}

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
            "jdbc:mysql://localhost:3306/b2?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Tokyo",
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
        String checkSql = "SELECT 1 FROM schedules WHERE student_id = ? AND subject_id = ?";
        String updateSql = "UPDATE schedules SET pages = ?, text_id = ? WHERE student_id = ? AND subject_id = ?";
        String insertSql = "INSERT INTO schedules (student_id, subject_id, text_id, pages) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection()) {
            ensureStudentExists(schedule.getStudent_id(), conn);
            ensureSubjectExists(schedule.getSubject_id(), conn);

            // 1. レコード存在チェック
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, schedule.getStudent_id());
                checkStmt.setInt(2, schedule.getSubject_id());
                var rs = checkStmt.executeQuery();
                if (rs.next()) {
                    // レコードが存在する → UPDATE
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setInt(1, schedule.getPages());
                        if (schedule.getText_id() == 0) {
                            updateStmt.setNull(2, java.sql.Types.INTEGER);
                        } else {
                            updateStmt.setInt(2, schedule.getText_id());
                        }


                        updateStmt.setInt(3, schedule.getStudent_id());
                        updateStmt.setInt(4, schedule.getSubject_id());
                        int updated = updateStmt.executeUpdate();
                        return updated > 0;
                    }
                } else {
                    // レコードが存在しない → INSERT
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                        insertStmt.setInt(1, schedule.getStudent_id());
                        insertStmt.setInt(2, schedule.getSubject_id());
                        if (schedule.getText_id() == 0) {
                            insertStmt.setNull(3, java.sql.Types.INTEGER); // ← ここ重要！
                        } else {
                            insertStmt.setInt(3, schedule.getText_id());
                        }



                        insertStmt.setInt(4, schedule.getPages());
                        int inserted = insertStmt.executeUpdate();
                        return inserted > 0;
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            //System.err.println("DB登録エラー: " + e.getMessage());
            return false;
        }
    }



private void ensureStudentExists(int studentId, Connection conn) throws SQLException {
    String selectSql = "SELECT id FROM students WHERE id = ?";
    String insertSql = "INSERT INTO students (id, name, furigana, school_id, birthday, gender, personality_id) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
        selectStmt.setInt(1, studentId);
        var rs = selectStmt.executeQuery();
        if (!rs.next()) {
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setInt(1, studentId);
                insertStmt.setString(2, "仮生徒" + studentId);
                insertStmt.setString(3, "かりせいと");
                insertStmt.setInt(4, 1); // 仮school_id
                insertStmt.setDate(5, java.sql.Date.valueOf("2000-01-01"));
                insertStmt.setString(6, "M");
                insertStmt.setInt(7, 1); // personality_id（仮）
                insertStmt.executeUpdate();
            }
        }
    }
}

private void ensureSubjectExists(int subjectId, Connection conn) throws SQLException {
    String selectSql = "SELECT id FROM subjects WHERE id = ?";
    String insertSql = "INSERT INTO subjects (id, subject_name) VALUES (?, ?)";

    try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
        selectStmt.setInt(1, subjectId);
        var rs = selectStmt.executeQuery();
        if (!rs.next()) {
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setInt(1, subjectId);
                insertStmt.setString(2, "仮科目" + subjectId);
                insertStmt.executeUpdate();
            }
        }
    }
}
}


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

    public boolean updatePages(schedules schedule) {
        String sql = "UPDATE schedules SET pages = ? WHERE student_id = ? AND subject_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, schedule.getPages());
            ps.setInt(2, schedule.getStudent_id());
            ps.setInt(3, schedule.getSubject_id());

            int result = ps.executeUpdate();
            return result > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }


}

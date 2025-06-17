package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentListDAO {

    // 生徒一覧を全件取得するメソッド
    public List<Student> findAll() {
        List<Student> studentList = new ArrayList<>();
        Connection conn = null;

        try {
            // JDBCドライバの読み込み
            Class.forName("com.mysql.cj.jdbc.Driver");

            // DB接続
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/webapp2?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9",
                "root",
                "password"
            );

            // SQLの準備・実行
            String sql = "SELECT name, school_id, gender FROM students ORDER BY id ASC";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            ResultSet rs = pStmt.executeQuery();

            // 結果をリストに追加
            while (rs.next()) {
                Student student = new Student(
                    rs.getInt("name"),
                    rs.getString("school_id"),
                    rs.getString("gender")
                );
                studentList.add(student);
            }

        } catch (Exception e) {
            e.printStackTrace();
            studentList = null;
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return studentList;
    }
}


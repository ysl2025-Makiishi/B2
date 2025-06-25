package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.students;

//dao/StudentListDAO.java
public class StudentListDAO {

 public List<students> findAll(String sort) {
     List<students> studentList = new ArrayList<>();
     Connection conn = null;

     try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         conn = DriverManager.getConnection(
             "jdbc:mysql://localhost:3306/B2?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Tokyo",
             "root", "password"
         );

         // デフォルトはID順
         String orderBy = "s.id ASC";

         // 並び替え条件に応じてSQLのORDER BY句を切り替える
         if (sort != null) {
        	// dao/StudentListDAO.java の該当箇所
        	 switch (sort) {
        	     case "createdAsc":
        	         orderBy = "s.created_at ASC";
        	         break;
        	     case "createdDesc":
        	         orderBy = "s.created_at DESC";
        	         break;
        	     case "updatedAsc":
        	         orderBy = "s.updated_at ASC";
        	         break;
        	     case "updatedDesc":
        	         orderBy = "s.updated_at DESC";
        	         break;
        	     case "nameAsc":
        	         orderBy = "s.furigana COLLATE utf8mb4_ja_0900_as_cs ASC";  // ← 変更
        	         break;
        	     case "nameDesc":
        	         orderBy = "s.furigana COLLATE utf8mb4_ja_0900_as_cs DESC"; // ← 変更
        	         break;
        	 }


         }

         // SQL文（並び順を変える）
         String sql = "SELECT s.id, s.name, s.furigana, s.gender, sc.school_name " +
                 "FROM students s " +
                 "JOIN schools sc ON s.school_id = sc.id " +
                 "ORDER BY " + orderBy;


         PreparedStatement pStmt = conn.prepareStatement(sql);
         ResultSet rs = pStmt.executeQuery();

         while (rs.next()) {
             students s = new students();
             s.setId(rs.getInt("id")); 
             s.setName(rs.getString("name"));
             s.setGender(rs.getString("gender"));
             s.setSchool_name(rs.getString("school_name"));
             s.setFurigana(rs.getString("furigana"));


             studentList.add(s);
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
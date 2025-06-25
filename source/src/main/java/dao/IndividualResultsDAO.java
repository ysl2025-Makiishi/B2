package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.IndividualResults;
import dto.IndividualResults.ExamScore;
import dto.IndividualResults.Gpa;
import dto.IndividualResults.Schedule;

public class IndividualResultsDAO {
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/b2?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Tokyo";
	private static final String DB_USER = "root";
	private static final String DB_PASS = "password";

	// 既存のgetStudentInfoメソッドを修正
	public static IndividualResults getStudentInfo(int studentId) {
		IndividualResults result = null;

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String sql = """
						SELECT s.*, sc.school_name, p.personality_name,
						       a1.aspiration_school_name AS asp1_name,
						       a2.aspiration_school_name AS asp2_name,
						       a3.aspiration_school_name AS asp3_name,
						       g_jp.gpa AS gpa_jp, g_ss.gpa AS gpa_ss, g_ma.gpa AS gpa_ma,
						       g_sc.gpa AS gpa_sc, g_en.gpa AS gpa_en, g_mu.gpa AS gpa_mu,
						       g_ar.gpa AS gpa_ar, g_pe.gpa AS gpa_pe, g_te.gpa AS gpa_te
						FROM students s
						LEFT JOIN schools sc ON s.school_id = sc.id
						LEFT JOIN personalities p ON s.personality_id = p.id
						LEFT JOIN aspiration_schools a1 ON s.aspiration_school1_id = a1.id
						LEFT JOIN aspiration_schools a2 ON s.aspiration_school2_id = a2.id
						LEFT JOIN aspiration_schools a3 ON s.aspiration_school3_id = a3.id
						LEFT JOIN gpas g_jp ON g_jp.student_id = s.id AND g_jp.subject_id = 1
						LEFT JOIN gpas g_ss ON g_ss.student_id = s.id AND g_ss.subject_id = 2
						LEFT JOIN gpas g_ma ON g_ma.student_id = s.id AND g_ma.subject_id = 3
						LEFT JOIN gpas g_sc ON g_sc.student_id = s.id AND g_sc.subject_id = 4
						LEFT JOIN gpas g_en ON g_en.student_id = s.id AND g_en.subject_id = 5
						LEFT JOIN gpas g_mu ON g_mu.student_id = s.id AND g_mu.subject_id = 6
						LEFT JOIN gpas g_ar ON g_ar.student_id = s.id AND g_ar.subject_id = 7
						LEFT JOIN gpas g_pe ON g_pe.student_id = s.id AND g_pe.subject_id = 8
						LEFT JOIN gpas g_te ON g_te.student_id = s.id AND g_te.subject_id = 9
						WHERE s.id = ?
					""";

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, studentId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				result = new IndividualResults();
				result.setId(rs.getInt("id"));
				result.setName(rs.getString("name"));
				result.setFurigana(rs.getString("furigana"));
				result.setGender(rs.getString("gender"));
				result.setBirthday(rs.getDate("birthday"));
				result.setSchoolName(rs.getString("school_name"));
				result.setPersonalityName(rs.getString("personality_name"));
				result.setAspirationSchool1Name(rs.getString("asp1_name"));
				result.setAspirationSchool2Name(rs.getString("asp2_name"));
				result.setAspirationSchool3Name(rs.getString("asp3_name"));
				result.setGpaJp(rs.getInt("gpa_jp"));
				result.setGpaSs(rs.getInt("gpa_ss"));
				result.setGpaMa(rs.getInt("gpa_ma"));
				result.setGpaSc(rs.getInt("gpa_sc"));
				result.setGpaEn(rs.getInt("gpa_en"));
				result.setGpaMu(rs.getInt("gpa_mu"));
				result.setGpaAr(rs.getInt("gpa_ar"));
				result.setGpaPe(rs.getInt("gpa_pe"));
				result.setGpaTe(rs.getInt("gpa_te"));
			}

			if (result != null) {
				try {
					result.setGpaList(getGpaList(conn, studentId));
					result.setExamResults(getExamResults(conn, studentId));

					// ★ 追加：スケジュール（テキスト選出）データを取得
					result.setSchedules(getSchedules(conn, studentId));

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	// === 新規追加：更新メソッド ===

	/**
	 * 生徒の基本情報を更新（学校名・志望校も含む）
	 */
	public static boolean updateStudentBasicInfo(int studentId, String name, String furigana, String gender,
			String birthday, String schoolName, String asp1, String asp2, String asp3) {
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			conn.setAutoCommit(false);

			// 1. 学校IDを取得/作成
			Integer schoolId = getOrCreateSchoolId(conn, schoolName);

			// 2. 志望校IDを取得/作成
			Integer asp1Id = getOrCreateAspirationSchoolId(conn, asp1);
			Integer asp2Id = getOrCreateAspirationSchoolId(conn, asp2);
			Integer asp3Id = getOrCreateAspirationSchoolId(conn, asp3);

			// 3. 生徒情報を更新
			String sql = """
					UPDATE students
					SET name = ?, furigana = ?, gender = ?, birthday = ?,
					    school_id = ?, aspiration_school1_id = ?, aspiration_school2_id = ?, aspiration_school3_id = ?,
					    updated_at = NOW()
					WHERE id = ?
					""";

			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setString(1, name);
				ps.setString(2, furigana);
				ps.setString(3, gender);
				ps.setString(4, birthday);

				// nullの場合はsetNullを使用
				if (schoolId != null) {
					ps.setInt(5, schoolId);
				} else {
					ps.setNull(5, java.sql.Types.INTEGER);
				}

				if (asp1Id != null) {
					ps.setInt(6, asp1Id);
				} else {
					ps.setNull(6, java.sql.Types.INTEGER);
				}

				if (asp2Id != null) {
					ps.setInt(7, asp2Id);
				} else {
					ps.setNull(7, java.sql.Types.INTEGER);
				}

				if (asp3Id != null) {
					ps.setInt(8, asp3Id);
				} else {
					ps.setNull(8, java.sql.Types.INTEGER);
				}

				ps.setInt(9, studentId);

				int result = ps.executeUpdate();
				conn.commit();
				return result > 0;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 学校名から学校IDを取得、存在しない場合は作成
	 */
	private static Integer getOrCreateSchoolId(Connection conn, String schoolName) throws SQLException {
		if (schoolName == null || schoolName.trim().isEmpty()) {
			return null; // 0ではなくnullを返す
		}

		// 既存の学校IDを検索
		String selectSql = "SELECT id FROM schools WHERE school_name = ?";
		try (PreparedStatement ps = conn.prepareStatement(selectSql)) {
			ps.setString(1, schoolName.trim());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt("id");
			}
		}

		// 存在しない場合は新規作成
		String insertSql = "INSERT INTO schools (school_name, created_at, updated_at) VALUES (?, NOW(), NOW())";
		try (PreparedStatement ps = conn.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, schoolName.trim());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				return rs.getInt(1);
			}
		}

		return null;
	}

	/**
	 * 志望校名から志望校IDを取得、存在しない場合は作成
	 */
	private static Integer getOrCreateAspirationSchoolId(Connection conn, String schoolName) throws SQLException {
		if (schoolName == null || schoolName.trim().isEmpty()) {
			return null; // 0ではなくnullを返す
		}

		// 既存の志望校IDを検索
		String selectSql = "SELECT id FROM aspiration_schools WHERE aspiration_school_name = ?";
		try (PreparedStatement ps = conn.prepareStatement(selectSql)) {
			ps.setString(1, schoolName.trim());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt("id");
			}
		}

		// 存在しない場合は新規作成
		String insertSql = "INSERT INTO aspiration_schools (aspiration_school_name, created_at, updated_at) VALUES (?, NOW(), NOW())";
		try (PreparedStatement ps = conn.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, schoolName.trim());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				return rs.getInt(1);
			}
		}

		return null;
	}

	/**
	 * GPAデータを更新（DELETE + INSERT方式）
	 */
	public static boolean updateGPA(int studentId, String gpaJp, String gpaSs, String gpaMa, String gpaSc, String gpaEn,
			String gpaMu, String gpaAr, String gpaPe, String gpaTe) {

		String[] gpaValues = { gpaJp, gpaSs, gpaMa, gpaSc, gpaEn, gpaMu, gpaAr, gpaPe, gpaTe };

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			conn.setAutoCommit(false); // トランザクション開始

			try {
				// 1. 既存のGPAデータを削除
				String deleteSql = "DELETE FROM gpas WHERE student_id = ?";
				try (PreparedStatement deletePs = conn.prepareStatement(deleteSql)) {
					deletePs.setInt(1, studentId);
					deletePs.executeUpdate();
				}

				// 2. 新しいGPAデータを挿入
				String insertSql = "INSERT INTO gpas (student_id, subject_id, gpa, created_at, updated_at) VALUES (?, ?, ?, NOW(), NOW())";
				int insertCount = 0;

				try (PreparedStatement insertPs = conn.prepareStatement(insertSql)) {
					for (int i = 0; i < gpaValues.length; i++) {
						if (gpaValues[i] != null && !gpaValues[i].trim().isEmpty()) {
							try {
								int gpaValue = Integer.parseInt(gpaValues[i].trim());
								insertPs.setInt(1, studentId);
								insertPs.setInt(2, i + 1); // subject_id は1から9
								insertPs.setInt(3, gpaValue);

								int result = insertPs.executeUpdate(); // 1件ずつ実行
								if (result > 0) {
									insertCount++;
								}
							} catch (NumberFormatException e) {
								// GPA値が不正な場合はスキップ
							}
						}
					}
				}

				// 3. トランザクションをコミット
				conn.commit();
				return insertCount > 0;

			} catch (SQLException e) {
				// エラー時はロールバック
				conn.rollback();
				throw e;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 新しい模試結果を登録
	 */
	public static boolean insertExamResults(int studentId, String[] examNames, String[] examDates,
			String[] examSubjects, String[] examScores, String[] examDevs, String[] examAvgs) {

		if (examNames == null || examNames.length == 0) {
			return true; // エラーではない
		}

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			conn.setAutoCommit(false);

			// exam_names テーブルに模試名を登録/取得
			String getExamNameIdSql = """
						INSERT INTO exam_names (exam_name, created_at, updated_at)
						VALUES (?, NOW(), NOW())
						ON DUPLICATE KEY UPDATE id = LAST_INSERT_ID(id)
					""";

			// exams テーブルに試験を登録
			String insertExamSql = """
						INSERT INTO exams (exam_name_id, exam_date, created_at, updated_at)
						VALUES (?, ?, NOW(), NOW())
					""";

			// exam_scores テーブルに結果を登録
			String insertScoreSql = """
						INSERT INTO exam_scores (exam_id, student_id, subject_id, score, deviation_value, average_score, created_at, updated_at)
						VALUES (?, ?, ?, ?, ?, ?, NOW(), NOW())
					""";

			for (int i = 0; i < examNames.length; i++) {
				if (examNames[i] != null && !examNames[i].trim().isEmpty() && examDates[i] != null
						&& !examDates[i].trim().isEmpty()) {

					// 1. exam_name_id を取得
					int examNameId;
					try (PreparedStatement ps1 = conn.prepareStatement(getExamNameIdSql,
							PreparedStatement.RETURN_GENERATED_KEYS)) {
						ps1.setString(1, examNames[i]);
						ps1.executeUpdate();
						try (ResultSet rs = ps1.getGeneratedKeys()) {
							rs.next();
							examNameId = rs.getInt(1);
						}
					}

					// 2. exam を登録
					int examId;
					try (PreparedStatement ps2 = conn.prepareStatement(insertExamSql,
							PreparedStatement.RETURN_GENERATED_KEYS)) {
						ps2.setInt(1, examNameId);
						ps2.setString(2, examDates[i]);
						ps2.executeUpdate();
						try (ResultSet rs = ps2.getGeneratedKeys()) {
							rs.next();
							examId = rs.getInt(1);
						}
					}

					// 3. exam_score を登録
					try (PreparedStatement ps3 = conn.prepareStatement(insertScoreSql)) {
						ps3.setInt(1, examId);
						ps3.setInt(2, studentId);
						ps3.setInt(3, getSubjectId(examSubjects[i])); // 教科名からIDを取得
						ps3.setInt(4, Integer.parseInt(examScores[i]));
						ps3.setDouble(5, Double.parseDouble(examDevs[i]));
						ps3.setDouble(6, Double.parseDouble(examAvgs[i]));
						ps3.executeUpdate();
					}
				}
			}

			conn.commit();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 教科名から subject_id を取得
	 */
	private static int getSubjectId(String subjectName) {
		switch (subjectName) {
		case "国語":
			return 1;
		case "数学":
			return 2; // ← 修正
		case "英語":
			return 3; // ← 修正
		case "理科":
			return 4;
		case "社会":
			return 5; // ← 修正
		case "保健体育":
			return 6;
		case "技術家庭":
			return 7;
		case "美術":
			return 8;
		case "音楽":
			return 9;
		case "総合":
			return 10;
		default:
			return 1; // デフォルトは国語
		}
	}

	// 既存のプライベートメソッド
	private static List<Gpa> getGpaList(Connection conn, int studentId) throws SQLException {
		List<Gpa> list = new ArrayList<>();
		String sql = """
					SELECT s.subject_name, g.gpa
					FROM gpas g
					JOIN subjects s ON g.subject_id = s.id
					WHERE g.student_id = ?
				""";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, studentId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Gpa g = new Gpa();
				g.setSubjectName(rs.getString("subject_name"));
				g.setGpa(rs.getInt("gpa"));
				list.add(g);
			}
		}
		return list;
	}

	private static List<ExamScore> getExamResults(Connection conn, int studentId) throws SQLException {
		List<ExamScore> list = new ArrayList<>();

		String sql = """
				    SELECT en.exam_name, ex.exam_date, sub.subject_name,
				           es.score, es.deviation_value, es.average_score
				    FROM exam_scores es
				    JOIN exams ex ON es.exam_id = ex.id
				    JOIN exam_names en ON ex.exam_name_id = en.id
				    JOIN subjects sub ON es.subject_id = sub.id
				    WHERE es.student_id = ?
				    ORDER BY ex.exam_date DESC
				""";

		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, studentId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ExamScore exam = new ExamScore();
				exam.setExamName(rs.getString("exam_name"));
				exam.setExamDate(rs.getDate("exam_date"));
				exam.setSubjectName(rs.getString("subject_name"));
				exam.setScore(rs.getInt("score"));
				exam.setDeviationValue(rs.getDouble("deviation_value"));
				exam.setAverageScore(rs.getDouble("average_score"));
				list.add(exam);
			}
		}

		return list;
	}

	// ★ 新規追加：スケジュール（テキスト選出）データ取得メソッド
	private static List<Schedule> getSchedules(Connection conn, int studentId) throws SQLException {
		List<Schedule> list = new ArrayList<>();

		String sql = """
					SELECT sch.id, sch.student_id, sch.subject_id, sch.text_id, sch.pages,
					       sub.subject_name, t.text_name, t.pages as total_pages
					FROM schedules sch
					LEFT JOIN subjects sub ON sch.subject_id = sub.id
					LEFT JOIN texts t ON sch.text_id = t.id
					WHERE sch.student_id = ?
					ORDER BY sch.subject_id ASC
				""";

		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, studentId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Schedule schedule = new Schedule();
				schedule.setId(rs.getInt("id"));
				schedule.setStudentId(rs.getInt("student_id"));
				schedule.setSubjectId(rs.getInt("subject_id"));
				schedule.setTextId(rs.getInt("text_id"));
				schedule.setPages(rs.getInt("pages"));
				schedule.setSubjectName(rs.getString("subject_name"));
				schedule.setTextName(rs.getString("text_name"));
				schedule.setTotalPages(rs.getInt("total_pages"));
				list.add(schedule);
			}
		}

		return list;
	}

	// ★ 新規追加：スケジュール更新メソッド
	public static boolean updateSchedule(int studentId, int subjectId, int textId, int pages) {
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			String sql = """
						INSERT INTO schedules (student_id, subject_id, text_id, pages, created_at, updated_at)
						VALUES (?, ?, ?, ?, NOW(), NOW())
						ON DUPLICATE KEY UPDATE
						text_id = VALUES(text_id),
						pages = VALUES(pages),
						updated_at = NOW()
					""";

			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setInt(1, studentId);
				ps.setInt(2, subjectId);
				ps.setInt(3, textId);
				ps.setInt(4, pages);

				int result = ps.executeUpdate();
				return result > 0;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// ★ 新規追加：スケジュール削除メソッド
	public static boolean deleteSchedule(int studentId, int subjectId) {
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			String sql = "DELETE FROM schedules WHERE student_id = ? AND subject_id = ?";

			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setInt(1, studentId);
				ps.setInt(2, subjectId);

				int result = ps.executeUpdate();
				return result > 0;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
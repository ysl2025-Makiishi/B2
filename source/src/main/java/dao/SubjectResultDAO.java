package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.IndividualResults.ExamScore;
import dto.SubjectData;

public class SubjectResultDAO {
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/B2?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Tokyo";
	private static final String DB_USER = "root";
	private static final String DB_PASS = "password";

	/**
	 * 教科別学習データを取得
	 */
	public static SubjectData getSubjectData(int studentId, int subjectId) {
		SubjectData result = new SubjectData();
		result.setStudentId(studentId);
		result.setSubjectId(subjectId);

		System.out.println("=== SubjectData取得開始 ===");
		System.out.println("studentId = " + studentId + ", subjectId = " + subjectId);

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			// reports テーブルから学習データを取得
			String sql = """
					SELECT did_last_time, do_next_time, homework, note
					FROM reports
					WHERE student_id = ? AND subject_id = ?
					ORDER BY created_at DESC
					LIMIT 1
					""";

			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setInt(1, studentId);
				ps.setInt(2, subjectId);
				ResultSet rs = ps.executeQuery();

				if (rs.next()) {
					result.setLastContent(rs.getString("did_last_time"));
					result.setNextContent(rs.getString("do_next_time"));
					result.setHomework(rs.getString("homework"));
					result.setNote(rs.getString("note"));
					System.out.println("教科別学習データ取得成功");
					System.out.println("lastContent = " + result.getLastContent());
				} else {
					System.out.println("教科別学習データが存在しません（初回アクセス）");
				}
			} catch (SQLException e) {
				System.out.println("学習データSQL実行エラー: " + e.getMessage());
				e.printStackTrace();
			}

			// levels テーブルから理解度を取得
			String levelSql = "SELECT level FROM levels WHERE student_id = ? AND subject_id = ?";
			try (PreparedStatement ps = conn.prepareStatement(levelSql)) {
				ps.setInt(1, studentId);
				ps.setInt(2, subjectId);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					result.setUnderstanding(String.valueOf(rs.getInt("level")));
				}
			} catch (SQLException e) {
				System.out.println("理解度取得エラー: " + e.getMessage());
			}

			// homeworks テーブルから宿題ページ数を取得
			String homeworkSql = "SELECT homework FROM homeworks WHERE student_id = ? AND subject_id = ?";
			try (PreparedStatement ps = conn.prepareStatement(homeworkSql)) {
				ps.setInt(1, studentId);
				ps.setInt(2, subjectId);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					result.setHomeworkPages(String.valueOf(rs.getInt("homework")));
				}
			} catch (SQLException e) {
				System.out.println("宿題ページ数取得エラー: " + e.getMessage());
			}

			// 教科別模試結果を取得
			try {
				result.setExamResults(getSubjectExamResults(conn, studentId, subjectId));
			} catch (SQLException e) {
				System.out.println("模試結果取得エラー: " + e.getMessage());
				e.printStackTrace();
			}

		} catch (SQLException e) {
			System.out.println("DB接続エラー: " + e.getMessage());
			e.printStackTrace();
		}

		System.out.println("=== SubjectData取得完了 ===");
		return result;
	}

	/**
	 * 教科別模試結果を取得
	 */
	private static List<ExamScore> getSubjectExamResults(Connection conn, int studentId, int subjectId)
			throws SQLException {
		List<ExamScore> list = new ArrayList<>();

		String sql = """
				SELECT es.id, en.exam_name, ex.exam_date, es.score, es.deviation_value, es.average_score
				FROM exam_scores es
				JOIN exams ex ON es.exam_id = ex.id
				JOIN exam_names en ON ex.exam_name_id = en.id
				WHERE es.student_id = ? AND es.subject_id = ?
				ORDER BY ex.exam_date DESC
				""";

		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, studentId);
			ps.setInt(2, subjectId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ExamScore exam = new ExamScore();
				exam.setId(rs.getInt("id"));
				exam.setExamName(rs.getString("exam_name"));
				exam.setExamDate(rs.getDate("exam_date"));
				exam.setScore(rs.getInt("score"));
				exam.setDeviationValue(rs.getDouble("deviation_value"));
				exam.setAverageScore(rs.getDouble("average_score"));
				list.add(exam);
			}

			System.out.println("教科別模試結果取得: " + list.size() + "件");
		}

		return list;
	}

	/**
	 * 教科別学習データを更新
	 */
	public static boolean updateSubjectData(int studentId, int subjectId, String understanding, String textSelection,
			String schedule, String homeworkPages, String lastContent, String nextContent, String homework,
			String note) {

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			conn.setAutoCommit(false);

			try {
				// 1. reports テーブルを更新
				String reportSql = """
						INSERT INTO reports (student_id, subject_id, did_last_time, do_next_time, homework, note, created_at, updated_at)
						VALUES (?, ?, ?, ?, ?, ?, NOW(), NOW())
						ON DUPLICATE KEY UPDATE
						did_last_time = VALUES(did_last_time),
						do_next_time = VALUES(do_next_time),
						homework = VALUES(homework),
						note = VALUES(note),
						updated_at = NOW()
						""";

				try (PreparedStatement ps = conn.prepareStatement(reportSql)) {
					ps.setInt(1, studentId);
					ps.setInt(2, subjectId);
					ps.setString(3, lastContent);
					ps.setString(4, nextContent);
					ps.setString(5, homework);
					ps.setString(6, note);
					ps.executeUpdate();
				}

				// 2. levels テーブルを更新（理解度）
				if (understanding != null && !understanding.trim().isEmpty()) {
					String levelSql = """
							INSERT INTO levels (student_id, subject_id, level, created_at, updated_at)
							VALUES (?, ?, ?, NOW(), NOW())
							ON DUPLICATE KEY UPDATE
							level = VALUES(level),
							updated_at = NOW()
							""";

					try (PreparedStatement ps = conn.prepareStatement(levelSql)) {
						ps.setInt(1, studentId);
						ps.setInt(2, subjectId);
						ps.setInt(3, Integer.parseInt(understanding));
						ps.executeUpdate();
					}
				}

				// 3. homeworks テーブルを更新（宿題ページ数）
				if (homeworkPages != null && !homeworkPages.trim().isEmpty()) {
					String homeworkSql = """
							INSERT INTO homeworks (student_id, subject_id, homework, created_at, updated_at)
							VALUES (?, ?, ?, NOW(), NOW())
							ON DUPLICATE KEY UPDATE
							homework = VALUES(homework),
							updated_at = NOW()
							""";

					try (PreparedStatement ps = conn.prepareStatement(homeworkSql)) {
						ps.setInt(1, studentId);
						ps.setInt(2, subjectId);
						ps.setInt(3, Integer.parseInt(homeworkPages));
						ps.executeUpdate();
					}
				}

				conn.commit();
				System.out.println("教科別学習データ更新完了");
				return true;

			} catch (Exception e) {
				conn.rollback();
				System.out.println("教科別学習データ更新エラー: " + e.getMessage());
				e.printStackTrace();
				return false;
			}

		} catch (SQLException e) {
			System.out.println("DB接続エラー: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 模試結果を削除
	 */
	public static boolean deleteExamResult(int examId) {
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			String sql = "DELETE FROM exam_scores WHERE id = ?";

			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setInt(1, examId);
				int result = ps.executeUpdate();
				System.out.println("模試結果削除: " + result + "件");
				return result > 0;
			}

		} catch (SQLException e) {
			System.out.println("模試結果削除エラー: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 模試結果を更新
	 */
	public static boolean updateExamResult(int examId, int score, double deviationValue, double averageScore) {
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			String sql = """
					UPDATE exam_scores
					SET score = ?, deviation_value = ?, average_score = ?, updated_at = NOW()
					WHERE id = ?
					""";

			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setInt(1, score);
				ps.setDouble(2, deviationValue);
				ps.setDouble(3, averageScore);
				ps.setInt(4, examId);

				int result = ps.executeUpdate();
				System.out.println("模試結果更新: " + result + "件");
				return result > 0;
			}

		} catch (SQLException e) {
			System.out.println("模試結果更新エラー: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
}
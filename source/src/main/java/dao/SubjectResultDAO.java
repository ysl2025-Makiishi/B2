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

		// System.out.println("=== SubjectData取得開始 ===");
		// System.out.println("studentId = " + studentId + ", subjectId = " +
		// subjectId);

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
					// System.out.println("教科別学習データ取得成功");
				} else {
					// System.out.println("教科別学習データが存在しません（初回アクセス）");
				}
			} catch (SQLException e) {
				// System.out.println("学習データSQL実行エラー: " + e.getMessage());
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
					// System.out.println("理解度取得: " + result.getUnderstanding());
				}
			} catch (SQLException e) {
				// System.out.println("理解度取得エラー: " + e.getMessage());
			}

			// schedulesテーブルから実際に選出されたテキストを取得（教科ID一致確認版）
			String textSql = """
					SELECT t.text_name
					FROM schedules s
					JOIN texts t ON s.text_id = t.id
					WHERE s.student_id = ? AND s.subject_id = ? AND t.subject_id = ?
					ORDER BY s.updated_at DESC
					LIMIT 1
					""";
			// System.out.println("テキスト選出SQL: " + textSql);
			// System.out.println("パラメータ: studentId=" + studentId + ", subjectId=" +
			// subjectId + " (2回指定)");

			try (PreparedStatement ps = conn.prepareStatement(textSql)) {
				ps.setInt(1, studentId);
				ps.setInt(2, subjectId);
				ps.setInt(3, subjectId); // textsテーブルのsubject_idも確認
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					String textName = rs.getString("text_name");
					result.setTextSelection(textName);
					// System.out.println("取得されたテキスト: " + textName);
				} else {
					// System.out.println("テキストデータなし");
					result.setTextSelection(null); // 明示的にnullに設定
				}
			} catch (SQLException e) {
				System.out.println("テキスト選出取得エラー: " + e.getMessage());
				e.printStackTrace();
			}

			// schedules テーブルからスケジュールを取得（教科ID一致確認版）
			String scheduleSql = """
					SELECT t.text_name, s.pages
					FROM schedules s
					JOIN texts t ON s.text_id = t.id
					WHERE s.student_id = ? AND s.subject_id = ? AND t.subject_id = ?
					ORDER BY s.updated_at DESC
					LIMIT 1
					""";
			System.out.println("スケジュールSQL: " + scheduleSql);

			try (PreparedStatement ps = conn.prepareStatement(scheduleSql)) {
				ps.setInt(1, studentId);
				ps.setInt(2, subjectId);
				ps.setInt(3, subjectId); // textsテーブルのsubject_idも確認
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					String textName = rs.getString("text_name");
					int pages = rs.getInt("pages");
					String schedule = textName + " " + pages + "ページ";
					result.setSchedule(schedule);
					// System.out.println("取得されたスケジュール: " + schedule);
				} else {
					// System.out.println("スケジュールデータなし");
					result.setSchedule(null); // 明示的にnullに設定
				}
			} catch (SQLException e) {
				// System.out.println("スケジュール取得エラー: " + e.getMessage());
				e.printStackTrace();
			}

			// homeworks テーブルから宿題ページ数を取得
			String homeworkSql = "SELECT homework FROM homeworks WHERE student_id = ? AND subject_id = ?";
			try (PreparedStatement ps = conn.prepareStatement(homeworkSql)) {
				ps.setInt(1, studentId);
				ps.setInt(2, subjectId);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					result.setHomeworkPages(String.valueOf(rs.getInt("homework")));
					// System.out.println("宿題ページ数取得: " + result.getHomeworkPages());
				}
			} catch (SQLException e) {
				// System.out.println("宿題ページ数取得エラー: " + e.getMessage());
			}

			// 教科別模試結果を取得
			try {
				result.setExamResults(getSubjectExamResults(conn, studentId, subjectId));
				// System.out.println("模試結果取得: " + result.getExamResults().size() + "件");
			} catch (SQLException e) {
				// System.out.println("模試結果取得エラー: " + e.getMessage());
				e.printStackTrace();
			}

		} catch (SQLException e) {
			// System.out.println("DB接続エラー: " + e.getMessage());
			e.printStackTrace();
		}

//		System.out.println("=== SubjectData取得完了 ===");
//		System.out.println("最終結果 - テキスト選出: " + result.getTextSelection());
//		System.out.println("最終結果 - スケジュール: " + result.getSchedule());
//		System.out.println("最終結果 - 理解度: " + result.getUnderstanding());
		return result;
	}

	/**
	 * 教科別模試結果を取得
	 */
	private static List<ExamScore> getSubjectExamResults(Connection conn, int studentId, int subjectId)
			throws SQLException {
		List<ExamScore> list = new ArrayList<>();

		String sql = """
				SELECT es.id, ex.id as exam_id, en.id as exam_name_id, en.exam_name, ex.exam_date,
				       es.score, es.deviation_value, es.average_score
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
				exam.setExamId(rs.getInt("exam_id"));
				exam.setExamNameId(rs.getInt("exam_name_id"));
				exam.setExamName(rs.getString("exam_name"));
				exam.setExamDate(rs.getDate("exam_date"));
				exam.setScore(rs.getInt("score"));
				exam.setDeviationValue(rs.getDouble("deviation_value"));
				exam.setAverageScore(rs.getDouble("average_score"));
				list.add(exam);
			}
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
				// System.out.println("教科別学習データ更新完了");
				return true;

			} catch (Exception e) {
				conn.rollback();
				// System.out.println("教科別学習データ更新エラー: " + e.getMessage());
				e.printStackTrace();
				return false;
			}

		} catch (SQLException e) {
			// System.out.println("DB接続エラー: " + e.getMessage());
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
				// System.out.println("模試結果削除: " + result + "件");
				return result > 0;
			}

		} catch (SQLException e) {
			// System.out.println("模試結果削除エラー: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 模試結果を更新（模試名・実施日も含む）
	 */
	public static boolean updateExamResult(int examScoreId, String examName, String examDate, int score,
			double deviationValue, double averageScore) {
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			conn.setAutoCommit(false);

			try {
				// 1. exam_names を更新/作成
				String examNameSql = """
						INSERT INTO exam_names (exam_name, created_at, updated_at)
						VALUES (?, NOW(), NOW())
						ON DUPLICATE KEY UPDATE id = LAST_INSERT_ID(id)
						""";
				int examNameId;
				try (PreparedStatement ps = conn.prepareStatement(examNameSql,
						PreparedStatement.RETURN_GENERATED_KEYS)) {
					ps.setString(1, examName);
					ps.executeUpdate();
					try (ResultSet rs = ps.getGeneratedKeys()) {
						rs.next();
						examNameId = rs.getInt(1);
					}
				}

				// 2. exams を更新
				String examSql = """
						UPDATE exams ex
						JOIN exam_scores es ON ex.id = es.exam_id
						SET ex.exam_name_id = ?, ex.exam_date = ?, ex.updated_at = NOW()
						WHERE es.id = ?
						""";
				try (PreparedStatement ps = conn.prepareStatement(examSql)) {
					ps.setInt(1, examNameId);
					ps.setString(2, examDate);
					ps.setInt(3, examScoreId);
					ps.executeUpdate();
				}

				// 3. exam_scores を更新
				String scoreSql = """
						UPDATE exam_scores
						SET score = ?, deviation_value = ?, average_score = ?, updated_at = NOW()
						WHERE id = ?
						""";
				try (PreparedStatement ps = conn.prepareStatement(scoreSql)) {
					ps.setInt(1, score);
					ps.setDouble(2, deviationValue);
					ps.setDouble(3, averageScore);
					ps.setInt(4, examScoreId);
					ps.executeUpdate();
				}

				conn.commit();
				// System.out.println("模試結果更新完了");
				return true;

			} catch (Exception e) {
				conn.rollback();
				// System.out.println("模試結果更新エラー: " + e.getMessage());
				e.printStackTrace();
				return false;
			}

		} catch (SQLException e) {
			// System.out.println("DB接続エラー: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 理解度のみ更新
	 */
	public static boolean updateUnderstandingOnly(int studentId, int subjectId, String understanding) {
//		System.out.println("=== 理解度更新デバッグ ===");
//		System.out.println("studentId: " + studentId);
//		System.out.println("subjectId: " + subjectId);
//		System.out.println("understanding: " + understanding);

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			// 入力値チェック
			if (understanding == null || understanding.trim().isEmpty()) {
				// System.out.println("ERROR: 理解度が空です");
				return false;
			}

			int levelValue;
			try {
				levelValue = Integer.parseInt(understanding.trim());
			} catch (NumberFormatException e) {
				// System.out.println("ERROR: 理解度が数値ではありません: " + understanding);
				return false;
			}

			// まず既存データをチェック
			String checkSql = "SELECT id FROM levels WHERE student_id = ? AND subject_id = ?";
			boolean exists = false;

			try (PreparedStatement checkPs = conn.prepareStatement(checkSql)) {
				checkPs.setInt(1, studentId);
				checkPs.setInt(2, subjectId);
				ResultSet rs = checkPs.executeQuery();
				exists = rs.next();
				// System.out.println("既存データ: " + (exists ? "あり" : "なし"));
			}

			String sql;
			if (exists) {
				// 更新
				sql = "UPDATE levels SET level = ?, updated_at = NOW() WHERE student_id = ? AND subject_id = ?";
			} else {
				// 新規挿入
				sql = "INSERT INTO levels (level, student_id, subject_id, created_at, updated_at) VALUES (?, ?, ?, NOW(), NOW())";
			}

			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setInt(1, levelValue);
				ps.setInt(2, studentId);
				ps.setInt(3, subjectId);

				int result = ps.executeUpdate();
//				System.out.println((exists ? "更新" : "挿入") + "結果: " + result + "件");
//				System.out.println("========================");
				return result > 0;
			}

		} catch (Exception e) {
			// System.out.println("理解度更新エラー: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
}
package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ScheduleServlet")
public class ScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ScheduleServlet() {
		super();
	}

	// GETメソッド（画面表示）
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// もしもログインしていなかったらログインサーブレットにリダイレクトする
		HttpSession session = request.getSession();
		if (session.getAttribute("id") == null) {
			response.sendRedirect(request.getContextPath() + "/LoginServlet");
			return;
		}

		String studentIdStr = request.getParameter("studentId");
		String subjectIdStr = request.getParameter("subjectId");
		String textIdStr = request.getParameter("textId"); // ← 追加

		if (studentIdStr == null || subjectIdStr == null) {
			request.setAttribute("error", "生徒または科目が指定されていません。");
			request.getRequestDispatcher("/WEB-INF/jsp/Schedule.jsp").forward(request, response);
			return;
		}

		try {
			int studentId = Integer.parseInt(studentIdStr);
			int subjectId = Integer.parseInt(subjectIdStr);
			request.setAttribute("studentId", studentId);
			request.setAttribute("subjectId", subjectId);

			if (textIdStr != null && !textIdStr.isEmpty()) {
				try {
					int textId = Integer.parseInt(textIdStr);
					request.setAttribute("textId", textId); // ← textIdをJSPに渡す
				} catch (NumberFormatException e) {
					// textIdが無効なときのログ（あってもなくても可）
					// System.err.println("無効な textId が渡されました：" + textIdStr);
				}
			}

			request.getRequestDispatcher("/WEB-INF/jsp/Schedule.jsp").forward(request, response);

		} catch (NumberFormatException e) {
			request.setAttribute("error", "不正なIDが指定されました。");
			request.getRequestDispatcher("/WEB-INF/jsp/Schedule.jsp").forward(request, response);
		}
	}

	// ScheduleServlet.java の doPost メソッドを修正

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		if (session.getAttribute("id") == null) {
			response.sendRedirect(request.getContextPath() + "/LoginServlet");
			return;
		}

		try {
			int studentId = Integer.parseInt(request.getParameter("studentId"));
			int subjectId = Integer.parseInt(request.getParameter("subjectId"));
//			String textIdStr = request.getParameter("textId");
			String calculatedPageStr = request.getParameter("calculated_page");

//			System.out.println("=== ScheduleServlet POST デバッグ ===");
//			System.out.println("studentId: " + studentId);
//			System.out.println("subjectId: " + subjectId);
//			System.out.println("textIdStr: " + textIdStr);
//			System.out.println("calculatedPageStr: " + calculatedPageStr);
//			System.out.println("=====================================");

			if (calculatedPageStr == null || calculatedPageStr.isEmpty()) {
				request.setAttribute("error", "ページ数が計算されていません。");
				request.getRequestDispatcher("/WEB-INF/jsp/Schedule.jsp").forward(request, response);
				return;
			}

			int pages = Integer.parseInt(calculatedPageStr);

			// ★ 修正：IndividualResultsDAOのupdateScheduleメソッドを使用
			// 既存のスケジュールのtext_idを取得
			int existingTextId = getExistingTextId(studentId, subjectId);

			if (existingTextId > 0) {
				// 既存のスケジュールが存在する場合、text_idを保持してpagesのみ更新
				boolean success = dao.IndividualResultsDAO.updateSchedule(studentId, subjectId, existingTextId, pages);
//				System.out.println("既存スケジュール更新結果: " + success);

				if (success) {
					response.sendRedirect(request.getContextPath() + "/SubjectResultServlet?studentId=" + studentId
							+ "&subjectId=" + subjectId);
				} else {
					request.setAttribute("error", "スケジュールの更新に失敗しました。");
					request.getRequestDispatcher("/WEB-INF/jsp/Schedule.jsp").forward(request, response);
				}
			} else {
				// 既存のスケジュールが存在しない場合はエラー
				request.setAttribute("error", "先にテキストを選出してください。");
				request.getRequestDispatcher("/WEB-INF/jsp/Schedule.jsp").forward(request, response);
			}

		} catch (NumberFormatException e) {
			e.printStackTrace();
			request.setAttribute("error", "数字の形式が正しくありません。");
			request.getRequestDispatcher("/WEB-INF/jsp/Schedule.jsp").forward(request, response);
		}
	}

	// ★ 新規追加：既存のtext_idを取得するメソッド
	private int getExistingTextId(int studentId, int subjectId) {
		try (Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/b2?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Tokyo", "root",
				"password")) {

			String sql = "SELECT text_id FROM schedules WHERE student_id = ? AND subject_id = ?";
			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setInt(1, studentId);
				ps.setInt(2, subjectId);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					return rs.getInt("text_id");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0; // 見つからない場合は0を返す
	}
}

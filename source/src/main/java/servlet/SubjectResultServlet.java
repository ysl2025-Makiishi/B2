package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.IndividualResultsDAO;
import dao.SubjectResultDAO;
import dto.IndividualResults;
import dto.SubjectData;

@WebServlet("/SubjectResultServlet")
public class SubjectResultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ★デバッグ用出力
//		System.out.println("=== SubjectResultServlet GET 開始 ===");

		// ★セッション状態をデバッグ出力
//		System.out.println("セッションID: " + session.getId());
//		System.out.println("セッションの'id'属性: [" + session.getAttribute("id") + "]");
//		System.out.println("セッションの'loginUser'属性: [" + session.getAttribute("loginUser") + "]");
//		System.out.println("セッションが新規作成?: " + session.isNew());

		// もしもログインしていなかったらログインサーブレットにリダイレクトする
		HttpSession session = request.getSession();
		if (session.getAttribute("id") == null) {
			response.sendRedirect(request.getContextPath() + "/LoginServlet");
			return;
		}

//		System.out.println("ログイン確認OK。処理を継続します。");

		String studentIdStr = request.getParameter("studentId");
		String subjectIdStr = request.getParameter("subjectId");

		if (studentIdStr == null || !studentIdStr.matches("\\d+") || subjectIdStr == null
				|| !subjectIdStr.matches("\\d+")) {
			response.sendRedirect("SearchResultServlet");
			return;
		}

		int studentId = Integer.parseInt(studentIdStr);
		int subjectId = Integer.parseInt(subjectIdStr);

		// 生徒情報を取得
		IndividualResults student = IndividualResultsDAO.getStudentInfo(studentId);
		if (student == null) {
			response.sendRedirect("SearchResultServlet");
			return;
		}

		// 教科別データを取得
		SubjectData subjectData = SubjectResultDAO.getSubjectData(studentId, subjectId);

		// 教科名を取得
		String subjectName = getSubjectName(subjectId);

		request.setAttribute("studentId", studentId);
		request.setAttribute("subjectId", subjectId);
		request.setAttribute("subject", subjectName);
		request.setAttribute("student", student);
		request.setAttribute("subjectData", subjectData);

		request.getRequestDispatcher("/WEB-INF/jsp/SubjectResult.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ★デバッグ用出力
//		System.out.println("=== SubjectResultServlet POST 開始 ===");

		// ★セッション状態をデバッグ出力
//		System.out.println("POSTでのセッションID: " + session.getId());
//		System.out.println("POSTでのセッションの'id'属性: [" + session.getAttribute("id") + "]");

		// もしもログインしていなかったらログインサーブレットにリダイレクトする
		HttpSession session = request.getSession();
		if (session.getAttribute("id") == null) {
			response.sendRedirect(request.getContextPath() + "/LoginServlet");
			return;
		}

		request.setCharacterEncoding("UTF-8");

		String action = request.getParameter("action");

		if ("update".equals(action)) {
			updateSubjectData(request, response);
		} else if ("updateUnderstanding".equals(action)) {
			updateUnderstanding(request, response);
		} else if ("deleteExam".equals(action)) {
			deleteExamResult(request, response);
		} else if ("updateExam".equals(action)) {
			updateExamResult(request, response);
		} else {
			doGet(request, response);
		}
	}

	// 以下のメソッドは元のまま...
	private void updateSubjectData(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int studentId = Integer.parseInt(request.getParameter("studentId"));
		int subjectId = Integer.parseInt(request.getParameter("subjectId"));

		String understanding = request.getParameter("understanding");
		String textSelection = request.getParameter("text_selection");
		String schedule = request.getParameter("schedule");
		String homeworkPages = request.getParameter("homework_pages");
		String lastContent = request.getParameter("lastContent");
		String nextContent = request.getParameter("nextContent");
		String homework = request.getParameter("homework");
		String note = request.getParameter("note");

		boolean success = SubjectResultDAO.updateSubjectData(studentId, subjectId, understanding, textSelection,
				schedule, homeworkPages, lastContent, nextContent, homework, note);

		String message = success ? "データが正常に保存されました" : "データの保存に失敗しました";
		request.setAttribute("message", message);

		response.sendRedirect("SubjectResultServlet?studentId=" + studentId + "&subjectId=" + subjectId);
	}

	private void deleteExamResult(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int examId = Integer.parseInt(request.getParameter("examId"));
		int studentId = Integer.parseInt(request.getParameter("studentId"));
		int subjectId = Integer.parseInt(request.getParameter("subjectId"));

		boolean success = SubjectResultDAO.deleteExamResult(examId);

		String message = success ? "模試結果を削除しました" : "模試結果の削除に失敗しました";
		request.setAttribute("message", message);

		response.sendRedirect("SubjectResultServlet?studentId=" + studentId + "&subjectId=" + subjectId);
	}

	private void updateExamResult(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int examId = Integer.parseInt(request.getParameter("examId"));
		String examName = request.getParameter("examName");
		String examDate = request.getParameter("examDate");
		int score = Integer.parseInt(request.getParameter("score"));
		double deviationValue = Double.parseDouble(request.getParameter("deviationValue"));
		double averageScore = Double.parseDouble(request.getParameter("averageScore"));
		int studentId = Integer.parseInt(request.getParameter("studentId"));
		int subjectId = Integer.parseInt(request.getParameter("subjectId"));

		boolean success = SubjectResultDAO.updateExamResult(examId, examName, examDate, score, deviationValue,
				averageScore);

		String message = success ? "模試結果を更新しました" : "模試結果の更新に失敗しました";
		request.setAttribute("message", message);

		response.sendRedirect("SubjectResultServlet?studentId=" + studentId + "&subjectId=" + subjectId);
	}

	private String getSubjectName(int subjectId) {
		switch (subjectId) {
		case 1:
			return "国語";
		case 2:
			return "数学";
		case 3:
			return "英語";
		case 4:
			return "理科";
		case 5:
			return "社会";
		case 6:
			return "保健体育";
		case 7:
			return "技術家庭";
		case 8:
			return "美術";
		case 9:
			return "音楽";
		case 10:
			return "総合";
		default:
			return "不明";
		}
	}

	private void updateUnderstanding(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String studentIdStr = request.getParameter("studentId");
		String subjectIdStr = request.getParameter("subjectId");
		String understanding = request.getParameter("understanding");

		if (studentIdStr == null || subjectIdStr == null) {
			response.sendRedirect("SearchResultServlet");
			return;
		}

		int studentId = Integer.parseInt(studentIdStr);
		int subjectId = Integer.parseInt(subjectIdStr);

		SubjectResultDAO.updateUnderstandingOnly(studentId, subjectId, understanding);

		response.sendRedirect("SubjectResultServlet?studentId=" + studentId + "&subjectId=" + subjectId);
	}
}
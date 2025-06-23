package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

//		System.out.println("=== SubjectResult GET リクエスト ===");
//		System.out.println("クエリ文字列: " + request.getQueryString());

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

		request.setCharacterEncoding("UTF-8");

		String action = request.getParameter("action");

		if ("update".equals(action)) {
			updateSubjectData(request, response);
		} else if ("updateUnderstanding".equals(action)) {
			updateUnderstanding(request, response); // ← 追加
		} else if ("deleteExam".equals(action)) {
			deleteExamResult(request, response);
		} else if ("updateExam".equals(action)) {
			updateExamResult(request, response);
		} else {
			doGet(request, response);
		}
	}

	/**
	 * 教科別学習データの更新
	 */
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

		// 更新後のデータを再取得して表示
		response.sendRedirect("SubjectResultServlet?studentId=" + studentId + "&subjectId=" + subjectId);
	}

	/**
	 * 模試結果の削除
	 */
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

	/**
	 * 模試結果の更新
	 */
	/**
	 * 模試結果の更新（模試名・実施日も含む）
	 */
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
			return "数学"; // ← 修正
		case 3:
			return "英語"; // ← 修正
		case 4:
			return "理科";
		case 5:
			return "社会"; // ← 修正
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

	/**
	 * 理解度のみ更新
	 */
	/**
	 * 理解度のみ更新
	 */
	private void updateUnderstanding(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

//		System.out.println("=== updateUnderstanding 開始 ===");

		String studentIdStr = request.getParameter("studentId");
		String subjectIdStr = request.getParameter("subjectId");
		String understanding = request.getParameter("understanding");

//		System.out.println("受け取ったパラメータ:");
//		System.out.println("studentId: " + studentIdStr);
//		System.out.println("subjectId: " + subjectIdStr);
//		System.out.println("understanding: " + understanding);

		if (studentIdStr == null || subjectIdStr == null) {
//			System.out.println("ERROR: 必要なパラメータが不足");
			response.sendRedirect("SearchResultServlet");
			return;
		}

		int studentId = Integer.parseInt(studentIdStr);
		int subjectId = Integer.parseInt(subjectIdStr);

//		boolean success = SubjectResultDAO.updateUnderstandingOnly(studentId, subjectId, understanding);
		SubjectResultDAO.updateUnderstandingOnly(studentId, subjectId, understanding);

//		String message = success ? "理解度を更新しました" : "理解度の更新に失敗しました";
//		System.out.println("更新結果: " + message);

		response.sendRedirect("SubjectResultServlet?studentId=" + studentId + "&subjectId=" + subjectId);
	}
}
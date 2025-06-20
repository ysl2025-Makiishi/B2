package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.IndividualResultsDAO;
import dto.IndividualResults;

@WebServlet("/SubjectResultServlet")
public class SubjectResultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// === デバッグ情報 ===
		System.out.println("=== SubjectResult GET リクエスト ===");
		System.out.println("リクエストURL: " + request.getRequestURL());
		System.out.println("クエリ文字列: " + request.getQueryString());

		// すべてのパラメータを表示
		System.out.println("=== すべてのパラメータ ===");
		request.getParameterMap().forEach((key, values) -> {
			System.out.println(key + " = " + String.join(", ", values));
		});
		System.out.println("========================");

		// パラメータを取得
		String studentIdStr = request.getParameter("studentId");
		String subjectIdStr = request.getParameter("subjectId");

		System.out.println("受け取った studentId = " + studentIdStr);
		System.out.println("受け取った subjectId = " + subjectIdStr);

		// バリデーション
		if (studentIdStr == null || !studentIdStr.matches("\\d+") || subjectIdStr == null
				|| !subjectIdStr.matches("\\d+")) {
			System.out.println("パラメータが不正です");
			response.sendRedirect("SearchResultServlet");
			return;
		}

		int studentId = Integer.parseInt(studentIdStr);
		int subjectId = Integer.parseInt(subjectIdStr);

		// 教科名を取得（表示用）
		String subjectName = getSubjectName(subjectId);
		System.out.println("教科名: " + subjectName);

		// 生徒情報を取得（ナビゲーションで使用するため）
		IndividualResults student = IndividualResultsDAO.getStudentInfo(studentId);

		if (student == null) {
			System.out.println("生徒情報が見つかりません");
			response.sendRedirect("SearchResultServlet");
			return;
		}

		System.out.println("生徒情報取得成功: " + student.getName());

		// TODO: ここで教科別データを取得
		// SubjectData subjectData = SubjectResultDAO.getSubjectData(studentId,
		// subjectId);

		request.setAttribute("studentId", studentId);
		request.setAttribute("subjectId", subjectId);
		request.setAttribute("subject", subjectName);
		request.setAttribute("student", student); // ← これが重要！

		System.out.println("SubjectResult.jspにフォワード");
		request.getRequestDispatcher("/WEB-INF/jsp/SubjectResult.jsp").forward(request, response);
	}

	// 教科IDから教科名を取得
	private String getSubjectName(int subjectId) {
		switch (subjectId) {
		case 1:
			return "国語";
		case 2:
			return "社会";
		case 3:
			return "数学";
		case 4:
			return "理科";
		case 5:
			return "英語";
		case 10:
			return "総合";
		default:
			return "不明";
		}
	}
}
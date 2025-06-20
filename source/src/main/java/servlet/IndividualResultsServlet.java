package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.IndividualResultsDAO;
import dto.IndividualResults;

@WebServlet("/IndividualResultsServlet")
public class IndividualResultsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// === デバッグ情報 ===
		System.out.println("=== GET リクエスト デバッグ情報 ===");
		System.out.println("リクエストURL: " + request.getRequestURL());
		System.out.println("クエリ文字列: " + request.getQueryString());
		System.out.println("リファラー: " + request.getHeader("Referer"));

		// すべてのパラメータを表示
		System.out.println("=== すべてのパラメータ ===");
		request.getParameterMap().forEach((key, values) -> {
			System.out.println(key + " = " + String.join(", ", values));
		});
		System.out.println("========================");

		// studentId という名前で受け取るよう修正
		String idStr = request.getParameter("studentId"); // ← ここを修正！
		System.out.println("GET：受け取った studentId = " + idStr);
		if (idStr == null || !idStr.matches("\\d+")) {
			System.out.println("IDが不正なのでリダイレクトします");
			response.sendRedirect("SearchResultServlet");
			return;
		}
		int studentId = Integer.parseInt(idStr);
		IndividualResults student = IndividualResultsDAO.getStudentInfo(studentId);
		if (student == null) {
			System.out.println("該当生徒が見つかりません");
			response.sendRedirect("SearchResultServlet");
			return;
		}
		request.setAttribute("student", student);
		request.getRequestDispatcher("/WEB-INF/jsp/IndividualResults.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		// リクエストパラメータからIDを取得
		String idStr = request.getParameter("id");
		System.out.println("POST：受け取った id = " + idStr);

		if (idStr == null || !idStr.matches("\\d+")) {
			System.out.println("POST：IDが不正です");
			response.sendRedirect("SearchResultServlet");
			return;
		}

		int studentId = Integer.parseInt(idStr);

		// フォームデータを取得
		String name = request.getParameter("name");
		String furigana = request.getParameter("furigana");
		String gender = request.getParameter("gender");
		String birthday = request.getParameter("birthday");
		String school = request.getParameter("school"); // ← 追加
		String asp1 = request.getParameter("asp1"); // ← 追加
		String asp2 = request.getParameter("asp2"); // ← 追加
		String asp3 = request.getParameter("asp3"); // ← 追加

		// GPA データを取得
		String gpaJp = request.getParameter("gpa_jp");
		String gpaSs = request.getParameter("gpa_ss");
		String gpaMa = request.getParameter("gpa_ma");
		String gpaSc = request.getParameter("gpa_sc");
		String gpaEn = request.getParameter("gpa_en");
		String gpaMu = request.getParameter("gpa_mu");
		String gpaAr = request.getParameter("gpa_ar");
		String gpaPe = request.getParameter("gpa_pe");
		String gpaTe = request.getParameter("gpa_te");
		// === GPAデバッグログ追加 ===
		System.out.println("=== GPA更新デバッグ ===");
		System.out.println("gpaJp = " + gpaJp);
		System.out.println("gpaSs = " + gpaSs);
		System.out.println("gpaMa = " + gpaMa);
		System.out.println("gpaSc = " + gpaSc);
		System.out.println("gpaEn = " + gpaEn);
		System.out.println("gpaMu = " + gpaMu);
		System.out.println("gpaAr = " + gpaAr);
		System.out.println("gpaPe = " + gpaPe);
		System.out.println("gpaTe = " + gpaTe);
		System.out.println("==================");

		// 模試データを取得（配列）
		String[] examNames = request.getParameterValues("exam_name[]");
		String[] examDates = request.getParameterValues("exam_date[]");
		String[] examSubjects = request.getParameterValues("exam_subject[]");
		String[] examScores = request.getParameterValues("exam_score[]");
		String[] examDevs = request.getParameterValues("exam_dev[]");
		String[] examAvgs = request.getParameterValues("exam_avg[]");

		System.out.println("POST：フォームデータ取得完了");

		boolean success = true;
		String message = "";

		// 1. 基本情報の更新（学校名・志望校も含む）← 変更
		if (!IndividualResultsDAO.updateStudentBasicInfo(studentId, name, furigana, gender, birthday, school, asp1,
				asp2, asp3)) {
			success = false;
			message += "基本情報の更新に失敗しました。";
		}

		// 2. GPAの更新
		if (!IndividualResultsDAO.updateGPA(studentId, gpaJp, gpaSs, gpaMa, gpaSc, gpaEn, gpaMu, gpaAr, gpaPe, gpaTe)) {
			success = false;
			message += "GPAの更新に失敗しました。";
		}

		// 3. 模試結果の登録（新規データがある場合のみ）
		if (examNames != null && examNames.length > 0) {
			if (!IndividualResultsDAO.insertExamResults(studentId, examNames, examDates, examSubjects, examScores,
					examDevs, examAvgs)) {
				success = false;
				message += "模試結果の登録に失敗しました。";
			}
		}

		// 更新後、最新データを再取得
		IndividualResults student = IndividualResultsDAO.getStudentInfo(studentId);

		if (student == null) {
			System.out.println("POST：生徒情報の再取得に失敗");
			response.sendRedirect("SearchResultServlet");
			return;
		}

		// 結果メッセージを設定
		if (success) {
			message = "データが正常に保存されました";
		}

		request.setAttribute("message", message);
		request.setAttribute("student", student);
		request.getRequestDispatcher("/WEB-INF/jsp/IndividualResults.jsp").forward(request, response);
	}
}
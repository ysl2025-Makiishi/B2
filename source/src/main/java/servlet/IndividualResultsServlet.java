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
		System.out.println("サーブレット：受け取ったID = " + idStr); // 🔴ログ①
		if (idStr == null || !idStr.matches("\\d+")) {
			// IDが無効なら検索結果ページに戻す
			System.out.println("サーブレット：IDが不正です");
			response.sendRedirect("SearchResultServlet");
			return;
		}
		int studentId = Integer.parseInt(idStr);
		System.out.println("受け取ったID: " + studentId); // ★ログ①
		IndividualResults student = IndividualResultsDAO.getStudentInfo(studentId);
		if (student == null) {
			System.out.println("サーブレット：生徒がnullです"); // 🔴ログ④
			response.sendRedirect("SearchResultServlet");
			return;
		}
		request.setAttribute("student", student);
		request.getRequestDispatcher("/WEB-INF/jsp/IndividualResults.jsp").forward(request, response);
	}
}
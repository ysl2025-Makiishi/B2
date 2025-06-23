package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.StudentListDAO;
import dto.students;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/StudentListServlet")
public class StudentListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// GETメソッド
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	// POSTメソッド
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	// GET/POST共通処理
	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		// POSTでidを受け取った場合だけ取得、なければ0
		int id = 0;
		String idStr = request.getParameter("id");
		if (idStr != null && idStr.matches("\\d+")) {
			id = Integer.parseInt(idStr);
		}

		// 生徒一覧をDAOから取得
		StudentListDAO dao = new StudentListDAO();
		List<students> studentList = dao.findAll();

		// リクエストスコープにセット
		request.setAttribute("studentList", studentList);
		request.setAttribute("studentid", id);

		// JSPにフォワード
		request.getRequestDispatcher("/WEB-INF/jsp/StudentList.jsp").forward(request, response);
	}
}	
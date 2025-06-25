package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	// servlet/StudentListServlet.java

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    request.setCharacterEncoding("UTF-8");
	    
	    HttpSession session = request.getSession();
		if (session.getAttribute("id") == null) {
			response.sendRedirect("LoginServlet");
			return;
		}

	    int id = 0;
	    String idStr = request.getParameter("id");
	    if (idStr != null && idStr.matches("\\d+")) {
	        id = Integer.parseInt(idStr);
	    }

	    // ▼ 並び替えパラメータを取得
	    String sort = request.getParameter("sort");

	    // ▼ DAOにソート条件を渡して取得
	    StudentListDAO dao = new StudentListDAO();
	    List<students> studentList = dao.findAll(sort);  // ← 修正箇所！

	    request.setAttribute("studentList", studentList);
	    request.setAttribute("studentid", id);
	    request.setAttribute("sort", sort);

	    request.getRequestDispatcher("/WEB-INF/jsp/StudentList.jsp").forward(request, response);
	}


}	
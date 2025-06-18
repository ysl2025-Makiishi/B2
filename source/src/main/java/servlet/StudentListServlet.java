package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	// GETメソッドで一覧を表示
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		// もしもログインしていなかったらログインサーブレットにリダイレクトする
		/*HttpSession session = request.getSession();
		if (session.getAttribute("id") == null) {
			response.sendRedirect("/webapp/LoginServlet");
			return;
		}*/

		// 遷移元メッセージ取得
        //String message = request.getParameter("message");
        //if (message != null && !message.isEmpty()) {
        //    request.setAttribute("message", message);
        //}
        
     // 生徒一覧を取得
        StudentListDAO dao = new StudentListDAO();
		List<students> studentList = dao.findAll();
		
		request.setAttribute("studentList", studentList);


        // 一覧ページにフォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/StudentList.jsp");
        dispatcher.forward(request, response);
    }
}
		
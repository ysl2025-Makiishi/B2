package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import dto.LoginUser;
import dto.Result;
import dto.users;

/**
 * ログイン画面表示＆認証 Servlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/* ---------- GET：ログイン画面へ ---------- */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
		dispatcher.forward(request, response);
	}

	/* ---------- POST：認証処理 ---------- */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		// フォーム name="id", name="pw"
		String userId = request.getParameter("id");
		String password = request.getParameter("pw");

		// DTO に詰める（id と password だけ使う）
		users dto = new users();
		dto.setUser_id(userId);
		dto.setPassword(password);

		// 認証
		UserDAO dao = new UserDAO();
		boolean ok = dao.isLoginOK(dto);

		if (ok) { // ===== ログイン成功 =====
			HttpSession session = request.getSession();
			session.setAttribute("id", new LoginUser(userId));
			response.sendRedirect("HomeServlet");

		} else { // ===== ログイン失敗 =====
			request.setAttribute("result", new Result("ログイン失敗！", "ID またはパスワードが間違っています。", "/webapp/LoginServlet"));

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
			dispatcher.forward(request, response);
		}
	}
}

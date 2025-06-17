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
import dto.IdPw; // ← 送信された ID／PW をまとめる DTO
import dto.LoginUser; // ← セッション用に ID だけ保持する簡易 DTO
import dto.Result;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/* ========================= GET：ログイン画面表示 ========================= */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
		rd.forward(request, response);
	}

	/* ========================= POST：認証処理 ========================= */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		// フォーム name="id" と name="pw" を取得（前後空白を除去）
		String userId = request.getParameter("id");
		String password = request.getParameter("pw");
		if (userId != null) {
			userId = userId.trim();
		}
		if (password != null) {
			password = password.trim();
		}

		/* ---------- DAO で認証判定 ---------- */
		UserDAO dao = new UserDAO();
		IdPw cred = new IdPw(userId, password); // ← DTO に詰める
		boolean loginOK = dao.isLoginOK(cred);

		if (loginOK) { // ===== 認証成功 =====
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", new LoginUser(userId));

			// ホーム画面へリダイレクト
			response.sendRedirect(request.getContextPath() + "/HomeServlet");

		} else { // ===== 認証失敗 =====
			Result result = new Result("ログイン失敗！", "ID またはパスワードが間違っています。", request.getContextPath() + "/LoginServlet");
			request.setAttribute("result", result);

			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
			rd.forward(request, response);
		}
	}
}

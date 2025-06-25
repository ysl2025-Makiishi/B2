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
import dto.IdPw;
import dto.LoginUser;
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

		// ★デバッグ用出力
		System.out.println("=== ログイン処理開始 ===");
		System.out.println("受信したuserIdの生値: [" + userId + "]");
		System.out.println("受信したpasswordの生値: [" + password + "]");

		if (userId != null) {
			userId = userId.trim();
		}
		if (password != null) {
			password = password.trim();
		}

		// ★デバッグ用出力
		System.out.println("トリム後のuserId: [" + userId + "]");
		System.out.println("トリム後のpassword: [" + password + "]");

		// ★空文字やnullチェックを追加
		if (userId == null || userId.isEmpty() || password == null || password.isEmpty()) {
			System.out.println("ERROR: userIdまたはpasswordが空です");
			Result result = new Result("ログイン失敗！", "ID またはパスワードを入力してください。", request.getContextPath() + "/LoginServlet");
			request.setAttribute("result", result);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
			rd.forward(request, response);
			return;
		}

		/* ---------- DAO で認証判定 ---------- */
		UserDAO dao = new UserDAO();
		IdPw cred = new IdPw(userId, password);
		boolean loginOK = dao.isLoginOK(cred);

		System.out.println("認証結果: " + loginOK);

		if (loginOK) { // ===== 認証成功 =====
			HttpSession session = request.getSession();

			// ★セッションに設定する前にデバッグ出力
			System.out.println("セッションに設定するuserId: [" + userId + "]");

			session.setAttribute("loginUser", new LoginUser(userId));
			session.setAttribute("id", userId);

			// ★設定後の確認
			System.out.println("セッションに設定されたid: [" + session.getAttribute("id") + "]");
			System.out.println("セッションに設定されたloginUser: [" + session.getAttribute("loginUser") + "]");

			// ホーム画面へリダイレクト
			response.sendRedirect(request.getContextPath() + "/HomeServlet");
		} else { // ===== 認証失敗 =====
			System.out.println("認証失敗: ID またはパスワードが間違っています");
			Result result = new Result("ログイン失敗！", "ID またはパスワードが間違っています。", request.getContextPath() + "/LoginServlet");
			request.setAttribute("result", result);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
			rd.forward(request, response);
		}
	}
}
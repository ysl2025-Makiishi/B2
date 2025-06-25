package servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // GET：検索フォームを表示
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // ログインチェック
        HttpSession session = request.getSession();
		if (session.getAttribute("id") == null) {
			response.sendRedirect("LoginServlet");
			return;
		}

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/search.jsp");
        dispatcher.forward(request, response);
    }

    // POST：検索処理を実行しリダイレクト
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // ログインチェック
        HttpSession session = request.getSession();
		if (session.getAttribute("id") == null) {
			response.sendRedirect("LoginServlet");
			return;
		}
        // パラメータ取得（null対策済み）
        String name = getSafeParam(request.getParameter("name"));
        String furigana = getSafeParam(request.getParameter("furigana"));
        String schoolName = getSafeParam(request.getParameter("schoolName"));

        try {
            // URLエンコードしてリダイレクト
            String redirectUrl = "SearchResultServlet?name=" + URLEncoder.encode(name, StandardCharsets.UTF_8)
                    + "&furigana=" + URLEncoder.encode(furigana, StandardCharsets.UTF_8)
                    + "&schoolName=" + URLEncoder.encode(schoolName, StandardCharsets.UTF_8);

            response.sendRedirect(redirectUrl);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "検索結果の遷移中にエラーが発生しました。");
            request.getRequestDispatcher("/WEB-INF/jsp/search.jsp").forward(request, response);
        }
    }

    // null → 空文字にするヘルパー
    private String getSafeParam(String param) {
        return param != null ? param : "";
    }
}

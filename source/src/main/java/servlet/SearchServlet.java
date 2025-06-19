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

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // GETリクエスト：検索フォームを表示
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/search.jsp");
        dispatcher.forward(request, response);
    }

    // POSTリクエスト：検索実行 → 検索結果画面にリダイレクト
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // パラメータ取得（null対策付き）
        String name = getSafeParam(request.getParameter("name"));
        String furigana = getSafeParam(request.getParameter("furigana"));
        String school = getSafeParam(request.getParameter("school"));
        
        /*String name = request.getParameter("name");
        String furigana = request.getParameter("furigana");
        String school = request.getParameter("school");*/
        
     // nullチェックして空文字にする
        name = name == null ? "" : name;
        furigana = furigana == null ? "" : furigana;
        school = school == null ? "" : school;


        try {
            // URLエンコードして検索結果画面にリダイレクト
            String redirectUrl = "SearchResultServlet?name=" + URLEncoder.encode(name, StandardCharsets.UTF_8)
                    + "&furigana=" + URLEncoder.encode(furigana, StandardCharsets.UTF_8)
                    + "&school=" + URLEncoder.encode(school, StandardCharsets.UTF_8);
            response.sendRedirect(redirectUrl);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "検索結果の遷移中にエラーが発生しました。");
            request.getRequestDispatcher("/WEB-INF/jsp/search.jsp").forward(request, response);
        }
    }

    // null を安全な空文字に変換するヘルパーメソッド
    private String getSafeParam(String param) {
        return param != null ? param : "";
    }
}

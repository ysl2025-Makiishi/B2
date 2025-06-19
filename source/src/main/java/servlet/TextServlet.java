package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TextDAO;
import dto.texts;

@WebServlet("/TextServlet")
public class TextServlet extends HttpServlet {

    // 初期表示（GET）時：選択リセット状態で表示
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("subject", ""); // ← 空文字に変更（JSPと合わせるため）
        request.setAttribute("personality", "");
        request.setAttribute("searched", false);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/Text.jsp");
        dispatcher.forward(request, response);
    }

    // 検索実行時（POST）：選択された値で検索し、選択状態を保持してJSPへ
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String subStr = request.getParameter("subject");
        String perStr = request.getParameter("personality");

        try {
            int subjectId = Integer.parseInt(subStr);
            int personalityId = Integer.parseInt(perStr);

            System.out.println("選択された subjectId = " + subjectId);
            System.out.println("選択された personalityId = " + personalityId);

            TextDAO dao = new TextDAO();
            List<texts> textsList = dao.getTextsBySubjectAndPersonality(subjectId, personalityId);

            System.out.println("取得した教材数 = " + textsList.size());

            request.setAttribute("textsList", textsList);
            request.setAttribute("searched", true);
            request.setAttribute("subject", subjectId);       // 選択状態保持
            request.setAttribute("personality", personalityId);

        } catch (NumberFormatException e) {
            System.out.println("エラー：subject または personality が不正（未選択または数字でない）");
            request.setAttribute("textsList", null);
            request.setAttribute("searched", true);
            request.setAttribute("subject", "");              // 初期化
            request.setAttribute("personality", "");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/Text.jsp");
        dispatcher.forward(request, response);
    }
}

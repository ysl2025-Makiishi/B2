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

    private static final long serialVersionUID = 1L;

    // 初期表示（GET）
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // パラメータ取得
        String studentIdStr = request.getParameter("studentId");
        String subjectIdStr = request.getParameter("subjectId");

        int studentId = parseIntOrDefault(studentIdStr, 0);
        int subjectId = parseIntOrDefault(subjectIdStr, 0);

        // 初期値セット
        request.setAttribute("studentId", studentId);
        request.setAttribute("subjectId", subjectId);
        request.setAttribute("subject", "");
        request.setAttribute("personality", "");
        request.setAttribute("searched", false);

        // 表示
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/Text.jsp");
        dispatcher.forward(request, response);
    }

    // POST（検索 or 登録 or 更新）
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");


        // ▼ ここからログ出力追加 ▼
        String action = request.getParameter("action");
        String textIdStr = request.getParameter("textId");
        String studentIdStr = request.getParameter("studentId");
        String subjectIdStr = request.getParameter("subjectId");

        System.out.println("[TextServlet POST] action=" + action
            + ", textId=" + textIdStr
            + ", studentId=" + studentIdStr
            + ", subjectId=" + subjectIdStr);
        // ▲ ここまで追加 ▲
        int studentId = parseIntOrDefault(studentIdStr, 0);
        int subjectId = parseIntOrDefault(subjectIdStr, 0);

        String message = "";
        Integer selectedSubjectId = null;

        TextDAO textDAO = new TextDAO();

        // 登録処理
        if ("register".equals(action) && textIdStr != null && studentId > 0) {
            try {
                int textId = Integer.parseInt(textIdStr);
                texts selectedText = textDAO.findById(textId);

                if (selectedText != null) {
                    subjectId = selectedText.getSubjectId();
                    selectedSubjectId = subjectId;

                    boolean registered = textDAO.registerSelectedText(studentId, subjectId, textId);
                    message = registered ? "テキストを登録しました。" : "登録に失敗しました。";
                } else {
                    message = "指定されたテキストが見つかりません。";
                }
            } catch (NumberFormatException e) {
                message = "不正なテキストIDです。";
            }

            request.setAttribute("message", message);
            request.setAttribute("searched", false);
            request.setAttribute("subject", "");
            request.setAttribute("personality", "");
            request.setAttribute("studentId", studentId);
            request.setAttribute("subjectId", selectedSubjectId != null ? selectedSubjectId : "");

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/Text.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // 更新処理
        if ("update".equals(action) && textIdStr != null && studentId > 0) {
            try {
                int textId = Integer.parseInt(textIdStr);
                texts selectedText = textDAO.findById(textId);

                if (selectedText != null) {
                    subjectId = selectedText.getSubjectId();
                    selectedSubjectId = subjectId;

                    boolean updated = textDAO.registerSelectedText(studentId, subjectId, textId);
                    message = updated ? "テキストを更新しました。" : "更新に失敗しました。";
                } else {
                    message = "指定されたテキストが見つかりません。";
                }
            } catch (NumberFormatException e) {
                message = "不正なテキストIDです。";
            }

            request.setAttribute("message", message);
            request.setAttribute("searched", false);
            request.setAttribute("subject", "");
            request.setAttribute("personality", "");
            request.setAttribute("studentId", studentId);
            request.setAttribute("subjectId", selectedSubjectId != null ? selectedSubjectId : "");

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/Text.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // 検索処理
        String subStr = request.getParameter("subject");
        String perStr = request.getParameter("personality");

        int subject = parseIntOrDefault(subStr, 0);
        int personality = parseIntOrDefault(perStr, 0);

        if (subject > 0 && personality > 0) {
            List<texts> textsList = textDAO.getTextsBySubjectAndPersonality(subject, personality);
            request.setAttribute("textsList", textsList);
            request.setAttribute("searched", true);
            request.setAttribute("subject", subject);
            request.setAttribute("personality", personality);
            request.setAttribute("subjectId", subject);
        } else {
            request.setAttribute("textsList", null);
            request.setAttribute("searched", true);
            request.setAttribute("subject", "");
            request.setAttribute("personality", "");
            request.setAttribute("subjectId", "");
        }

        request.setAttribute("studentId", studentId);
        request.setAttribute("message", message);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/Text.jsp");
        dispatcher.forward(request, response);
    }

    // 数字変換補助メソッド
    private int parseIntOrDefault(String str, int defaultValue) {
        try {
            return (str != null) ? Integer.parseInt(str) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
} 

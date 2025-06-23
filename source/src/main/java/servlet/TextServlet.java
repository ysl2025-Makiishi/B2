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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // studentId を取得（なければ 0）
        String studentIdStr = request.getParameter("studentId");
        int studentId = 0;
        try {
            studentId = Integer.parseInt(studentIdStr);
        } catch (NumberFormatException | NullPointerException e) {
            studentId = 0;
        }

        // 初期表示に必要な属性
        request.setAttribute("subject", "");
        request.setAttribute("personality", "");
        request.setAttribute("searched", false);
        request.setAttribute("studentId", studentId);
        request.setAttribute("subjectId", "");

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/Text.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        String textIdStr = request.getParameter("textId");
        String studentIdStr = request.getParameter("studentId");

        int studentId = 0;
        String message = "";
        Integer selectedSubjectId = null;
        int subjectId = 0;

        try {
            studentId = Integer.parseInt(studentIdStr);
        } catch (NumberFormatException e) {
            studentId = 0;
        }

        // ▼ 登録処理
        if (action != null && textIdStr != null && studentId > 0) {
            try {
                int textId = Integer.parseInt(textIdStr);
                TextDAO textDAO = new TextDAO();
                texts selectedText = textDAO.findById(textId);

                if (selectedText == null) {
                    message = "指定されたテキストが見つかりません。";
                } else {
                    subjectId = selectedText.getSubjectId();
                    selectedSubjectId = subjectId;

                    boolean registered = textDAO.registerSelectedText(studentId, subjectId, textId);
                    if (!registered) {
                        message = "選出登録に失敗しました。";
                    }
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

        // ▼ 検索処理
        String subStr = request.getParameter("subject");
        String perStr = request.getParameter("personality");

        int personalityId = 0;
        boolean validInput = true;

        try {
            if (subStr != null && !subStr.isEmpty()) {
                subjectId = Integer.parseInt(subStr);
            } else {
                validInput = false;
            }

            if (perStr != null && !perStr.isEmpty()) {
                personalityId = Integer.parseInt(perStr);
            } else {
                validInput = false;
            }

            if (validInput) {
                TextDAO dao = new TextDAO();
                List<texts> textsList = dao.getTextsBySubjectAndPersonality(subjectId, personalityId);

                request.setAttribute("textsList", textsList);
                request.setAttribute("searched", true);
                request.setAttribute("subject", subjectId);
                request.setAttribute("personality", personalityId);
                request.setAttribute("subjectId", subjectId);
            } else {
                request.setAttribute("textsList", null);
                request.setAttribute("searched", true);
                request.setAttribute("subject", "");
                request.setAttribute("personality", "");
                request.setAttribute("subjectId", "");
            }

        } catch (NumberFormatException e) {
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
}

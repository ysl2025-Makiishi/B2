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

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/Text.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        int subjectId = Integer.parseInt(request.getParameter("subject"));
        int personalityId = Integer.parseInt(request.getParameter("personality"));

        TextDAO dao = new TextDAO();
        List<texts> textsList = dao.getTextsBySubjectAndPersonality(subjectId, personalityId);

        // ここに追加
        System.out.println("検索結果件数: " + textsList.size());

        request.setAttribute("textsList", textsList);
        request.setAttribute("searched", true);
        request.setAttribute("subject", subjectId);
        request.setAttribute("personality", personalityId);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/Text.jsp");
        dispatcher.forward(request, response);
    }
}
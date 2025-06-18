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

        String subject = request.getParameter("subject");
        String personality = request.getParameter("personality");

        int subjectId = 0;
        int personalityId = 0;

        if (subject != null && !subject.isEmpty()) {
            subjectId = Integer.parseInt(subject);
        }
        if (personality != null && !personality.isEmpty()) {
            personalityId = Integer.parseInt(personality);
        }

        System.out.println("subjectId = " + subjectId);
        System.out.println("personalityId = " + personalityId);

        List<texts> textsList = new TextDAO().findBySubjectAndPersonality(subjectId, personalityId);
        System.out.println("textsList.size() = " + textsList.size());

        request.setAttribute("textsList", textsList);
        request.setAttribute("searched", true);
        request.getRequestDispatcher("/WEB-INF/jsp/Text.jsp").forward(request, response);
}}
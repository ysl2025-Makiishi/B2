package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SearchDAO;          // ← 追加！
import dto.students; 
import java.net.URLEncoder;
// ← 追加！

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/search.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        String furigana = request.getParameter("furigana");
        String school = request.getParameter("school");

        String redirectUrl = "SearchResultServlet?name=" + URLEncoder.encode(name, "UTF-8")
                + "&furigana=" + URLEncoder.encode(furigana, "UTF-8")
                + "&school=" + URLEncoder.encode(school, "UTF-8");

        response.sendRedirect(redirectUrl);
    }}


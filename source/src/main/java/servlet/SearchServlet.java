package servlet;

import java.io.IOException;
import java.net.URLEncoder;
// ← 追加！

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        
     // nullチェックして空文字にする
        name = name == null ? "" : name;
        furigana = furigana == null ? "" : furigana;
        school = school == null ? "" : school;

        String redirectUrl = "SearchResultServlet?name=" + URLEncoder.encode(name, "UTF-8")
                + "&furigana=" + URLEncoder.encode(furigana, "UTF-8")
                + "&school=" + URLEncoder.encode(school, "UTF-8");

        response.sendRedirect(redirectUrl);
    }}


package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

        String result = "";

        if ((subject == null || subject.isEmpty()) && (personality == null || personality.isEmpty())) {
            result = "結果：情報を入力してください。";
        } else if (subject == null || subject.isEmpty()) {
            result = "結果：教科を入力してください。";
        } else if (personality == null || personality.isEmpty()) {
            result = "結果：性格を入力してください。";
        } else {
            result = "結果：" + subject + " をやろう！（" + personality + "向け）";
        }
        
        System.out.println("subject: [" + subject + "]");
        System.out.println("personality: [" + personality + "]");
        System.out.println("result: [" + result + "]");


        request.setAttribute("result", result);
        request.getRequestDispatcher("/WEB-INF/jsp/text.jsp").forward(request, response);
}}

package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.HomeworkDAO;

@WebServlet("/HomeworkServlet")
public class HomeworkServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String studentIdStr = request.getParameter("studentId");
        String subjectIdStr = request.getParameter("subjectId");

        if (studentIdStr == null || subjectIdStr == null) {
            response.getWriter().println("studentId と subjectId をURLに指定してください。");
            return;
        }

        int studentId = Integer.parseInt(studentIdStr);
        int subjectId = Integer.parseInt(subjectIdStr);

        request.setAttribute("studentId", studentId);
        request.setAttribute("subjectId", subjectId);

        request.getRequestDispatcher("/WEB-INF/jsp/Homework.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        try {
            int studentId = Integer.parseInt(request.getParameter("studentId"));
            int subjectId = Integer.parseInt(request.getParameter("subjectId"));
            int pagesPerDay = Integer.parseInt(request.getParameter("pagesPerDay"));
            String action = request.getParameter("action");
            HomeworkDAO dao = new HomeworkDAO();

            if ("update".equals(action)) {
                dao.updateHomework(studentId, subjectId, pagesPerDay);
            } else {
                dao.insertHomework(studentId, subjectId, pagesPerDay);
            }

            response.sendRedirect("SubjectResultServlet?studentId=" + studentId + "&subjectId=" + subjectId);

        } catch (Exception e) {
            response.getWriter().println("<h3>エラーが発生しました。</h3>");
            response.getWriter().println("<pre>");
            e.printStackTrace(response.getWriter());
            response.getWriter().println("</pre>");
        }
    }
}

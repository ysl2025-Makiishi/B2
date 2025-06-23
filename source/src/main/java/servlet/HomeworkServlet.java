package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

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

        // ここから計算ロジック
        String nextDateStr = request.getParameter("nextDate");
        String totalPagesStr = request.getParameter("totalPages");

        if (nextDateStr != null && totalPagesStr != null) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate today = LocalDate.now();
                LocalDate nextDate = LocalDate.parse(nextDateStr, formatter);
                int totalPages = Integer.parseInt(totalPagesStr);

                long daysBetween = ChronoUnit.DAYS.between(today, nextDate);

                if (daysBetween <= 0) {
                    request.setAttribute("errorMessage", "次に塾に行く日は今日より後の日付を入力してください。");
                } else {
                    int pagesPerDay = (int) Math.ceil((double) totalPages / daysBetween);
                    request.setAttribute("pagesPerDay", pagesPerDay);

                    // 登録済みチェック
                    HomeworkDAO dao = new HomeworkDAO();
                    boolean isRegistered = dao.existsHomework(studentId, subjectId);
                    request.setAttribute("isRegistered", isRegistered);
                }

            } catch (Exception e) {
                request.setAttribute("errorMessage", "入力形式が正しくありません。");
            }
        }

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

            // 登録後は科目ごと個人結果ページへリダイレクト
            response.sendRedirect("SubjectResultServlet?studentId=" + studentId + "&subjectId=" + subjectId);

        } catch (Exception e) {
            response.getWriter().println("<h3>エラーが発生しました。</h3>");
            response.getWriter().println("<pre>");
            e.printStackTrace(response.getWriter());
            response.getWriter().println("</pre>");
        }
    }
}

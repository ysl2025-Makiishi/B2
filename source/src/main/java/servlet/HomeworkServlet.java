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
import javax.servlet.http.HttpSession;

import dao.HomeworkDAO;

@WebServlet("/HomeworkServlet")
public class HomeworkServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //  ログインチェック（最初に行う）
        HttpSession session = request.getSession();
        if (session.getAttribute("id") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        //  パラメータの取得とバリデーション
        String studentIdStr = request.getParameter("studentId");
        String subjectIdStr = request.getParameter("subjectId");

        if (studentIdStr == null || subjectIdStr == null) {
            request.setAttribute("errorMessage", "生徒または科目が指定されていません。");
            request.getRequestDispatcher("/WEB-INF/jsp/Homework.jsp").forward(request, response);
            return;
        }

        int studentId = Integer.parseInt(studentIdStr);
        int subjectId = Integer.parseInt(subjectIdStr);

        request.setAttribute("studentId", studentId);
        request.setAttribute("subjectId", subjectId);

        //  宿題計算処理
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
                    
                    // 既に登録されているか確認
                    HomeworkDAO dao = new HomeworkDAO();
                    boolean isRegistered = dao.existsHomework(studentId, subjectId);
                    request.setAttribute("isRegistered", isRegistered);
                }

            } catch (Exception e) {
                request.setAttribute("errorMessage", "入力形式が正しくありません。");
            }
        }

        //  JSP にフォワード
        request.getRequestDispatcher("/WEB-INF/jsp/Homework.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        
        // ログインチェック（最初に行う）
        HttpSession session = request.getSession();
        if (session.getAttribute("id") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

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
            
            // 科目別の個人結果画面にリダイレクト
            response.sendRedirect(request.getContextPath() + "/SubjectResultServlet?studentId=" + studentId + "&subjectId=" + subjectId);
            
        } catch (Exception e) {
            response.getWriter().println("<h3>エラーが発生しました。</h3>");
            response.getWriter().println("<pre>");
            e.printStackTrace(response.getWriter());
            response.getWriter().println("</pre>");
        }
    }
}

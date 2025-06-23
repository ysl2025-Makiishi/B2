package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ScheduleDAO;
import dto.schedules;

@WebServlet("/ScheduleServlet")
public class ScheduleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ScheduleServlet() {
        super();
    }

    // GETメソッド（画面表示）
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // ★ テスト的に studentId と subjectId を仮でセットしてみる
        request.setAttribute("studentId", 1);
        request.setAttribute("subjectId", 1);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/Schedule.jsp");
        dispatcher.forward(request, response);
    }


    // POSTメソッド（登録処理）
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            int studentId = Integer.parseInt(request.getParameter("studentId"));
            int subjectId = Integer.parseInt(request.getParameter("subjectId"));
            String pageStr = request.getParameter("page");

            System.out.println("DEBUG: studentId = " + studentId);
            System.out.println("DEBUG: subjectId = " + subjectId);
            System.out.println("DEBUG: page = " + pageStr);

            int pages = Integer.parseInt(pageStr);

            schedules schedule = new schedules();
            schedule.setStudent_id(studentId);
            schedule.setSubject_id(subjectId);
            schedule.setPages(pages);

            ScheduleDAO dao = new ScheduleDAO();
            boolean success = dao.upsertPages(schedule);


            System.out.println("DEBUG: DAO update result = " + success);

            if (success) {
                response.sendRedirect("ScheduleServlet");
            } else {
                request.setAttribute("error", "登録に失敗しました。");
                request.getRequestDispatcher("/WEB-INF/jsp/Schedule.jsp").forward(request, response);
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("error", "数字の形式が正しくありません。");
            request.getRequestDispatcher("/WEB-INF/jsp/Schedule.jsp").forward(request, response);
        }
    }
}

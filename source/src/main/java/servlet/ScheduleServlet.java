package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ScheduleServlet")
public class ScheduleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ScheduleServlet() {
        super();
    }

    /**
     * GET：検索処理 + 初期表示
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // パラメータ取得（検索条件）
//        String targetDate = request.getParameter("target_date");
//        String frequency = request.getParameter("frequency");
//        String understandingLevel = request.getParameter("understandingLevel");
//        String pageStr = request.getParameter("page");

//        ScheduleDAO dao = new ScheduleDAO();

        // いずれかの条件が入力されていれば検索
//        if ((targetDate != null && !targetDate.isEmpty()) ||
//           (frequency != null && !frequency.isEmpty()) ||
//            (understandingLevel != null && !understandingLevel.isEmpty()) ||
//           (pageStr != null && !pageStr.isEmpty())) {

//            Integer page = null;
//           if (pageStr != null && !pageStr.isEmpty()) {
//                try {
//                   page = Integer.parseInt(pageStr);
//                } catch (NumberFormatException e) {
//                    request.setAttribute("error", "ページ数は数字で入力してください");
//                    request.getRequestDispatcher("/WEB-INF/jsp/Schedule.jsp").forward(request, response);
//                    return;
//                }
//            }

//          List<Schedule> result = dao.searchByConditions(targetDate, frequency, understandingLevel, page);
//            request.setAttribute("schedules", result);
//      }

        // 入力値を保持してビューへ渡す
//        request.setAttribute("targetDate", targetDate);
//        request.setAttribute("frequency", frequency);
//        request.setAttribute("understandingLevel", understandingLevel);
//        request.setAttribute("page", pageStr);

//        request.getRequestDispatcher("/WEB-INF/jsp/Schedule.jsp").forward(request, response);
//    }

    /**
     * POST：新規登録
     */
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//       request.setCharacterEncoding("UTF-8");

//        String studentIdStr = request.getParameter("studentId");
//        String targetDate = request.getParameter("target_date");
//        String frequency = request.getParameter("frequency");
//        String understandingLevel = request.getParameter("understandingLevel");
//        String pageStr = request.getParameter("page");

//        if (studentIdStr == null || studentIdStr.isEmpty() ||
//            targetDate == null || targetDate.isEmpty() ||
//            frequency == null || frequency.isEmpty() ||
//            understandingLevel == null || understandingLevel.isEmpty() ||
//            pageStr == null || pageStr.isEmpty()) {

//            request.setAttribute("error", "すべての項目を入力してください");
//            request.getRequestDispatcher("/WEB-INF/jsp/Schedule.jsp").forward(request, response);
//            return;
//        }

//        int studentId;
//        int page;
//        try {
//            studentId = Integer.parseInt(studentIdStr);
//            page = Integer.parseInt(pageStr);
//        } catch (NumberFormatException e) {
//            request.setAttribute("error", "IDとページ数は数字で入力してください");
//            request.getRequestDispatcher("/WEB-INF/jsp/Schedule.jsp").forward(request, response);
//            return;
//        }

//        Schedule schedule = new Schedule();
//        schedule.setStudentId(studentId);
//        schedule.setTargetDate(targetDate);
//        schedule.setFrequency(frequency);
//        schedule.setUnderstandingLevel(understandingLevel);
//        schedule.setPage(page);

//        ScheduleDAO dao = new ScheduleDAO();
//        boolean success = dao.insert(schedule);

//        if (success) {
//            response.sendRedirect("ScheduleServlet");
//        } else {
//            request.setAttribute("error", "登録に失敗しました。");
    	  RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/Schedule.jsp");
          dispatcher.forward(request, response);
//        }
    }
}

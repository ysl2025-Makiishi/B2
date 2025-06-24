package servlet;

import java.io.IOException;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String studentIdStr = request.getParameter("studentId");
        String subjectIdStr = request.getParameter("subjectId");
        String textIdStr = request.getParameter("textId");  // ← 追加

        if (studentIdStr == null || subjectIdStr == null) {
            request.setAttribute("error", "生徒または科目が指定されていません。");
            request.getRequestDispatcher("/WEB-INF/jsp/Schedule.jsp").forward(request, response);
            return;
        }

        try {
            int studentId = Integer.parseInt(studentIdStr);
            int subjectId = Integer.parseInt(subjectIdStr);
            request.setAttribute("studentId", studentId);
            request.setAttribute("subjectId", subjectId);

            if (textIdStr != null && !textIdStr.isEmpty()) {
                try {
                    int textId = Integer.parseInt(textIdStr);
                    request.setAttribute("textId", textId);  // ← textIdをJSPに渡す
                } catch (NumberFormatException e) {
                    // textIdが無効なときのログ（あってもなくても可）
                    System.err.println("無効な textId が渡されました：" + textIdStr);
                }
            }

            request.getRequestDispatcher("/WEB-INF/jsp/Schedule.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("error", "不正なIDが指定されました。");
            request.getRequestDispatcher("/WEB-INF/jsp/Schedule.jsp").forward(request, response);
        }
    }





    // POSTメソッド（登録処理）
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        System.out.println("DEBUG: studentId = " + request.getParameter("studentId"));
        System.out.println("DEBUG: subjectId = " + request.getParameter("subjectId"));
        System.out.println("DEBUG: calculated_page = " + request.getParameter("calculated_page"));

        try {
            int studentId = Integer.parseInt(request.getParameter("studentId"));
            int subjectId = Integer.parseInt(request.getParameter("subjectId"));
            String textIdStr = request.getParameter("textId");
            int textId = 0; // 0を「未指定」の意味にする

            if (textIdStr != null && !textIdStr.isEmpty()) {
                try {
                    textId = Integer.parseInt(textIdStr);
                } catch (NumberFormatException e) {
                    textId = 0; // 無効なら0にする
                }
            }


            String calculatedPageStr = request.getParameter("calculated_page");
            if (calculatedPageStr == null || calculatedPageStr.isEmpty()) {
                request.setAttribute("error", "ページ数が計算されていません。");
                request.getRequestDispatcher("/WEB-INF/jsp/Schedule.jsp").forward(request, response);
                return;
            }

            int pages = Integer.parseInt(calculatedPageStr);

            schedules schedule = new schedules();
            schedule.setStudent_id(studentId);
            schedule.setSubject_id(subjectId);
            schedule.setPages(pages);
            schedule.setText_id(textId);
            ScheduleDAO dao = new ScheduleDAO();
            boolean success = dao.upsertPages(schedule);

            System.out.println("DEBUG: DAO update result = " + success);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/SubjectResultServlet?studentId=" + studentId + "&subjectId=" + subjectId);
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

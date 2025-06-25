package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.SearchResultDAO;
import dto.students;

@WebServlet("/SearchResultServlet")
public class SearchResultServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int PAGE_SIZE = 18;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

     // もしもログインしていなかったらログインサーブレットにリダイレクトする
     		HttpSession session = request.getSession();
     		if (session.getAttribute("id") == null) {
     			response.sendRedirect(request.getContextPath() + "/LoginServlet");
     			return;
     		}

        // ★ ログイン済みならPOST処理へ
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

     // もしもログインしていなかったらログインサーブレットにリダイレクトする
     		HttpSession session = request.getSession();
     		if (session.getAttribute("id") == null) {
     			response.sendRedirect("LoginServlet");
     			return;
     		}

        // 削除処理
        String deleteIdStr = request.getParameter("deleteId");
        if (deleteIdStr != null && deleteIdStr.matches("\\d+")) {
            int deleteId = Integer.parseInt(deleteIdStr);
            boolean deleted = SearchResultDAO.deleteById(deleteId);
            if (deleted) {
                System.out.println("削除成功: ID = " + deleteId);
            } else {
                System.out.println("削除失敗: ID = " + deleteId);
            }
            // 再読み込みしてフォーム再送信を防止
            response.sendRedirect("SearchResultServlet");
            return;
        }

        // 検索条件取得
        int id = 0;
        String idStr = request.getParameter("id");
        if (idStr != null && idStr.matches("\\d+")) {
            id = Integer.parseInt(idStr);
        }

        String name = getOrDefault(request.getParameter("name"));
        String furigana = getOrDefault(request.getParameter("furigana"));
        String schoolName = getOrDefault(request.getParameter("schoolName"));

        // 並び替えオプション取得と検証
        String sort = request.getParameter("sort");
        if (sort == null || !(sort.equals("createdDesc") || sort.equals("createdAsc")
                || sort.equals("nameAsc") || sort.equals("nameDesc")
                || sort.equals("updatedAsc") || sort.equals("updatedDesc"))) {
            sort = "createdDesc";
        }

        // ページ番号取得
        int currentPage = 1;
        String pageParam = request.getParameter("page");
        if (pageParam != null && pageParam.matches("\\d+")) {
            currentPage = Integer.parseInt(pageParam);
        }

        // 総件数取得
        int totalRecords = SearchResultDAO.countByName(name, furigana, schoolName);
        int totalPages = (int) Math.ceil((double) totalRecords / PAGE_SIZE);
        int offset = (currentPage - 1) * PAGE_SIZE;

        // 生徒リスト取得
        List<students> studentList = SearchResultDAO.searchByName(
                id, name, furigana, schoolName, sort, PAGE_SIZE, offset
        );

        // JSPへデータ渡す
        request.setAttribute("studentid", id);
        request.setAttribute("studentList", studentList);
        request.setAttribute("name", name);
        request.setAttribute("furigana", furigana);
        request.setAttribute("schoolName", schoolName);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("sort", sort);
        request.setAttribute("totalRecords", totalRecords);

        request.getRequestDispatcher("/WEB-INF/jsp/SearchResult.jsp").forward(request, response);
    }

    private String getOrDefault(String val) {
        if (val == null || val.trim().isEmpty()) {
            return null;
        }
        return val;
    }
}

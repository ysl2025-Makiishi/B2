	package servlet;
	
	import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SearchResultDAO;
import dto.students;
	
	@WebServlet("/SearchResultServlet")
	public class SearchResultServlet extends HttpServlet {
	    private static final long serialVersionUID = 1L;
	
	    private static final int PAGE_SIZE = 18;
	
	    @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        doPost(request, response); // GETもPOSTも同じ処理
	    }
	
	    @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	
	        request.setCharacterEncoding("UTF-8");
	
	        String nameKeyword = getOrDefault(request.getParameter("nameKeyword"));
	        String furiganaKeyword = getOrDefault(request.getParameter("furiganaKeyword"));
	        String schoolNameKeyword = getOrDefault(request.getParameter("schoolNameKeyword"));
	
	        String sort = request.getParameter("sort");
	        if (sort == null) sort = "createdDesc";
	
	        int currentPage = 1;
	        String pageParam = request.getParameter("page");
	        if (pageParam != null && pageParam.matches("\\d+")) {
	            currentPage = Integer.parseInt(pageParam);
	        }
	
	        // 総件数をDAOで取得
	        int totalRecords = SearchResultDAO.countByName(nameKeyword, furiganaKeyword, schoolNameKeyword);
	        int totalPages = (int) Math.ceil((double) totalRecords / PAGE_SIZE);
	
	        int offset = (currentPage - 1) * PAGE_SIZE;
	
	        // ページサイズと開始位置をDAOに渡して必要分だけ取得
	        List<students> studentList = SearchResultDAO.searchByName(
	            nameKeyword, furiganaKeyword, schoolNameKeyword, sort, PAGE_SIZE, offset
	        );
	
	        // JSPにセット
	        request.setAttribute("studentList", studentList);
	        request.setAttribute("nameKeyword", nameKeyword);
	        request.setAttribute("furiganaKeyword", furiganaKeyword);
	        request.setAttribute("schoolNameKeyword", schoolNameKeyword);
	        request.setAttribute("currentPage", currentPage);
	        request.setAttribute("totalPages", totalPages);
	        request.setAttribute("sort", sort);
	        request.setAttribute("totalRecords", totalRecords);
	
	        request.getRequestDispatcher("/WEB-INF/jsp/SearchResult.jsp").forward(request, response);
	    }
	
	    private String getOrDefault(String val) {
	        return val == null ? "" : val;
	    }
	}

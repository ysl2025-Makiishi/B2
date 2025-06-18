package servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.RegistDAO;

//後でdaoとdtoをimportする。詳しくはサンプル

@WebServlet("/RegistServlet")
public class RegistServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// もしもログインしていなかったらログインサーブレットにリダイレクトする
//		HttpSession session = request.getSession();
//		if (session.getAttribute("id") == null) {
//			response.sendRedirect("/webapp/LoginServlet");
//			return;
//		}

		// 登録ページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/regist.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// もしもログインしていなかったらログインサーブレットにリダイレクトする
//		HttpSession session = request.getSession();
//		if (session.getAttribute("id") == null) {
//			response.sendRedirect("/webapp/LoginServlet");
//			return;
//		}
		try {
		//Gpa登録用Map
		Map<Integer, Integer> gpasMap = new HashMap<>();
		for(int i=1; i<=9; i++) {
			String gpaStr = request.getParameter("gpa" + i);
			
			if(gpaStr != null && !gpaStr.isEmpty()) {
				gpasMap.put(i, Integer.parseInt(gpaStr));
			}
		}
		
		//登録処理
		RegistDAO dao = new RegistDAO();
		dao.registAll(
				request.getParameter("name"),
			    request.getParameter("furigana"),
			    request.getParameter("school"),
			    new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("birthday")),
			    request.getParameter("gender"),
			    request.getParameter("aspiration_school1"),
			    request.getParameter("aspiration_school2"),
			    request.getParameter("aspiration_school3"),
			    gpasMap
				);

		// 結果ページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/regist.jsp");
		dispatcher.forward(request, response);
	} catch (java.text.ParseException e) {
		e.printStackTrace();
		request.setAttribute("error", "日付の形式が正しくありません。");
	}
	}
}

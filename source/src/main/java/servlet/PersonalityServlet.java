package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/PersonalityServlet")
public class PersonalityServlet extends HttpServlet{
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
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/personality.jsp");
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

//		 リクエストパラメータを取得する
//		request.setCharacterEncoding("UTF-8");
//		String name = request.getParameter("name");
//		String furigana = request.getParameter("furigana");
//		int school_id = Integer.parseInt(request.getParameter("school_id"));
//		Date birthday = Date.valueOf(request.getParameter("birthday"));
//		String gender = request.getParameter("gender");
//		//志望校
//		String aspiration_school1 = request.getParameter("aspiration_school1");
//		String aspiration_school2 = request.getParameter("aspiration_school2");
//		String aspiration_school3 = request.getParameter("aspiration_school3");
//		
//		// 学生DTO作成
//				students student = new students();
//				student.setName(name);
//				student.setFurigana(furigana);
//				student.setSchool_id(school_id);
//				student.setBirthday(birthday);
//				student.setGender(gender);
//				student.setAspiration_school1_id(aspiration_school1_id);
//				student.setAspiration_school2_id(aspiration_school2_id);
//				student.setAspiration_school3_id(aspiration_school3_id);
//				student.setCreated_at(new java.util.Date());
//				student.setUpdated_at(new java.util.Date());
//		
//		//成績の登録（科目）
//		String subject1 = request.getParameter("subject1");
//		String subject2 = request.getParameter("subject2");
//		String subject3 = request.getParameter("subject3");
//		String subject4 = request.getParameter("subject4");
//		String subject5 = request.getParameter("subject5");
//		String subject6 = request.getParameter("subject6");
//		String subject7 = request.getParameter("subject7");
//		String subject8 = request.getParameter("subject8");
//		String subject9 = request.getParameter("subject9");
//		String gpa1 = request.getParameter("gpa1");
//		String gpa2 = request.getParameter("gpa2");
//		String gpa3 = request.getParameter("gpa3");
//		String gpa4 = request.getParameter("gpa4");
//		String gpa5 = request.getParameter("gpa5");
//		String gpa6 = request.getParameter("gpa6");
//		String gpa7 = request.getParameter("gpa7");
//		String gpa8 = request.getParameter("gpa8");
//		String gpa9 = request.getParameter("gpa9");
//
//		// 登録処理を行う
//		RegistDAO rDao = new RegistDAO();
//		if (rDao.insert(new St(0, name, furigana, school_id, birthday, gender, aspiration_school1_id, aspiration_school2_id, aspiration_school3_id, email, remarks))) { // 登録成功
//			request.setAttribute("result", new Result("登録成功！", "レコードを登録しました。", "/webapp/MenuServlet"));
//		} else { // 登録失敗
//			request.setAttribute("result", new Result("登録失敗！", "レコードを登録できませんでした。", "/webapp/MenuServlet"));
//		}

		// 結果ページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/regist.jsp");
		dispatcher.forward(request, response);
	}
}

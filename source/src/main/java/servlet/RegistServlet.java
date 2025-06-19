package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.RegistDAO;
import dto.aspiration_schools;
import dto.schools;
import dto.students;

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
		
		//生年月日を受け取る
		String birthdayStr = request.getParameter("birthday");
		java.sql.Date sqlBirthDate = java.sql.Date.valueOf(birthdayStr);  // ← 修正

		//入力値を受け取る
		String name = request.getParameter("name");
		String furigana = request.getParameter("furigana");
		String schoolName = request.getParameter("school");
		String gender = request.getParameter("gender");
		String aspiration_school1 = request.getParameter("aspiration_school1");
		String aspiration_school2 = request.getParameter("aspiration_school2");
		String aspiration_school3 = request.getParameter("aspiration_school3");

		try {
			//DAO初期か
			RegistDAO dao = new RegistDAO();
			
			//学校名からschool_id取得or登録
			schools schoolDTO = new schools();
			schoolDTO.setSchool_name(schoolName);
			int schoolId = dao.insertOrGetSchoolId(schoolDTO);
			
			//志望校名からaspiration_school_id取得or登録
			aspiration_schools aspDTO = new aspiration_schools();
			aspDTO.setAspiration_school_name(aspiration_school1);
			int aspiration_school1_id = dao.insertOrGetAspirationSchoolId(aspDTO);
			
			aspDTO.setAspiration_school_name(aspiration_school2);
			int aspiration_school2_id = dao.insertOrGetAspirationSchoolId(aspDTO);
			
			aspDTO.setAspiration_school_name(aspiration_school3);
			int aspiration_school3_id = dao.insertOrGetAspirationSchoolId(aspDTO);
			
			//生徒情報のDTO作成
			students studentDTO = new students();
			studentDTO.setName(name);
            studentDTO.setFurigana(furigana);
            studentDTO.setSchool_id(schoolId);
            studentDTO.setBirthday(sqlBirthDate);
            studentDTO.setGender(gender);
            studentDTO.setAspiration_school1_id(aspiration_school1_id);
            studentDTO.setAspiration_school2_id(aspiration_school2_id);
            studentDTO.setAspiration_school3_id(aspiration_school3_id);
            studentDTO.setPersonality_id(5); // 初期は空欄扱い
            // createdAt・updatedAt は DAO 側で NOW() により登録
            
            //生徒IDの取得が必要なら、insertStudentを改良してreturnさせる
            
            
            //生徒を登録
            int studentId = dao.insertStudent(studentDTO);
            
            //成績情報を取得(subject1~subject9)
            Map<Integer, Integer> gpas = new HashMap<>();
            for (int i=1; i<=9; i++) {
            	String scoreStr = request.getParameter("gpa" + i);
            	if(scoreStr != null && !scoreStr.isEmpty()) {
            		try {
            			int score = Integer.parseInt(scoreStr);
            			gpas.put(i, score);	//subject_idとscore
            			
            		} catch (NumberFormatException e) {
            			//無効な数値はスキップ
            		}
            	}
            }
            
            //成績登録
            dao.insertGrades(studentId, gpas);
            
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		

		// 結果ページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/regist.jsp");
		dispatcher.forward(request, response);
	}
}

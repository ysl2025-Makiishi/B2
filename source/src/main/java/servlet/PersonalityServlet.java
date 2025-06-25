package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.PersonalityDAO;


@WebServlet("/PersonalityServlet")
public class PersonalityServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	//もしもログインしていなかったらログインサーブレットにリダイレクトする
    			HttpSession session = request.getSession();
    			if (session.getAttribute("id") == null) {
    				response.sendRedirect(request.getContextPath() + "/LoginServlet");
    				return;
    			}
    			
    	//ここにいれる
    	request.setCharacterEncoding("UTF-8");
    	
        // === デバッグ情報を詳しく出力 ===
//        System.out.println("=== PersonalityServlet GET リクエスト ===");
//        System.out.println("リクエストURL: " + request.getRequestURL());
//        System.out.println("クエリ文字列: " + request.getQueryString());
//        System.out.println("リファラー: " + request.getHeader("Referer"));
//        // すべてのパラメータを表示
//        System.out.println("=== すべてのパラメータ ===");
//        request.getParameterMap().forEach((key, values) -> {
//            System.out.println(key + " = " + String.join(", ", values));
//        });
//        System.out.println("========================");
        // studentId パラメータの詳細確認
        String studentIdStr = request.getParameter("studentId");
        //System.out.println("性格診断：受け取った studentId = " + studentIdStr);
        if (studentIdStr == null) {
           // System.out.println("ERROR: studentId パラメータが null です");
        } else if (studentIdStr.trim().isEmpty()) {
           // System.out.println("ERROR: studentId パラメータが空文字です");
        } else if (!studentIdStr.matches("\\d+")) {
          //  System.out.println("ERROR: studentId が数字ではありません: " + studentIdStr);
        } else {
            int studentId = Integer.parseInt(studentIdStr);
          //  System.out.println("SUCCESS: 正常な studentId を受け取りました: " + studentId);
            // 生徒IDをリクエスト属性にセット
            request.setAttribute("studentId", studentId);
        }
        // 性格診断ページにフォワード
       // System.out.println("性格診断ページにフォワードします");
        request.getRequestDispatcher("/WEB-INF/jsp/personality.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	//もしもログインしていなかったらログインサーブレットにリダイレクトする
    			HttpSession session = request.getSession();
    			if (session.getAttribute("id") == null) {
    				response.sendRedirect(request.getContextPath() + "/LoginServlet");
    				return;
    			}
    			
    	//ここにいれる
    	request.setCharacterEncoding("UTF-8");
    	
        //System.out.println("=== PersonalityServlet POST リクエスト ===");
        // POST処理が必要な場合はここに追加

        // リクエストパラメータ取得
        String studentIdStr = request.getParameter("studentId");
        String personalityName = request.getParameter("personality");

        //System.out.println("受け取った studentId: " + studentIdStr);
        //System.out.println("受け取った personality: " + personalityName);

        // バリデーション
        if (studentIdStr == null || !studentIdStr.matches("\\d+")) {
            //System.out.println("ERROR: studentIdが不正です: " + studentIdStr);
            return;
        }

        int studentId = Integer.parseInt(studentIdStr);

        // DAOを使って性格IDを取得
        PersonalityDAO dao = new PersonalityDAO();
        Integer personalityId = dao.getPersonalityIdByName(personalityName);

        if (personalityId == null) {
            //System.out.println("ERROR: 性格IDの取得に失敗しました");
            return;
        }

        // 生徒の personality_id を更新
        boolean success = dao.updateStudentPersonality(studentId, personalityId);
        if (success) {
            //System.out.println("SUCCESS: 生徒の性格IDを更新しました");
            response.sendRedirect(request.getContextPath() + "/IndividualResultsServlet?studentId=" + studentId);
            //response.sendRedirect("/WEB-INF/jsp/IndividualResults.jsp");
        } else {
            //System.out.println("ERROR: 生徒の性格ID更新に失敗しました");
        }
        //System.out.println("=== PersonalityServlet POST リクエスト ===");
        
    }
    
    
    }


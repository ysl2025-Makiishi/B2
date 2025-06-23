<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.time.LocalDate, java.time.temporal.ChronoUnit" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="<c:url value='/css/Homework.css' />">
    <meta charset="UTF-8">
    
    <title>宿題提案</title>
    
    <style>
    .result {
        text-align: center;
        margin-top: 30px;
        font-size: 20px;
    }
    form {
        text-align: center;
    }

    .form-group {
        display: inline-block;
        text-align: left;
        margin-bottom: 15px;
    }

    .form-group label {
        display: block;
        margin-bottom: 5px;
        font-weight: bold;
    }

    .form-group input[type="date"],
    .form-group input[type="number"] {
        width: 200px;
        padding: 6px;
        font-size: 14px;
    }

    .submit-btn {
        margin-top: 20px;
        text-align: center;
    }

    .submit-btn button {
        padding: 10px 25px;
        font-size: 16px;
    }
    
    
</style>
    
</head>
<div class="wrapper">
<body>
<h1 id="logo" style="text-align: center;">
			<a href="<c:url value='/HomeServlet' />"> <img
				src="<c:url value='/img/K-Manage_logo.png' />" alt="K-Manage">
			</a>

		</h1>
		<ul id="nav">
			<li><a href="<c:url value='/HomeServlet' />">ホーム</a></li>
			<li><a href="<c:url value='/StudentListServlet' />">生徒一覧</a></li>
			<li><a href="<c:url value='/RegistServlet' />">登録</a></li>
			<li><a href="<c:url value='/SearchServlet' />">検索</a></li>
			<li><a href="<c:url value='/LogoutServlet' />"
				onclick="return confirm('本当に実行しますか？');">ログアウト</a></li>
		</ul>
<div class="container">
    <h2>宿題提案</h2>

    <!-- ★ 提案を表示フォーム -->
    <form method="get" action="HomeworkServlet">
    <input type="hidden" name="studentId" value="${studentId}">
    <input type="hidden" name="subjectId" value="${subjectId}">

    <div class="form-group">
        <label>次に塾に行く日：</label>
        <input type="date" name="nextDate"
               value="<%= request.getParameter("nextDate") != null ? request.getParameter("nextDate") : "" %>" required>
    </div>

    <div class="form-group">
        <label>終わらせたいページ数：</label>
        <input type="number" name="totalPages" min="1"
               value="<%= request.getParameter("totalPages") != null ? request.getParameter("totalPages") : "" %>" required>
    </div>

    <div class="submit-btn">
        <button type="submit">提案を表示</button>
    </div>
</form>
    
    <div style="text-align: center; margin-top: 30px;">
        <form action="<c:url value='/SubjectResultServlet' />" method="get">
          <button type="submit" style="
            background-color: #888;
            color: white;
            padding: 10px 20px;
            font-size: 16px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
          ">
            科目ごと個人結果に戻る
          </button>
        </form>
      </div>

<%
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
%>
                <div class="result">⚠ 次に塾に行く日は今日より後の日付を入力してください。</div>
<%
            } else {
                int pagesPerDay = (int) Math.ceil((double) totalPages / daysBetween);

                // ★ ここで DAO を使って登録済みかチェック
                boolean isRegistered = false;
                try {
                    int studentId = Integer.parseInt(request.getParameter("studentId"));
                    int subjectId = Integer.parseInt(request.getParameter("subjectId"));
                    dao.HomeworkDAO dao = new dao.HomeworkDAO();
                    isRegistered = dao.existsHomework(studentId, subjectId);
                } catch (Exception e) {
                    // エラーハンドリングしてもよい
                }

                String actionType = isRegistered ? "update" : "insert";
%>
                <div class="result">
                    <strong>1日に <%= pagesPerDay %> ページ</strong>進めよう！
                </div>

                <!-- 登録 or 更新 ボタン -->
                <form method="post" action="HomeworkServlet" style="text-align:center; margin-top:30px;">
                    <input type="hidden" name="studentId" value="${studentId}">
                    <input type="hidden" name="subjectId" value="${subjectId}">
                    <input type="hidden" name="pagesPerDay" value="<%= pagesPerDay %>">
                    <input type="hidden" name="action" value="<%= actionType %>">
                    <button type="submit" class="register-button">
                        <%= isRegistered ? "宿題を更新" : "この宿題を登録" %>
                    </button>
                </form>
<%
            }
        } catch (Exception e) {
%>
            <div class="result">⚠ 入力形式が正しくありません。</div>
<%
        }
    }
%>

</div>
</div>
</body>
</html>

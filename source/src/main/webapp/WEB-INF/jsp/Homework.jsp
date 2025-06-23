<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>宿題提案</title>
  <link rel="stylesheet" href="<c:url value='/css/Homework.css' />">
  <style>
    .result { text-align: center; margin-top: 30px; font-size: 20px; }
    form { text-align: center; }
    .form-group {
      display: inline-block; text-align: left; margin-bottom: 15px;
    }
    .form-group label {
      display: block; margin-bottom: 5px; font-weight: bold;
    }
    .form-group input[type="date"],
    .form-group input[type="number"] {
      width: 200px; padding: 6px; font-size: 14px;
    }
    .submit-btn {
      margin-top: 20px; text-align: center;
    }
    .submit-btn button {
      padding: 10px 25px; font-size: 16px;
    }
  </style>
</head>
<body>
  <div class="wrapper">
    <h1 id="logo" style="text-align: center;">
      <a href="<c:url value='/HomeServlet' />">
        <img src="<c:url value='/img/K-Manage_logo.png' />" alt="K-Manage">
      </a>
    </h1>

    <ul id="nav">
      <li><a href="<c:url value='/HomeServlet' />">ホーム</a></li>
      <li><a href="<c:url value='/StudentListServlet' />">生徒一覧</a></li>
      <li><a href="<c:url value='/RegistServlet' />">登録</a></li>
      <li><a href="<c:url value='/SearchServlet' />">検索</a></li>
      <li><a href="<c:url value='/LogoutServlet' />" onclick="return confirm('本当に実行しますか？');">ログアウト</a></li>
    </ul>

    <div class="container">
      <h2>宿題提案</h2>

      <!-- 提案を表示フォーム -->
      <form method="get" action="<c:url value='/HomeworkServlet' />">
        <input type="hidden" name="studentId" value="${studentId}">
        <input type="hidden" name="subjectId" value="${subjectId}">

        <div class="form-group">
          <label>次に塾に行く日：</label>
          <input type="date" name="nextDate" value="${param.nextDate}" required>
        </div>

        <div class="form-group">
          <label>終わらせたいページ数：</label>
          <input type="number" name="totalPages" min="1" value="${param.totalPages}" required>
        </div>

        <div class="submit-btn">
          <button type="submit">提案を表示</button>
        </div>
      </form>

      <!-- エラー表示 -->
      <c:if test="${not empty errorMessage}">
        <div class="result">⚠ ${errorMessage}</div>
      </c:if>

      <!-- 提案結果の表示 -->
      <c:if test="${not empty pagesPerDay}">
        <div class="result">
          <strong>1日に ${pagesPerDay} ページ</strong>進めよう！
        </div>

        <!-- 登録または更新ボタン -->
        <form method="post" action="<c:url value='/HomeworkServlet' />" style="text-align:center; margin-top:30px;">
          <input type="hidden" name="studentId" value="${studentId}">
          <input type="hidden" name="subjectId" value="${subjectId}">
          <input type="hidden" name="pagesPerDay" value="${pagesPerDay}">
          <input type="hidden" name="action" value="${isRegistered ? 'update' : 'insert'}">
          <button type="submit" class="register-button">
            <c:choose>
              <c:when test="${isRegistered}">宿題を更新</c:when>
              <c:otherwise>この宿題を登録</c:otherwise>
            </c:choose>
          </button>
        </form>
      </c:if>

      <!-- 戻るボタン -->
      <div style="text-align: center; margin-top: 30px;">
        <form action="<c:url value='/SubjectResultServlet' />" method="get">
          <input type="hidden" name="studentId" value="${studentId}">
          <input type="hidden" name="subjectId" value="${subjectId}">
          <button type="submit" style="
            background-color: #888;
            color: white;
            padding: 10px 20px;
            font-size: 16px;
            border: none;
            border-radius: 8px;
            cursor: pointer;">
            科目ごと個人結果に戻る
          </button>
        </form>
      </div>
    </div>
  </div>
</body>
</html>

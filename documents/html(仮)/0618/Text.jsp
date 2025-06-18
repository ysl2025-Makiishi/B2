<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>テキスト選出システム | K-manage</title>
  <link rel="stylesheet" href="<c:url value='/css/K-style.css' />">
  <style>
    body {
      font-family: Arial, sans-serif;
      padding: 40px;
    }
    h1 {
      font-size: 24px;
      margin-bottom: 20px;
      text-align: center;
    }
    label {
      display: block;
      margin: 15px 0 5px;
      font-size: 18px;
      font-weight: bold;
    }
    select {
      width: 300px;
      padding: 8px;
      font-size: 16px;
    }
    .buttons {
      margin: 20px 0;
    }
    button {
      padding: 10px 20px;
      font-size: 16px;
      margin-right: 10px;
    }
    .result {
      margin-top: 30px;
      font-size: 22px;
      font-weight: bold;
      color: #1685e6;
    }
    #footer {
      margin-top: 50px;
      text-align: center;
      font-size: 14px;
      color: #999;
    }
    #nav {
      list-style: none;
      padding: 0;
      display: flex;
      gap: 15px;
      justify-content: center;
      margin-bottom: 40px;
    }
    #nav li a {
      text-decoration: none;
      font-size: 16px;
      color: #1685e6;
    }
  </style>
</head>
<body>
<!-- ここにデバッグ用表示を入れる -->
<c:out value="textsListがセットされていますか？: ${not empty textsList}" /><br />
<c:out value="textsListの件数: ${fn:length(textsList)}" /><br />

  <div class="wrapper">
    <!-- ロゴ -->
    <h1 id="logo">
      <div style="text-align: center;">
        <a href="<c:url value='/HomeServlet' />">
          <img src="<c:url value='/img/K-Manage_logo.png' />" alt="K-Manage">
        </a>
      </div>
    </h1>

    <!-- ナビゲーション -->
    <ul id="nav">
      <li><a href="<c:url value='/HomeServlet' />">ホーム</a></li>
      <li><a href="<c:url value='/StudentListServlet' />">生徒一覧</a></li>
      <li><a href="<c:url value='/RegistServlet' />">登録</a></li>
      <li><a href="<c:url value='/SearchServlet' />">検索</a></li>
      <li><a href="<c:url value='/LoginServlet' />">ログアウト</a></li>
    </ul>

    <!-- 中央寄せコンテンツ -->
    <div class="content-center">
      <h2>テキスト選出</h2>

      <form action="<c:url value='/TextServlet' />" method="post" onsubmit="return validateForm()">
        <label for="subject">学習したい教科</label>
        <select id="subject" name="subject">
          <option value="">-- 教科を選んでください --</option>
          <option value="1">国語</option>
          <option value="2">数学</option>
          <option value="3">英語</option>
          <option value="4">理科</option>
          <option value="5">社会</option>
        </select>

        <label for="personality">生徒の性格</label>
        <select id="personality" name="personality">
          <option value="">-- 性格を選んでください --</option>
          <option value="1">開放性</option>
          <option value="2">勤勉性</option>
          <option value="3">神経症傾向</option>
          <option value="4">外向性</option>
        </select>

        <div class="buttons">
          <button type="submit">検索</button>
          <button type="reset">リセット</button>
          <span id="warningMsg" style="color: red; font-size: 12px;"></span>
        </div>
      </form>

      <!-- 検索結果表示 -->
     <!-- 検索結果表示の下 -->
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:if test="${not empty textsList}">
  <p>デバッグ: textsListの件数 = ${fn:length(textsList)}</p>
  
  <h3>おすすめ教材一覧</h3>
  <ul>
    <c:forEach var="text" items="${textsList}">
      <li>
        教材名: ${text.textName} ／ ページ数: ${text.pages} ／ 備考: ${text.note}
        <form action="<c:url value='/TextRegisterServlet' />" method="post" style="display:inline;">
          <input type="hidden" name="textId" value="${text.id}" />
          <input type="hidden" name="subjectId" value="${text.subjectId}" />
          <input type="hidden" name="personalityId" value="${text.personalityId}" />
          <button type="submit">登録</button>
        </form>
      </li>
    </c:forEach>
  </ul>
</c:if>
 
  <script>
    function validateForm() {
      const subject = document.getElementById('subject').value;
      const personality = document.getElementById('personality').value;
      const msg = document.getElementById('warningMsg');

      msg.textContent = '';

      if (!subject) {
        msg.textContent = '教科を選んでください。';
        return false;
      }
      if (!personality) {
        msg.textContent = '性格を選んでください。';
        return false;
      }
      return true;
    }
  </script>
</body>
</html>

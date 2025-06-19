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
    .wrapper {
      max-width: 800px;
      margin: 0 auto;
    }
    h1, h2 {
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
  <div class="wrapper">
    <h1 id="logo">
      <a href="<c:url value='/HomeServlet' />">
        <img src="<c:url value='/img/K-Manage_logo.png' />" alt="K-Manage" width="200" height="200">
      </a>
    </h1>

    <ul id="nav">
      <li><a href="<c:url value='/HomeServlet' />">ホーム</a></li>
      <li><a href="<c:url value='/StudentListServlet' />">生徒一覧</a></li>
      <li><a href="<c:url value='/RegistServlet' />">登録</a></li>
      <li><a href="<c:url value='/SearchServlet' />">検索</a></li>
      <li><a href="<c:url value='/LogoutServlet' />" onclick="return confirm('本当に実行しますか？');">ログアウト</a></li>
    </ul>

    <div class="content-center">
      <h2>テキスト選出</h2>

      <form id="searchForm" action="<c:url value='/TextServlet' />" method="post" onsubmit="return validateForm();">
        <label for="subject">学習したい教科</label>
        <select id="subject" name="subject">
          <option value="">-- 教科を選んでください --</option>
          <option value="1" <c:if test="${subject == 1}">selected</c:if>>国語</option>
          <option value="2" <c:if test="${subject == 2}">selected</c:if>>数学</option>
          <option value="3" <c:if test="${subject == 3}">selected</c:if>>英語</option>
          <option value="4" <c:if test="${subject == 4}">selected</c:if>>理科</option>
          <option value="5" <c:if test="${subject == 5}">selected</c:if>>社会</option>
        </select>

        <label for="personality">生徒の性格</label>
        <select id="personality" name="personality">
          <option value="">-- 性格を選んでください --</option>
          <option value="1" <c:if test="${personality == 1}">selected</c:if>>開放性</option>
          <option value="2" <c:if test="${personality == 2}">selected</c:if>>勤勉性</option>
          <option value="3" <c:if test="${personality == 3}">selected</c:if>>神経症傾向</option>
          <option value="4" <c:if test="${personality == 4}">selected</c:if>>外向性</option>
        </select>

        <div class="buttons">
          <button type="submit">検索</button>
          <button type="reset">リセット</button>
          <span id="warningMsg" style="color: red; font-size: 12px;"></span>
        </div>
      </form>

      <div id="searchResult">
  <c:if test="${searched}">
  <c:choose>
    <c:when test="${not empty textsList}">
      <h3>おすすめ教材一覧</h3>
      <p>ヒット件数: ${fn:length(textsList)}</p>
      <ul>
        <c:forEach var="text" items="${textsList}">
          <li>
            結果：${text.textName} を使おう！
            <form action="<c:url value='/TextRegisterServlet' />" method="post" style="display:inline;">
              <input type="hidden" name="textId" value="${text.id}" />
              <button type="submit">登録</button>
            </form>
          </li>
        </c:forEach>
      </ul>
    </c:when>
    <c:otherwise>
      <p>検索結果はありません</p>
    </c:otherwise>
  </c:choose>
</c:if>
  
</div>
      
    </div>
  </div>

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

    // リセット処理
    document.getElementById('searchForm').addEventListener('reset', function () {
      document.getElementById('warningMsg').textContent = '';
      document.getElementById('subject').selectedIndex = 0;
      document.getElementById('personality').selectedIndex = 0;

      // 検索結果を非表示にする
      const resultArea = document.getElementById('searchResult');
      if (resultArea) resultArea.innerHTML = '';
    });
  </script>
</body>
</html>

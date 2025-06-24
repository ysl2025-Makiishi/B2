<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ja">

<head>
  <meta charset="UTF-8">
  <title>テキスト選出システム | K-manage</title>
  <link rel="stylesheet" href="<c:url value='/css/K-style.css' />">
  <style>
    body {
      font-family: Arial, sans-serif;
      padding: 0 40px 40px 40px;
    }

    h1, h2 {
      text-align: center;
    }

    form#searchForm label,
    form#searchForm select {
      display: block;
      width: 300px;
      margin-left: auto;
      margin-right: auto;
      text-align: center;
    }

    form#searchForm {
      text-align: left;
      margin: 0 auto;
    }

    form#searchForm .buttons {
      text-align: center;
      margin: 20px 0;
    }

    #searchResult {
      max-width: 700px;
      margin: 30px auto 0 auto;
      text-align: center;
    }

    .card-container {
      margin: 0 auto;
      max-width: 700px;
    }

    .card {
      border: 2px solid #1685E6;
      border-radius: 12px;
      padding: 20px;
      margin-bottom: 20px;
      background-color: #f9f9f9;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
      max-width: 600px;
      margin-left: auto;
      margin-right: auto;
      text-align: center;
    }

    .card .result-text {
      font-size: 22px;
      font-weight: bold;
      color: #1685E6;
      margin-bottom: 10px;
      display: block;
    }

    .modern-button {
      font-size: 16px;
      padding: 10px 24px;
      border: none;
      border-radius: 25px;
      cursor: pointer;
      transition: all 0.3s ease;
      margin: 0 8px;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }

    .modern-button.primary {
      background: linear-gradient(135deg, #1685E6, #5dade2);
      color: white;
    }

    .modern-button.primary:hover {
      background: linear-gradient(135deg, #0f6ac4, #439fd7);
      transform: translateY(-2px);
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

      <c:if test="${not empty message}">
        <p style="color: green; font-weight: bold; text-align: center; margin-bottom: 20px;">
          ${message}
        </p>
      </c:if>

      <form id="searchForm" action="<c:url value='/TextServlet' />" method="post" onsubmit="return validateForm();">
        <input type="hidden" name="studentId" value="${studentId}" />

        <label for="subject">学習したい教科</label>
        <select id="subject" name="subject">
          <option value="" <c:if test="${subject == null}">selected</c:if>>-- 教科を選んでください --</option>
          <option value="1" <c:if test="${subject == 1}">selected</c:if>>国語</option>
          <option value="2" <c:if test="${subject == 2}">selected</c:if>>数学</option>
          <option value="3" <c:if test="${subject == 3}">selected</c:if>>英語</option>
          <option value="4" <c:if test="${subject == 4}">selected</c:if>>理科</option>
          <option value="5" <c:if test="${subject == 5}">selected</c:if>>社会</option>
        </select>

        <label for="personality">生徒の性格</label>
        <select id="personality" name="personality">
          <option value="" <c:if test="${personality == null}">selected</c:if>>-- 性格を選んでください --</option>
          <option value="1" <c:if test="${personality == 1}">selected</c:if>>開放性</option>
          <option value="2" <c:if test="${personality == 2}">selected</c:if>>勤勉性</option>
          <option value="3" <c:if test="${personality == 3}">selected</c:if>>神経症傾向</option>
          <option value="4" <c:if test="${personality == 4}">selected</c:if>>外向性</option>
        </select>

        <div class="buttons">
          <button type="submit" class="modern-button primary">検索</button>
          <button type="reset" class="modern-button primary">リセット</button>
          <span id="warningMsg" style="color: red; font-size: 12px;"></span>
        </div>
      </form>
		<!-- 検索結果表示の上あたりに登録済みテキスト表示を追加 -->
<c:if test="${not empty registeredText}">
  <div class="card-container">
    <div class="card">
      <span class="result-text">登録済みテキスト：${registeredText.textName}</span>
      <form action="<c:url value='/TextServlet' />" method="post" style="display:inline-block;">
        <input type="hidden" name="action" value="update" />
        <input type="hidden" name="textId" value="${registeredText.id}" />
        <input type="hidden" name="studentId" value="${studentId}" />
        <input type="hidden" name="subjectId" value="${subjectId}" />
        <button type="submit" class="modern-button primary" style="background: linear-gradient(135deg, #E68A00, #f4b942);">更新</button>
      </form>
    </div>
  </div>
</c:if>
		
      <div id="searchResult">
        <c:if test="${searched}">
          <c:choose>
            <c:when test="${not empty textsList}">
              <div class="card-container">
                <c:forEach var="text" items="${textsList}">
                  <div class="card">
                    <span class="result-text">結果：${text.textName} を使おう！</span>

                    <form action="<c:url value='/TextServlet' />" method="post" style="display:inline-block;">
                      <input type="hidden" name="action" value="register" />
                      <input type="hidden" name="textId" value="${text.id}" />
                      <input type="hidden" name="studentId" value="${studentId}" />
                      <input type="hidden" name="subjectId" value="${subjectId}" />
                      <button type="submit" class="modern-button primary">登録</button>
                    </form>

                    <form action="<c:url value='/TextServlet' />" method="post" style="display:inline-block;">
                      <input type="hidden" name="action" value="update" />
                      <input type="hidden" name="textId" value="${text.id}" />
                      <input type="hidden" name="studentId" value="${studentId}" />
                      <input type="hidden" name="subjectId" value="${subjectId}" />
                      <button type="submit" class="modern-button primary" style="background: linear-gradient(135deg, #E68A00, #f4b942);">更新</button>
                    </form>
                  </div>
                </c:forEach>
              </div>
            </c:when>
          </c:choose>
        </c:if>
      </div>

      <div style="text-align: center; margin-top: 30px;">
        <form action="<c:url value='/SubjectResultServlet' />" method="get">
          <input type="hidden" name="studentId" value="${studentId}" />
          <input type="hidden" name="subjectId" value="${subjectId}" />
          <button type="submit" style="background-color: #888; color: white; padding: 10px 20px; font-size: 16px; border: none; border-radius: 8px; cursor: pointer;">
            科目ごと個人結果に戻る
          </button>
        </form>
      </div>
    </div>
  </div>
  <script src="<c:url value='/js/Text.js' />"></script>
</body>
</html>
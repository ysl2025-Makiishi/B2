<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>検索</title>
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

    h2 {
      text-align: center;
    }

    .search-form {
      margin: 0 auto;
      width: 450px;
    }

    .search-form label {
      display: flex;
      align-items: center;
      margin: 15px 0;
      font-size: 18px;
      font-weight: bold;
    }

    .label-text {
      min-width: 100px;
      display: inline-block;
    }

    .search-form input[type="text"] {
      padding: 6px;
      font-size: 16px;
      width: 250px;
      margin-right: 10px;
    }

    .error {
      color: red;
      font-size: 13px;
      white-space: nowrap;
    }

    .buttons {
      margin-top: 20px;
      text-align: center;
    }

    input[type="submit"],
    input[type="reset"] {
      padding: 8px 20px;
      font-size: 16px;
      margin: 0 10px;
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

    #footer {
      margin-top: 50px;
      text-align: center;
      font-size: 14px;
      color: #999;
    }
  </style>
</head>
<body>
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

    <h2>検索（入力した項目を含む生徒を検索します）</h2>

    <div class="search-form">
      <form action="SearchServlet" method="post">
        <label>
          <span class="label-text">氏名：</span>
          <input type="text" name="name" />
        </label>

        <label>
          <span class="label-text">ふりがな：</span>
          <input type="text" name="hurigana" id="huriganaInput" />
          <span id="huriganaError" class="error"></span>
        </label>

        <label>
          <span class="label-text">学校名：</span>
          <input type="text" name="school" />
        </label>

        <div class="buttons">
          <input type="submit" value="検索" />
          <input type="reset" value="リセット" />
        </div>
      </form>
    </div>
  </div>

  <script>
    const huriganaInput = document.getElementById('huriganaInput');
    const huriganaError = document.getElementById('huriganaError');

    huriganaInput.addEventListener('input', () => {
      const original = huriganaInput.value;
      const filtered = original.replace(/[^\u3041-\u3096ー\s]/g, '');

      if (original !== filtered) {
        huriganaError.textContent = 'ひらがなで入力してください';
      } else {
        huriganaError.textContent = '';
      }

      huriganaInput.value = filtered;
    });
  </script>
</body>
</html>

<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>検索</title>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    text-align: center;
    margin: 0 auto;
    width: 400px;
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
      display: none; /* 初期状態では非表示 */
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
  <div class="wrapper">
    <!-- ロゴ -->
    <h1 id="logo">
      <div style="text-align: center;">
       <div style="text-align: center;">
 <a href="<%= request.getContextPath() %>/home">
  <img src="<%= request.getContextPath() %>/img/K-Manage_logo.png" width="200" height="200" alt="K-Manage">
</a>
</div>
       
      </div>
    </h1>

    <!-- ナビゲーション -->
    <ul id="nav">
      <li><a href="<%= request.getContextPath() %>/home">
      ホーム</a></li>
      <li><a href="#">生徒一覧</a></li>
      <li><a href="#">登録</a></li>
      <li><a href="#">検索</a></li>
      <li><a href="#">宿題提案</a></li>
      <li><a href="#">ログアウト</a></li>
    </ul>

    <!-- 検索タイトル -->
<h2>検索（入力した項目を含む生徒を検索します）</h2>

<div class="search-form">
  <form action="SearchServlet" method="post">
    <label>氏名：<input type="text" name="name" /></label><br>
    <label>ふりがな：<input type="text" name="hurigana" /></label><br>
    <label>学校名：<input type="text" name="school" /></label><br>
    <input type="submit" value="検索" />
    <input type="reset" value="リセット" />
  </form>
</div>

  </div> <!-- wrapper終わり -->
</body>

</html>

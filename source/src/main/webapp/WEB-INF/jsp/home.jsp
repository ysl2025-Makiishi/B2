<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ホーム | K-manage</title>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
           <link rel="stylesheet" href="<c:url value='/css/K-style.css' />">
</head>

<body id="home">
    <div class="wrapper">
        <!-- ヘッダー（ここから） -->
        <h1 id="logo">
            <div style="text-align: center;">
                <div style="text-align: center;">
  <a href="<%= request.getContextPath() %>/home">
  <img src="<%= request.getContextPath() %>/img/K-Manage_logo.png" width="200" height="200" alt="K-Manage">
</a>
</div>
                
            </div>
            <!-- <p>K-Manage</p> -->
        </h1>
        <ul id="nav">
            <li><a href="<%= request.getContextPath() %>/home">
            ホーム</a></li>
            <!-- <li><a href="home.html"><img src="images/a.jpg" width="100" height="100" alt="K-Manage"></a></li> -->
            <li><a href="">生徒一覧</a></li>
            <li><a href="">登録</a></li>
            <li><a href="">検索</a></li>
            <li><a href="">宿題提案</a></li>
            <li><a href="">ログアウト</a></li>
        </ul>
        <!-- ヘッダー（ここまで） -->
        <!-- メイン（ここから） -->
        <h2>ホーム</h2>
        <h2>ヘルプ</h2>
        <p>
            ここに書く
        </p>
        <ul>
        </ul>
        <div style="text-align: center;">

        </div>
        <!-- メイン（ここまで） -->
        <!-- フッター（ここから） -->
        <div id="footer">
            <p>&copy;Copyright wataamekun. All rights reserved.</p>
        </div>
        <!-- フッター（ここまで） -->
    </div>
</body>

</html>
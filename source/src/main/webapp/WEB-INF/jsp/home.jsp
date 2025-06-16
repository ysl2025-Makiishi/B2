<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>ホーム | K-manage</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/K-style.css' />">
</head>

<body id="home">
    <div class="wrapper">
        <!-- ヘッダー（ここから） -->
        <header class="header">
        <h1 id="logo">
            <a href="HomeServlet">
                <img src="img/K-Manage_logo.png" alt="名刺管理">
            </a>
        </h1>
    </header>
        <ul id="nav">
            <li><a href="HomeServlet">ホーム</a></li>
            <li><a href="/webapp/HomeServlet">生徒一覧</a></li>
            <li><a href="/webapp/HomeServlet">登録</a></li>
            <li><a href="/webapp/HomeServlet">検索</a></li>
            <li><a href="/webapp/HomeServlet">ログアウト</a></li>
        </ul>
        <!-- ヘッダー（ここまで） -->

        <!-- メイン（ここから） -->
        <h2>ホーム</h2>
        <h2>ヘルプ</h2>
        <p>
            ここに書く
        </p>
        <!-- メイン（ここまで） -->

        <!-- フッター（ここから） -->
        <div id="footer">
            <p>&copy;Copyright wataamekun. All rights reserved.</p>
        </div>
        <!-- フッター（ここまで） -->
    </div>
</body>

</html>

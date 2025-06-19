<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>宿題提案</title>
    <link rel="stylesheet" href="<c:url value='/css/K-style.css' />">

    <style>
        body {
            font-family: Arial, sans-serif;
            padding: 20px;
        }
        input, button {
            margin-top: 10px;
        }
    </style>
</head>

<body id="home">
    <div class="wrapper">
        <!-- ヘッダー（ここから） -->
        <h1 id="logo">
            <a href="<c:url value='/HomeServlet' />">
                <img src="<c:url value='/img/K-Manage_logo.png' />"  alt="K-Manage">
            </a>
        </h1>
           <ul id="nav">
            <li><a href="<c:url value='/HomeServlet' />">ホーム</a></li>
            <li><a href="<c:url value='/StudentListServlet' />">生徒一覧</a></li>
            <li><a href="<c:url value='/RegistServlet' />">登録</a></li>
            <li><a href="<c:url value='/SearachServlet' />">検索</a></li>
            <li><a href="<c:url value='/LogoutServlet' />"
					onclick="return confirm('本当に実行しますか？');">ログアウト</a></li>
        </ul>
        <!-- ヘッダー（ここまで） -->

        <h1>宿題提案</h1>

        <label for="nextDate">次に塾に行く日を選んでください</label><br>
        <input type="date" id="nextDate"><br>

        <label for="totalPages">次に塾に行く日までに終わらせたいテキストページ数を入力してください</label><br>
        <input type="number" id="totalPages" min="1"><br>

        <button onclick="calculateHomework()">検索</button>
        <button type="button" onclick="resetForm()">リセット</button>

        <h2 id="result"></h2>
        
        <button id="registerBtn" type="button" style="display: none;">登録</button>

        
    </div>

    <!-- 外部JavaScriptファイルの読み込み -->
    <script src="/B2/js/Homework.js"></script>
</body>
</html>

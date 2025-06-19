<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>ホーム | K-manage</title>
<link rel="stylesheet" href="<c:url value='/css/K-style.css' />">
</head>
<body id="home">
	<div class="wrapper">
		<!-- ヘッダー（ここから） -->
		<header class="header">
			<h1 id="logo">
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
		</header>
		<!-- ヘッダー（ここまで） -->

		<!-- メイン（ここから） -->
		<h2>ホーム</h2>
		<h2>ヘルプ</h2>
		<p>ここに書く</p>

		<div style="text-align: center;">
			<!-- 必要ならコンテンツをここに -->
		</div>
		<!-- メイン（ここまで） -->

		<!-- フッター（ここから） -->
		<div id="footer">
			<p>&copy; Copyright wataamekun. All rights reserved.</p>
		</div>
		<!-- フッター（ここまで） -->
	</div>
</body>
</html>

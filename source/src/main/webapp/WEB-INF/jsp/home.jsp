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
		<h2 class="help-title">❔　ヘルプ　❔</h2>
		<div class="help-content">
		  <h3>✨ このアプリでできること</h3>
		  <ul>
		    <li>性格診断で個別最適な学習を実現  
		      <br>→ 生徒は8問の簡単なアンケートに答えることで、4タイプの性格<br>
		      　（開放性・勤勉性・協調性・神経性）に分類されます。</li>
		    <li>性格に合った教材を自動で選出  
		      <br>→ 性格診断の結果に応じて最適なテキストが提案されます。</li>
		    <li>理解度に応じたスケジュールを自動作成  
		      <br>→ 理解度は10段階評価で、生徒にあった適切な1日に進める<br>
		      　テキストページ数を提案します。</li>
		    <li>宿題の分量を自動作成  
		      <br>→ 次に塾に行く日を逆算して1日に進める宿題のページ数を提案します。</li>
		  </ul>
		  
		  <h3>📋 生徒情報を確認したいとき</h3>
		  <p>ホーム画面の「生徒一覧」から対象の生徒を選んでください。<br>
		  個人情報、成績、模試結果、学習計画などが確認できます。</p>
		
		  <h3>✏️ 情報を編集・追加したいとき</h3>
		  <p>個人結果ページで各項目を修正できます。<br>
		  入力後は必ず「更新」してください。</p>
		
		  <h3>📊 模試の成績を登録したいとき</h3>
		  <p>「個人結果」の模試結果新規登録から新しい模試を追加できます。<br>
		  模試名・実施日・教科・点数・偏差値・平均を入力してください。</p>
		
		  <h3>📘 学習計画を見直したいとき</h3>
		  <p>「科目ごと結果」の学習計画では生徒に合ったテキストや<br>
		  宿題の進捗管理ができます。<br>
		  理解度に応じて内容の調整も可能です。</p>
		
		</div>
		

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

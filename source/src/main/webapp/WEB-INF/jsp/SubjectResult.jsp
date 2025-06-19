<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8" />
<title>${subject}の結果|K-Manage</title>
<link rel="stylesheet" href="<c:url value='/css/K-style.css' />">
<link rel="stylesheet" href="<c:url value='/css/SubjectResult.css' />">
</head>

<body>
	<div class="wrapper">
		<!-- ヘッダー -->
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

		<h2>${subject}の結果</h2>

		<a
			href="<c:url value='/IndividualResultsServlet?studentId=${student.id}' />"
			class="diag-link-button">個人結果に戻る</a>

		<ul class="subject-nav">
			<c:forEach var="subj" items="${['国語','数学','理科','社会','英語','総合']}">
				<li><a
					href="<c:url value='/SubjectResultServlet?studentId=${student.id}&subject=${subj}' />">${subj}</a></li>
			</c:forEach>
		</ul>

		<form method="post" action="<c:url value='/SubjectResultServlet' />">

			<!-- 基本情報 -->
			<section class="centered">
				<h3>基本情報</h3>
				<div class="flex-row">
					<div class="field">
						<label for="name">氏名</label> <input type="text" id="name"
							name="name" value="${student.name}" readonly />
					</div>
					<div class="field">
						<label for="kana">ふりがな</label> <input type="text" id="kana"
							name="kana" value="${student.furigana}" readonly />
					</div>
				</div>
				<div class="section-btn-row">
					<button type="button" class="edit-btn" data-target="basic">編集</button>
				</div>
			</section>

			<!-- 学習計画 -->
			<section class="centered">
				<h3>学習計画</h3>
				<div class="flex-row">
					<div class="field">
						<label for="understanding">理解度</label> <input type="text"
							id="understanding" name="understanding"
							value="${student.understanding}" readonly />
					</div>
					<div class="section-btn-row">
						<button type="button" class="edit-btn" data-target="plan">編集</button>
					</div>
					<div class="field">
						<label for="text_selection">テキスト選出</label> <input type="text"
							id="text_selection" name="text_selection"
							value="${student.textSelection}" readonly /> <a
							href="<c:url value='/TextServlet' />" target="_blank"
							class="diag-link-button">テキスト選出はこちら</a>
					</div>
					<div class="field">
						<label for="schedule">スケジュール作成</label> <input type="text"
							id="schedule" name="schedule" value="${student.schedule}"
							readonly /> <a href="<c:url value='/ScheduleServlet' />"
							target="_blank" class="diag-link-button">スケジュール作成はこちら</a>
					</div>
					<div class="field">
						<label for="homework_pages">宿題ページ数</label> <input type="text"
							id="homework_pages" name="homework_pages"
							value="${student.homeworkPages}" readonly /> <a
							href="<c:url value='/HomeworkServlet' />" target="_blank"
							class="diag-link-button">宿題提案はこちら</a>
					</div>
				</div>

			</section>


			<!-- メモ欄 -->
			<section class="centered">
				<h3>前回やったこと</h3>
				<textarea name="lastContent" class="large-textbox"></textarea>
				<button type="button" class="edit-btn">編集</button>
			</section>
			<section class="centered">
				<h3>次回やること</h3>
				<textarea name="nextContent" class="large-textbox"></textarea>
				<button type="button" class="edit-btn">編集</button>
			</section>
			<section class="centered">
				<h3>宿題内容</h3>
				<textarea name="homework" class="large-textbox"></textarea>
				<button type="button" class="edit-btn">編集</button>
			</section>
			<section class="centered">
				<h3>備考</h3>
				<textarea name="note" class="large-textbox"></textarea>
				<button type="button" class="edit-btn">編集</button>
			</section>

			<!-- 模試結果 -->
			<section class="centered">
				<h3>${subject}の模試結果一覧</h3>
				<table>
					<thead>
						<tr>
							<th>模試名</th>
							<th>実施日</th>
							<th>点数</th>
							<th>偏差値</th>
							<th>平均</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="exam" items="${examResults}">
							<tr>
								<td>${exam.examName}</td>
								<td><fmt:formatDate value="${exam.examDate}"
										pattern="yyyy-MM-dd" /></td>
								<td>${exam.score}</td>
								<td>${exam.deviationValue}</td>
								<td>${exam.averageScore}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</section>

			<!-- グラフ -->
			<section class="centered">
				<h3>点数・平均</h3>
				<div class="chart-wrapper">
					<canvas id="scoreAvgChart" width="320" height="320"></canvas>
				</div>
			</section>

			<section class="centered">
				<h3>偏差値</h3>
				<div class="chart-wrapper">
					<canvas id="deviationChart" width="320" height="320"></canvas>
				</div>
			</section>
		</form>

		<a
			href="<c:url value='/IndividualResultsServlet?studentId=${student.id}' />"
			class="diag-link-button">個人結果に戻る</a>
	</div>

	<script src="<c:url value='/js/SubjectResultServlet.js' />"></script>
	<script src="<c:url value='/js/IndividualResults.js' />"></script>
</body>
</html>

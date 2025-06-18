<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8" />
<title>?の結果 | K-Manage</title>
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
			<li><a href="/B2/LoginServlet"
				onclick="return confirm('本当に実行しますか？');">ログアウト</a></li>
		</ul>

		<h2>?の結果</h2>

		<a href="<c:url value='/IndividualResultsServlet' />" rel="noopener"
			class="diag-link-button">個人結果に戻る</a>

		<ul class="subject-nav">
			<li><a href="<c:url value='/SubjectResultServlet' />">国語</a></li>
			<li><a href="<c:url value='/SubjectResultServlet' />">数学</a></li>
			<li><a href="<c:url value='/SubjectResultServlet' />">理科</a></li>
			<li><a href="<c:url value='/SubjectResultServlet' />">社会</a></li>
			<li><a href="<c:url value='/SubjectResultServlet' />">英語</a></li>
		</ul>

		<form method="post" action="<c:url value='/SubjectResultServlet' />">

			<!-- 基本情報 -->
			<section class="centered">
				<h3>基本情報</h3>
				<div class="flex-row">
					<div class="field">
						<label class="main-label" for="name">氏名</label> <input type="text"
							id="name" name="name" />
					</div>
					<div class="field">
						<label class="sub-label" for="kana">ふりがな（ひらがなのみ）</label> <input
							type="text" id="kana" name="kana" class="kana-only" />
					</div>
				</div>
			</section>


			<!-- 学習計画 -->
			<section class="centered">
				<h3>学習計画</h3>

				<!-- 理解度（編集ボタンを左に） -->
				<div class="flex-row">
					
					<div class="field">
						<label for="understanding">理解度</label> <input id="understanding"
							name="understanding" type="text" />
					</div>
					<div class="section-btn-row">
						<button type="button" id="planEdit" class="edit-btn">編集</button>
					</div>
				</div>

				<!-- テキスト選出、スケジュール作成、宿題ページ数 -->
				<div class="flex-row">
					<div class="field">
						<label for="text_selection">テキスト選出</label> <input
							id="text_selection" name="text_selection" type="text" /> <a
							href="<c:url value='/TextServlet' />" target="_blank"
							rel="noopener" class="diag-link-button">テキスト選出はこちら</a>
					</div>

					<div class="field">
						<label for="schedule">スケジュール作成</label> <input id="schedule"
							name="schedule" type="text" /> <a
							href="<c:url value='/ScheduleServlet' />" target="_blank"
							rel="noopener" class="diag-link-button">スケジュール作成はこちら</a>
					</div>

					<div class="field">
						<label for="homework_pages">宿題ページ数</label> <input
							id="homework_pages" name="homework_pages" type="text" /> <a
							href="<c:url value='/HomeworkServlet' />" target="_blank"
							rel="noopener" class="diag-link-button">宿題提案はこちら</a>
					</div>
				</div>
			</section>



			<!-- 以下そのまま -->
			<section class="centered">
				<h3>前回やったこと</h3>
				<textarea name="lastContent" class="large-textbox"
					placeholder="前回の内容を記入してください"></textarea>
				<button type="button" class="edit-btn">編集</button>
			</section>

			<section class="centered">
				<h3>次回やること</h3>
				<textarea name="nextContent" class="large-textbox"
					placeholder="次回の予定内容を記入してください"></textarea>
				<button type="button" class="edit-btn">編集</button>
			</section>

			<section class="centered">
				<h3>宿題内容</h3>
				<textarea name="homework" class="large-textbox"
					placeholder="宿題内容を記入してください"></textarea>
				<button type="button" class="edit-btn">編集</button>
			</section>

			<section class="centered">
				<h3>備考</h3>
				<textarea name="note" class="large-textbox"
					placeholder="備考があれば記入してください"></textarea>
				<button type="button" class="edit-btn">編集</button>
			</section>

			<section class="centered">
				<h3>模試結果一覧</h3>
				<table>
					<thead>
						<tr>
							<th>模試名</th>
							<th>実施日</th>
							<th>教科</th>
							<th>点数</th>
							<th>偏差値</th>
							<th>平均</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><input name="exam_name[]" type="text" /></td>
							<td><input name="exam_date[]" type="date" /></td>
							<td><select name="exam_subject[]">
									<option value="国語">国語</option>
									<option value="数学">数学</option>
									<option value="英語">英語</option>
									<option value="理科">理科</option>
									<option value="社会">社会</option>
									<option value="総合">総合</option>
							</select></td>

							<td><input name="exam_score[]" min="0" max="100" step="1"
								type="number" /></td>
							<td><input name="exam_dev[]" step="0.1" min="0"
								type="number" /></td>
							<td><input name="exam_avg[]" step="0.1" min="0" max="100"
								type="number" /></td>

							<td><button type="button" class="edit-btn small-btn">編集</button></td>
							<td><button type="button" class="edit-btn small-btn">削除</button></td>
						</tr>
					</tbody>
				</table>
			</section>

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

		<a href="<c:url value='/IndividualResultsServlet' />" rel="noopener"
			class="diag-link-button">個人結果に戻る</a>
	</div>

	<script src="<c:url value='/js/SubjectResultServlet.js' />"></script>
	<script src="<c:url value='/js/IndividualResults.js' />"></script>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.time.LocalDate"%>
<%
LocalDate today = LocalDate.now(); // 今日の日付を取得
%>
<!DOCTYPE html>
<html lang="ja">

<head>
<meta charset="UTF-8" />
<title>個人結果 | K-Manage</title>
<!-- スタイルは /css フォルダーから読み込み -->
<link rel="stylesheet" href="<c:url value='/css/K-style.css' />">
<link rel="stylesheet"
	href="<c:url value='/css/IndividualResults.css' />" />
</head>

<body>
	<div class="wrapper">
		<!-- ヘッダー（ここから） -->
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
		<!-- ヘッダー（ここまで） -->
		<h2>個人結果</h2>

		<a href="<c:url value='/SearchResultServlet' />" rel="noopener"
			class="diag-link-button">検索結果に戻る</a>

		<ul class="subject-nav">
			<li><a href="<c:url value='/SubjectResultServlet' />">国語</a></li>
			<li><a href="<c:url value='/SubjectResultServlet' />">数学</a></li>
			<li><a href="<c:url value='/SubjectResultServlet' />">理科</a></li>
			<li><a href="<c:url value='/SubjectResultServlet' />">社会</a></li>
			<li><a href="<c:url value='/SubjectResultServlet' />">英語</a></li>
		</ul>

		<form id="studentForm" method="post"
			action="<c:url value='/IndividualResultsServlet' />">
			<!-- ========== 基本情報 ========== -->
			<!-- ========== 基本情報 ========== -->
			<section class="centered">
				<h3>基本情報</h3>

				<!-- 1段目 -->
				<div class="flex-row">
					<div class="field">
						<label for="name">氏名</label> <input id="name" name="name"
							type="text" />
					</div>

					<div class="field">
						<label for="kana">ふりがな（ひらがなのみ）</label> <input id="kana"
							name="kana" type="text" class="kana-only" />
					</div>

					<div class="field">
						<label for="gender">性別</label> <select id="gender" name="gender">
							<option value="">選択してください</option>
							<option value="M">男性</option>
							<option value="F">女性</option>
							<option value="O">その他</option>
						</select>
					</div>
				</div>

				<!-- 2段目 -->
				<div class="flex-row">
					<div class="field">
						<label for="personality">性格</label> <input id="personality"
							name="personality" type="text" />
					</div>
					<div class="field">
						<label for="birthday">生年月日</label> <input id="birthday"
							name="birthday" type="date" max="<%=today.toString()%>" />
					</div>



					<div class="field">
						<label for="school">学校名</label> <input id="school" name="school"
							type="text" />
					</div>
				</div>

				<div class="section-btn-row">
					<button type="button" id="basicEdit" class="edit-btn">編集</button>
					<a href="<c:url value='/PersonalityServlet' />" target="_blank"
						rel="noopener" class="diag-link-button">性格診断はこちら</a>
				</div>
			</section>



			<!-- ========== 志望校 ========== -->
			<section class="centered">
				<h3>志望校</h3>
				<div class="flex-row">
					<div class="field">
						<label for="asp1">志望校 1</label><input id="asp1" name="asp1"
							type="text" />
					</div>
					<div class="field">
						<label for="asp2">志望校 2</label><input id="asp2" name="asp2"
							type="text" />
					</div>
					<div class="field">
						<label for="asp3">志望校 3</label><input id="asp3" name="asp3"
							type="text" />
					</div>
				</div>

				<div class="section-btn-row">
					<button type="button" id="aspirationEdit" class="edit-btn">編集</button>
				</div>
			</section>

			<!-- ========== GPA 一覧（9 教科）＋ 内申点 ========== -->
			<section>
				<h3>GPA 一覧（9 教科）</h3>

				<div class="gpa-list">
					<div class="gpa-item">
						<label for="gpa_jp">国語</label><input id="gpa_jp" min="1" max="5"
							step="1" type="number" />
					</div>
					<div class="gpa-item">
						<label for="gpa_ss">社会</label><input id="gpa_ss" min="1" max="5"
							step="1" type="number" />
					</div>
					<div class="gpa-item">
						<label for="gpa_ma">数学</label><input id="gpa_ma" min="1" max="5"
							step="1" type="number" />
					</div>
					<div class="gpa-item">
						<label for="gpa_sc">理科</label><input id="gpa_sc" min="1" max="5"
							step="1" type="number" />
					</div>
					<div class="gpa-item">
						<label for="gpa_en">英語</label><input id="gpa_en" min="1" max="5"
							step="1" type="number" />
					</div>
					<div class="gpa-item">
						<label for="gpa_mu">音楽</label><input id="gpa_mu" min="1" max="5"
							step="1" type="number" />
					</div>
					<div class="gpa-item">
						<label for="gpa_ar">美術</label><input id="gpa_ar" min="1" max="5"
							step="1" type="number" />
					</div>
					<div class="gpa-item">
						<label for="gpa_pe">保健体育</label><input id="gpa_pe" min="1" max="5"
							step="1" type="number" />
					</div>
					<div class="gpa-item">
						<label for="gpa_te">技術家庭</label><input id="gpa_te" min="1" max="5"
							step="1" type="number" />
					</div>
				</div>


				<!-- 内申点を中央寄せで同じ枠内に配置 -->
				<div class="flex-row center-row">
					<div class="field naishin-field">
						<label for="naishin">内申点</label> <input id="naishin"
							name="naishin" readonly type="number" />
					</div>
				</div>


				<div class="section-btn-row">
					<button type="button" id="gpaEdit" class="edit-btn">編集</button>
				</div>
			</section>

			<!-- ========== 模試結果（一覧入力） ========== -->
			<section>
				<h3>模試結果新規登録</h3>
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

						</tr>
					</tbody>
				</table>

				<div class="button-row">
					<button type="submit" id="examRegisterBtn">登録</button>
					<button type="reset">リセット</button>
				</div>
			</section>

			<!-- ========== 最新の模試結果 ========== -->
			<section class="centered">
				<h3>最新の模試結果</h3>

				<div class="latest-result-row">
					<div class="latest-info">
						<span>日付：<input type="date" id="latestDate"
							name="latestDate" /></span> <span>模試名：<input type="text"
							id="latestExamName" name="latestExamName" placeholder="例：第3回全統模試" /></span>
						<span>偏差値：<input type="number" id="latestDev"
							name="latestDev" step="0.1" min="0" /></span>
					</div>
					<button type="button" id="overallBtn" class="overall-btn">総合結果</button>
				</div>

				<h4>主教科 5 科目（点数・平均・偏差値）</h4>
				<div class="major-flex">
					<table class="major-table compact">
						<thead>
							<tr>
								<th>教科</th>
								<th>点数</th>
								<th>平均</th>
								<th>偏差値</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><span class="subject-label">国語</span></td>
								<td><input name="jpn_score" min="0" max="100" type="number" /></td>
								<td><input name="jpn_avg" step="0.1" min="0" type="number" /></td>
								<td><input name="jpn_dev" step="0.1" min="0" type="number" /></td>
							</tr>
							<tr>
								<td><span class="subject-label">数学</span></td>
								<td><input name="math_score" min="0" max="100"
									type="number" /></td>
								<td><input name="math_avg" step="0.1" min="0" type="number" /></td>
								<td><input name="math_dev" step="0.1" min="0" type="number" /></td>
							</tr>
							<tr>
								<td><span class="subject-label">英語</span></td>
								<td><input name="eng_score" min="0" max="100" type="number" /></td>
								<td><input name="eng_avg" step="0.1" min="0" type="number" /></td>
								<td><input name="eng_dev" step="0.1" min="0" type="number" /></td>
							</tr>
							<tr>
								<td><span class="subject-label">理科</span></td>
								<td><input name="sci_score" min="0" max="100" type="number" /></td>
								<td><input name="sci_avg" step="0.1" min="0" type="number" /></td>
								<td><input name="sci_dev" step="0.1" min="0" type="number" /></td>
							</tr>
							<tr>
								<td><span class="subject-label">社会</span></td>
								<td><input name="soc_score" min="0" max="100" type="number" /></td>
								<td><input name="soc_avg" step="0.1" min="0" type="number" /></td>
								<td><input name="soc_dev" step="0.1" min="0" type="number" /></td>
							</tr>
						</tbody>
					</table>


					<div class="chart-wrapper major-chart">
						<canvas id="majorRadar" width="320" height="320"></canvas>
					</div>
				</div>
			</section>
		</form>
		<a href="<c:url value='/SearchResultServlet' />" rel="noopener"
			class="diag-link-button">検索結果に戻る</a>
	</div>
	<script src="<c:url value='/js/IndividualResults.js' />"></script>
</body>

</html>

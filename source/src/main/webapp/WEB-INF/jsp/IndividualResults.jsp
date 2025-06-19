<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="java.time.LocalDate"%>
<%
LocalDate today = LocalDate.now();
%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8" />
<title>個人結果 | K-Manage</title>
<link rel="stylesheet" href="<c:url value='/css/K-style.css' />" />
<link rel="stylesheet"
	href="<c:url value='/css/IndividualResults.css' />" />
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

		<h2>個人結果</h2>
		<a href="<c:url value='/SearchResultServlet' />"
			class="diag-link-button">検索結果に戻る</a>

		<ul class="subject-nav">
			<li><a
				href="<c:url value='/SubjectResultServlet?id=${student.id}&subject=国語' />">国語</a></li>
			<li><a
				href="<c:url value='/SubjectResultServlet?id=${student.id}&subject=数学' />">数学</a></li>
			<li><a
				href="<c:url value='/SubjectResultServlet?id=${student.id}&subject=理科' />">理科</a></li>
			<li><a
				href="<c:url value='/SubjectResultServlet?id=${student.id}&subject=社会' />">社会</a></li>
			<li><a
				href="<c:url value='/SubjectResultServlet?id=${student.id}&subject=英語' />">英語</a></li>
			<li><a
				href="<c:url value='/SubjectResultServlet?id=${student.id}&subject=総合' />">総合</a></li>
		</ul>



		<form id="studentForm" method="post"
			action="<c:url value='/IndividualResultsServlet' />">
			<input type="hidden" name="id" value="${student.id}" />

			<!-- 基本情報 -->
			<section class="centered">
				<h3>基本情報</h3>
				<div class="flex-row">
					<div class="field">
						<label>氏名</label><input type="text" name="name"
							value="${student.name}" />
					</div>
					<div class="field">
						<label>ふりがな</label><input type="text" name="furigana"
							value="${student.furigana}" class="kana-only" />
					</div>
					<div class="field">
						<label>性別</label> <select name="gender">
							<option value="M" ${student.gender == 'M' ? 'selected' : ''}>男性</option>
							<option value="F" ${student.gender == 'F' ? 'selected' : ''}>女性</option>
							<option value="O" ${student.gender == 'O' ? 'selected' : ''}>その他</option>
						</select>
					</div>
				</div>
				<div class="flex-row">
					<div class="field">
						<label>性格</label><span class="personality-display">${student.personalityName}</span>
						<a href="<c:url value='/PersonalityServlet' />" target="_blank" class="diag-link-button">性格診断はこちら</a>
					</div>
					<div class="field">
						<label>生年月日</label><input type="date" name="birthday"
							value="${student.birthday}" max="<%= today.toString() %>" />
					</div>
					<div class="field">
						<label>学校名</label><input type="text" name="school"
							value="${student.schoolName}" />
					</div>
				</div>
			</section>

			<!-- 志望校 -->
			<section class="centered">
				<h3>志望校</h3>
				<div class="flex-row">
					<div class="field">
						<label>志望校 1</label><input type="text" name="asp1"
							value="${student.aspirationSchool1Name}" />
					</div>
					<div class="field">
						<label>志望校 2</label><input type="text" name="asp2"
							value="${student.aspirationSchool2Name}" />
					</div>
					<div class="field">
						<label>志望校 3</label><input type="text" name="asp3"
							value="${student.aspirationSchool3Name}" />
					</div>
				</div>
			</section>

			<!-- GPA一覧 -->
			<section>
				<h3>GPA 一覧（9 教科）</h3>
				<div class="gpa-list">
					<div class="gpa-item">
						<label for="gpa_jp">国語</label><input type="text" id="gpa_jp"
							name="gpa_jp" value="${student.gpaJp}" class="gpa-input" />
					</div>
					<div class="gpa-item">
						<label for="gpa_ss">社会</label><input type="text" id="gpa_ss"
							name="gpa_ss" value="${student.gpaSs}" class="gpa-input" />
					</div>
					<div class="gpa-item">
						<label for="gpa_ma">数学</label><input type="text" id="gpa_ma"
							name="gpa_ma" value="${student.gpaMa}" class="gpa-input" />
					</div>
					<div class="gpa-item">
						<label for="gpa_sc">理科</label><input type="text" id="gpa_sc"
							name="gpa_sc" value="${student.gpaSc}" class="gpa-input" />
					</div>
					<div class="gpa-item">
						<label for="gpa_en">英語</label><input type="text" id="gpa_en"
							name="gpa_en" value="${student.gpaEn}" class="gpa-input" />
					</div>
					<div class="gpa-item">
						<label for="gpa_mu">音楽</label><input type="text" id="gpa_mu"
							name="gpa_mu" value="${student.gpaMu}" class="gpa-input" />
					</div>
					<div class="gpa-item">
						<label for="gpa_ar">美術</label><input type="text" id="gpa_ar"
							name="gpa_ar" value="${student.gpaAr}" class="gpa-input" />
					</div>
					<div class="gpa-item">
						<label for="gpa_pe">保健体育</label><input type="text" id="gpa_pe"
							name="gpa_pe" value="${student.gpaPe}" class="gpa-input" />
					</div>
					<div class="gpa-item">
						<label for="gpa_te">技術家庭</label><input type="text" id="gpa_te"
							name="gpa_te" value="${student.gpaTe}" class="gpa-input" />
					</div>
				</div>

				<div class="flex-row center-row">
					<div class="field naishin-field">
						<label for="naishin">内申点</label> <input id="naishin"
							name="naishin" readonly type="number" />
					</div>
				</div>
			</section>

			<!-- 保存 -->
			<div class="section-btn-row">
				<button type="submit" class="save-btn">保存</button>
			</div>

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



			
			<!-- 最新の模試結果 -->
			<section class="centered">
				<h3>最新の模試結果</h3>
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
						<c:forEach var="exam" items="${student.examResults}">
							<tr>
								<td>${exam.examName}</td>
								<td><fmt:formatDate value="${exam.examDate}"
										pattern="yyyy-MM-dd" /></td>
								<td>${exam.subjectName}</td>
								<td>${exam.score}</td>
								<td>${exam.deviationValue}</td>
								<td>${exam.averageScore}</td>
							</tr>
						</c:forEach>

					</tbody>
				</table>
			</section>
		</form>
	</div>

	<script src="<c:url value='/js/IndividualResults.js' />"></script>
</body>
</html>
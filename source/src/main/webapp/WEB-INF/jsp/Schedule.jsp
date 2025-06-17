<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>スケジュール作成 | K-manage</title>
  <link rel="stylesheet" href="<c:url value='/css/Schedule.css' />">
</head>
<body>
	<div class="wrapper">
        <!-- ヘッダー（ここから） -->
        <div id="logo" style="text-align: center;">
		  <h1>
		    <a href="<c:url value='/home.jsp' />">
		      <img src="<c:url value='/img/K-Manage_logo.png'/>" width="200" height="200" alt="K-Manage">
		    </a>
		  </h1>
		</div>
        <ul id="nav">
            <li><a href="home.html">ホーム</a></li>
            <!-- <li><a href="home.html"><img src="images/a.jpg" width="100" height="100" alt="K-Manage"></a></li> -->
            <li><a href="">生徒一覧</a></li>
            <li><a href="">登録</a></li>
            <li><a href="">検索</a></li>
            <!--<li><a href="">性格診断</a></li>
            <li><a href="">テキスト選出</a></li>
            <li><a href="">スケジュール作成</a></li>-->
            <li><a href="">宿題提案</a></li>
            <li><a href="">ログアウト</a></li>
        </ul>
        <!-- ヘッダー（ここまで） -->
        <!-- メイン（ここから） -->
        <h2>スケジュール作成</h2>
        <div id="form_and_register_wrapper">
  		<form id="schedule_form" action="ScheduleServlet" method="get">
            <table>
            <tr>
                <td>
                <label>目標日
                <input type="date" name="target_date">
                </label>
                </td>
            </tr>
            <tr>
                <td>
                <label>塾に行く頻度（週何回）
                    <select name="frequency">
                        <option value="">選択してください</option>
                        <option value="1">1回</option>
                        <option value="2">2回</option>
                        <option value="3">3回</option>
                        <option value="4">4回</option>
                        <option value="5">5回</option>
                        <option value="6">6回</option>
                        <option value="7">7回</option>
                    </select>
                </label>
                </td>
            </tr>
            <tr>
                <td>
                <label>生徒の理解度
                    <select name="level_of_understanding">
                        <option value="">選択してください</option>
                        <option value="1">1（全く理解できていない）</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10（よく理解できている）</option>
                    </select>
                </label>
                </td>
            </tr>
            <tr>
                <td>
                <label>進めるテキストぺージ数
                    <input type="number" name="page" min="1" step="1">
                </label>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="submit" name="search" value="検索">
                    <input type="reset" name="reset" value="リセット">
                    <span id="error_message"></span>
                <td>
            </tr>
            </table>
        </form>
        <!-- 最初は非表示の結果 -->
        <p id="result" style="display: none;">結果：　ページ進めよう！</p>

        <!-- 登録ボタン（非表示） -->
        <div id="register_container" style="display: none; margin-top: 10px;">
            <button type="button" onclick="alert('登録しました');">登録</button>
        </div>
        </div>

        <!-- 戻るボタン -->
        <div style="margin-top: 20px;">
            <a href="subject_result.html">
                <button type="button">科目ごと個人結果に戻る</button>
            </a>
        </div>
        <!-- メイン（ここまで） -->
        <!-- フッター（ここから） -->
        <!--<div id="footer">
            <p>&copy;Copyright wataamekun. All rights reserved.</p>
        </div>-->
        <!-- フッター（ここまで） -->
    </div>
    <script src="<c:url value='/js/Schedule.js' />"></script>
</body>
</html>
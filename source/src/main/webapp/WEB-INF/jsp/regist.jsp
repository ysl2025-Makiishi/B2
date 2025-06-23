<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>生徒登録フォーム</title>
  <link rel="stylesheet" type="text/css" href="<c:url value='/css/regist.css' />">
  <style>
  select, option {
    background-color: white;
    color: black;
  }
    body {
      font-family: sans-serif;
      padding: 40px;
      background-color: #b9e2ec;
    }
    h1 {
      font-size: 28px;
      margin-bottom: 20px;
    }
    form {
      background: #fff;
      padding: 20px;
      border-radius: 8px;
      max-width: 800px;
      margin: auto;
     /* box-shadow: 0 0 10px rgba(0,0,0,0.1);*/
    }
    .form-row {
      display: flex;
      flex-wrap: wrap;
      margin-bottom: 15px;
    }
    .form-group {
      flex: 1;
      min-width: 200px;
      margin-right: 20px;
    }
    /*label {
      display: block;
      font-weight: bold;
      margin-bottom: 5px;
    }*/
    input[type="text"], input[type="date"], input[type="number"] {
      width: 100%;
      padding: 8px;
      box-sizing: border-box;
    }
    .radio-group {
      display: flex;
      gap: 15px;
    }
    .buttons {
      margin-top: 20px;
    }
    button {
      padding: 10px 20px;
      margin-right: 10px;
    }
    .error-message {
      color: red;
      font-size: 14px;
    }
  </style>
</head>
    <body>
  <div class="wrapper">
    <!-- ヘッダー -->
    <div style="text-align: center;">
    <h1 id="logo" >
      
        <a href="<c:url value='/HomeServlet' />">
          <img src="<c:url value='/img/K-Manage_logo.png' />" alt="K-Manage">
        </a>
      

    </h1>


    <ul id="nav">
      <li><a href="<c:url value='/HomeServlet' />">ホーム</a></li>
      <li><a href="<c:url value='/StudentListServlet' />">生徒一覧</a></li>
      <li><a href="<c:url value='/RegistServlet' />">登録</a></li>
      <li><a href="<c:url value='/SearchServlet' />">検索</a></li>
      <!--  <li><a href="<c:url value='/LoginServlet' />">宿題提案</a></li> -->
      <li><a href="<c:url value='/LogoutServlet' />"
					onclick="return confirm('本当に実行しますか？');">ログアウト</a></li>
    </ul>


  <h2>登録</h2>

  <!-- ここに送り先サーブレット -->
  <form id="registerForm" method="POST" action="<c:url value='/RegistServlet'/>">
    
    <!-- 氏名・ふりがな -->
    <div class="form-row">
      <div class="form-group">
        <label for="name">氏名 <span style="color:red;">※</span></label>
        <input type="text" id="name" name="name" value="" required>
      </div>
      <div class="form-group">
        <label for="furigana">ふりがな <span style="color:red;">※</span></label>
        <input type="text" id="furigana" name="furigana" value="" onblur="validateHiragana(this)" required>
      </div>
    </div>

    <!-- 学校名・生年月日 -->
    <div class="form-row">
      <div class="form-group">
        <label for="school">学校名</label>
        <input type="text" id="school" name="school" value="">
      </div>
      <div class="form-group">
        <label for="birthday">生年月日 <span style="color:red;">※</span></label>
        <input type="date" id="birthday" name="birthday" value="${ student.birthday}" required>
      </div>
    </div>

    <!-- 性別・性格 -->
    <div class="form-row">
      <div class="form-group">
        <label>性別 <span style="color:red;">※</span></label>
        <div class="radio-group">
          <label><input type="radio" name="gender" value="男" required> 男性</label>
          <label><input type="radio" name="gender" value="女"> 女性</label>
          <label><input type="radio" name="gender" value="無回答"> 無回答</label>
        </div>
      </div>
      <div class="form-group">
        <label for="personality">性格</label>
        <input type="text" id="personality" name="personality" value="性格診断後に表示" disabled>
      </div>
    </div>

    <!-- 志望校 -->
    <div class="form-row">
      <div class="form-group">
        <label for="hope1">志望校 1</label>
        <input type="text" id="hope1" name="aspiration_school1" value="">
      </div>
      <div class="form-group">
        <label for="hope2">志望校 2</label>
        <input type="text" id="hope2" name="aspiration_school2" value="">
      </div>
      <div class="form-group">
        <label for="hope3">志望校 3</label>
        <input type="text" id="hope3" name="aspiration_school3" value="">
      </div>
    </div>

   <!-- 成績（9教科：3×3マス表示） -->
<div class="form-row" style="flex-direction: column;">
  <label style="font-weight: bold;">学校の成績（9教科）</label>
  <div style="display: grid; grid-template-columns: repeat(3, 1fr); gap: 15px; max-width: 600px;">
    <!-- 成績入力 1〜9 -->
    <!-- 1 -->
    <div>
 
<!--       <select name="subject1" style="width: 120px;">
  <option value="">科目</option>
  <option value="国語">国語</option>
  <option value="数学">数学</option>
  <option value="英語">英語</option>
  <option value="理科">理科</option>
  <option value="社会">社会</option>
  <option value="音楽">音楽</option>
  <option value="美術">美術</option>
  <option value="保健体育">保健体育</option>
  <option value="技術家庭">技術家庭</option>
</select>-->

国語&nbsp;&nbsp;<select name="gpa1" style="width: 60px; margin-top: 5px;">
  <option value="">成績</option>
  <option value="1">1</option>
  <option value="2">2</option>
  <option value="3">3</option>
  <option value="4">4</option>
  <option value="5">5</option>
</select>

    </div>

    <div>
<!--        <select name="subject2" style="width: 120px;"> 
         <option value="">科目</option>
  <option value="国語">国語</option>
  <option value="数学">数学</option>
  <option value="英語">英語</option>
  <option value="理科">理科</option>
  <option value="社会">社会</option>
  <option value="音楽">音楽</option>
  <option value="美術">美術</option>
  <option value="保健体育">保健体育</option>
  <option value="技術家庭">技術家庭</option>
     </select>-->
      数学&nbsp;&nbsp;<select name="gpa2" style="width: 60px; margin-top: 5px;">
         <option value="">成績</option>
  <option value="1">1</option>
  <option value="2">2</option>
  <option value="3">3</option>
  <option value="4">4</option>
  <option value="5">5</option>
      </select>
    </div>
    <div>
      <!--  <select name="subject3" style="width: 120px;">
         <option value="">科目</option>
  <option value="国語">国語</option>
  <option value="数学">数学</option>
  <option value="英語">英語</option>
  <option value="理科">理科</option>
  <option value="社会">社会</option>
  <option value="音楽">音楽</option>
  <option value="美術">美術</option>
  <option value="保健体育">保健体育</option>
  <option value="技術家庭">技術家庭</option>
      </select>-->
      英語&nbsp;&nbsp;<select name="gpa3" style="width: 60px; margin-top: 5px;">
          <option value="">成績</option>
  <option value="1">1</option>
  <option value="2">2</option>
  <option value="3">3</option>
  <option value="4">4</option>
  <option value="5">5</option>
      </select>
    </div>
    <div>
      <!--  <select name="subject4" style="width: 120px;">
         <option value="">科目</option>
  <option value="国語">国語</option>
  <option value="数学">数学</option>
  <option value="英語">英語</option>
  <option value="理科">理科</option>
  <option value="社会">社会</option>
  <option value="音楽">音楽</option>
  <option value="美術">美術</option>
  <option value="保健体育">保健体育</option>
  <option value="技術家庭">技術家庭</option>
      </select>-->
      理科&nbsp;&nbsp;<select name="gpa4" style="width: 60px; margin-top: 5px;"> 
         <option value="">成績</option>
  <option value="1">1</option>
  <option value="2">2</option>
  <option value="3">3</option>
  <option value="4">4</option>
  <option value="5">5</option>
      </select>
    </div>
    <div>
      <!--<select name="subject5" style="width: 120px;">
        <option value="">科目</option>
  <option value="国語">国語</option>
  <option value="数学">数学</option>
  <option value="英語">英語</option>
  <option value="理科">理科</option>
  <option value="社会">社会</option>
  <option value="音楽">音楽</option>
  <option value="美術">美術</option>
  <option value="保健体育">保健体育</option>
  <option value="技術家庭">技術家庭</option>
      </select>-->
      社会&nbsp;&nbsp;<select name="gpa5" style="width: 60px; margin-top: 5px;">
         <option value="">成績</option>
  <option value="1">1</option>
  <option value="2">2</option>
  <option value="3">3</option>
  <option value="4">4</option>
  <option value="5">5</option>
      </select>
    </div>
    <div>
      <!--<select name="subject6" style="width: 120px;">
        <option value="">科目</option>
  <option value="国語">国語</option>
  <option value="数学">数学</option>
  <option value="英語">英語</option>
  <option value="理科">理科</option>
  <option value="社会">社会</option>
  <option value="音楽">音楽</option>
  <option value="美術">美術</option>
  <option value="保健体育">保健体育</option>
  <option value="技術家庭">技術家庭</option>
      </select>-->
      体育&nbsp;&nbsp;<select name="gpa6" style="width: 60px; margin-top: 5px;">
         <option value="">成績</option>
  <option value="1">1</option>
  <option value="2">2</option>
  <option value="3">3</option>
  <option value="4">4</option>
  <option value="5">5</option>
      </select>
    </div>
    <div>
      <!--<select name="subject7" style="width: 120px;">
        <option value="">科目</option>
  <option value="国語">国語</option>
  <option value="数学">数学</option>
  <option value="英語">英語</option>
  <option value="理科">理科</option>
  <option value="社会">社会</option>
  <option value="音楽">音楽</option>
  <option value="美術">美術</option>
  <option value="保健体育">保健体育</option>
  <option value="技術家庭">技術家庭</option>
      </select>-->
      技家&nbsp;&nbsp;<select name="gpa7" style="width: 60px; margin-top: 5px;"> 
        <option value="">成績</option>
  <option value="1">1</option>
  <option value="2">2</option>
  <option value="3">3</option>
  <option value="4">4</option>
  <option value="5">5</option></select>
    </div>
    <div>
      <!--<select name="subject8" style="width: 120px;"><option value="">科目</option>
  <option value="国語">国語</option>
  <option value="数学">数学</option>
  <option value="英語">英語</option>
  <option value="理科">理科</option>
  <option value="社会">社会</option>
  <option value="音楽">音楽</option>
  <option value="美術">美術</option>
  <option value="保健体育">保健体育</option>
  <option value="技術家庭">技術家庭</option></select>-->
      美術&nbsp;&nbsp;<select name="gpa8" style="width: 60px; margin-top: 5px;">
         <option value="">成績</option>
  <option value="1">1</option>
  <option value="2">2</option>
  <option value="3">3</option>
  <option value="4">4</option>
  <option value="5">5</option>
      </select>
    </div>
    <div>
      <!--<select name="subject9" style="width: 120px;"><option value="">科目</option>
  <option value="国語">国語</option>
  <option value="数学">数学</option>
  <option value="英語">英語</option>
  <option value="理科">理科</option>
  <option value="社会">社会</option>
  <option value="音楽">音楽</option>
  <option value="美術">美術</option>
  <option value="保健体育">保健体育</option>
  <option value="技術家庭">技術家庭</option></select>-->
      音楽&nbsp;&nbsp;<select name="gpa9" style="width: 60px; margin-top: 5px;">
         <option value="">成績</option>
  <option value="1">1</option>
  <option value="2">2</option>
  <option value="3">3</option>
  <option value="4">4</option>
  <option value="5">5</option>
      </select>
    </div>
  </div>
</div> 


    <!-- ボタンとエラーメッセージ -->
    <div class="buttons">
      <button type="submit" value="登録">登録</button>
      <button type="reset">リセット</button>
      <span class="error-message" id="errorMessage"></span>
    </div>

  </form>
    </div>
    </div>
<script src="<c:url value='/js/regist.js' />"></script>
</body>
</html>

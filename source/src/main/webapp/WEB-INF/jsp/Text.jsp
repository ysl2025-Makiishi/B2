<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>テキスト選出システム | K-manage</title>
  <link rel="stylesheet" href="<c:url value='/css/K-style.css' />">
  <style>
    /* ページ全体のフォントやリセット */
    html, body {
      height: 100%;
      margin: 0;
      padding: 0;
      font-family: Arial, sans-serif;
    }

    body {
      display: flex;
      flex-direction: column;
      min-height: 100vh;
    }

    .wrapper {
      flex: 1;
      display: flex;
      flex-direction: column;
    }

    /* ロゴとナビは上部に固定 */
    #logo, #nav {
      flex-shrink: 0;
      text-align: center;
      margin-bottom: 20px;
    }

    #nav {
      list-style: none;
      padding: 0;
      display: flex;
      justify-content: center;
      gap: 15px;
      margin-bottom: 40px;
    }

    #nav li a {
      text-decoration: none;
      font-size: 16px;
      color: #1685e6;
    }

    /* 中央寄せ部分 */
    .content-center {
      flex: 1;
      display: flex;
      flex-direction: column;
      justify-content: center; /* 縦中央 */
      align-items: center;     /* 横中央 */
      text-align: center;
      padding: 20px;
    }

    /* フォーム内スタイル */
    label {
      display: block;
      margin: 15px 0 5px;
      font-size: 18px;
      font-weight: bold;
    }
    select {
      width: 300px;
      padding: 8px;
      font-size: 16px;
    }
    .buttons {
      margin: 20px 0;
    }
    button {
      padding: 10px 20px;
      font-size: 16px;
      margin-right: 10px;
    }
    .result {
      margin-top: 30px;
      font-size: 22px;
      font-weight: bold;
      color: #1685e6;
      text-align: center;
    }

  .search-form {
    text-align: center;
    margin: 0 auto;
    width: 400px;

    background: white !important;
    padding: 20px 30px !important;
    border-radius: 8px !important;
    box-shadow: 0 2px 8px rgba(0,0,0,0.1) !important;
    box-sizing: border-box !important;
  }
    
  </style>
</head>
<body>
  <div class="wrapper">
    <!-- ロゴ -->
    <h1 id="logo">
      <a href="<%= request.getContextPath() %>/home">
        <img src="<%= request.getContextPath() %>/img/K-Manage_logo.png" width="200" height="200" alt="K-Manage">
      </a>
    </h1>

    <!-- ナビゲーション -->
    <ul id="nav">
      <li><a href="<%= request.getContextPath() %>/home">ホーム</a></li>
      <li><a href="#">生徒一覧</a></li>
      <li><a href="#">登録</a></li>
      <li><a href="#">検索</a></li>
      <li><a href="#">宿題提案</a></li>
      <li><a href="#">ログアウト</a></li>
    </ul>

    <!-- 中央寄せコンテンツ -->
    <div class="content-center">
      <h2>テキスト選出</h2>

      <form id="textForm" action="<%= request.getContextPath() %>/TextServlet" method="post">
        <label for="subject">学習したい教科</label>
        <select id="subject" name="subject">
          <option value="">-- 教科を選んでください --</option>
          <option value="1">国語</option>
          <option value="2">数学</option>
          <option value="3">英語</option>
          <option value="4">理科</option>
          <option value="5">社会</option>
        </select>

        <label for="personality">生徒の性格</label>
        <select id="personality" name="personality">
          <option value="">-- 性格を選んでください --</option>
          <option value="1">開放性</option>
          <option value="2">勤勉性</option>
          <option value="3">神経症傾向</option>
          <option value="4">外向性</option>
        </select>

        <div class="buttons" style="position: relative;">
          <button type="submit">検索</button>
          <button type="reset">リセット</button>
          <span id="warningMsg" style="color: red; font-size: 12px; margin-left: 10px;"></span>
        </div>
      </form>

      <div id="result" class="result"></div>

      <form id="registerForm" action="<%= request.getContextPath() %>/RegisterResultServlet" method="post" style="margin-top: 10px; display: none;">
        <input type="hidden" name="subject" id="registerSubject" value="">
        <input type="hidden" name="personality" id="registerPersonality" value="">
        <input type="hidden" name="resultText" id="registerResultText" value="">
        <button type="submit">登録</button>
      </form>
    </div>
  </div>

  <script>
    document.getElementById('textForm').addEventListener('submit', function(e) {
      e.preventDefault();

      const subjectSelect = document.getElementById('subject');
      const personalitySelect = document.getElementById('personality');
      const warningMsg = document.getElementById('warningMsg');
      const resultDiv = document.getElementById('result');
      const registerForm = document.getElementById('registerForm');
      const registerSubject = document.getElementById('registerSubject');
      const registerPersonality = document.getElementById('registerPersonality');
      const registerResultText = document.getElementById('registerResultText');

      warningMsg.textContent = '';
      resultDiv.textContent = '';
      registerForm.style.display = 'none';

      if (!subjectSelect.value) {
        warningMsg.textContent = '教科を選んでください。';
        return;
      }
      if (!personalitySelect.value) {
        warningMsg.textContent = '性格を選んでください。';
        return;
      }

      const subject = subjectSelect.options[subjectSelect.selectedIndex].text;
      const personality = personalitySelect.options[personalitySelect.selectedIndex].text;

      const resultText = `結果：${subject} をやろう！（${personality}向け）`;

      resultDiv.textContent = resultText;

      registerSubject.value = subjectSelect.value;
      registerPersonality.value = personalitySelect.value;
      registerResultText.value = resultText;

      registerForm.style.display = 'block';
    });

    document.getElementById('textForm').addEventListener('reset', function() {
      document.getElementById('warningMsg').textContent = '';
      document.getElementById('result').textContent = '';
      document.getElementById('registerForm').style.display = 'none';
    });
  </script>
</body>
</html>

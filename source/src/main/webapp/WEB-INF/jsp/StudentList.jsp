<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>生徒一覧</title>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="<c:url value='/css/StudentList.css' />">
</head>
<body>
<!-- ヘッダー -->
<div class="wrapper">
  <div id="logo" style="text-align: center;">
	  <h1>
	    <a href="<c:url value='/home.jsp' />">
	      <img src="<c:url value='/img/K-Manage_logo.png'/>" width="200" height="200" alt="K-Manage">
	    </a>
	  </h1>
	</div>
  
  <ul id="nav">
    <li><a href="home.html">ホーム</a></li>
    <li><a href="">生徒一覧</a></li>
    <li><a href="">登録</a></li>
    <li><a href="">検索</a></li>
    <!-- <li><a href="">性格診断</a></li>
    <li><a href="">テキスト選出</a></li>
    <li><a href="">スケジュール作成</a></li> -->
    <li><a href="">宿題提案</a></li>
    <li><a href="">ログアウト</a></li>
  </ul>
 <h1 style="border-bottom: 2px solid #1685E6; padding-bottom: 5px; margin-bottom: 20px;">生徒一覧</h1>
  <div>現在の登録人数: <span id="studentCount">0</span> 人</div>
  <div style="display: flex; justify-content: flex-end; margin-top: 10px;">
	  <select id="sortSelect" style="padding: 5px; font-size: 1rem;">
	    <option disabled selected>並び替え</option>
	    <option value="default">登録順</option>
	    <option value="reverse">新しい順</option>
	    <option value="name-asc">名前：昇順</option>
	    <option value="name-desc">名前：降順</option>
	  </select>
  </div>
  
  
  <!-- 生徒カード表示 -->
  <div class="student-grid" id="studentGrid"></div>
  <div class="pagination" id="pagination"></div>
      <button id="backToTop" onclick="window.scrollTo({top: 0, behavior: 'smooth'});" style="
                position: fixed;
                bottom: 20px;
                right: 20px;
                padding: 10px 15px;
                font-size: 16px;
                border-radius: 5px;
                ">
                ↑ TOP
            </button>
</div>
<script src="<c:url value='/js/StudentList.js' />"></script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>生徒一覧</title>
<link rel="stylesheet" href="<c:url value='/css/StudentList.css' />">
</head>
<body>
<!-- ヘッダー -->
<div class="wrapper">
  <div id="logo" style="text-align: center;">
		  <h1 id="logo">
            <a href="<c:url value='/HomeServlet' />">
                <img src="<c:url value='/img/K-Manage_logo.png' />"  alt="K-Manage">
            </a>
        </h1>
        </div>
        <ul id="nav">
            <li><a href="<c:url value='/HomeServlet' />">ホーム</a></li>
            <li><a href="<c:url value='/StudentListServlet' />">生徒一覧</a></li>
            <li><a href="<c:url value='/RegistServlet' />">登録</a></li>
            <li><a href="<c:url value='/SearchServlet' />">検索</a></li>
            <li><a href="<c:url value='/LogoutServlet' />" onclick="return confirm('本当に実行しますか？');">ログアウト</a></li>
        </ul>
 <h1 style="border-bottom: 2px solid #1685E6; padding-bottom: 5px; margin-bottom: 20px;">生徒一覧</h1>
  <div>現在の登録人数： <span id="studentCount">0</span> 人</div>
  <div style="display: flex; justify-content: flex-end; margin-top: 10px;">
	  <!-- 並び替え -->
		<form method="get" action="<c:url value='/StudentListServlet' />" style="text-align: right; margin-top: 10px;">
		    <select name="sort" onchange="this.form.submit()" style="padding: 5px; font-size: 1rem;">
		        <option value="" disabled <c:if test="${empty sort}">selected</c:if>>並び替え</option>
		        <option value="createdDesc" <c:if test="${sort eq 'createdDesc'}">selected</c:if>>登録順（新しい順）</option>
		        <option value="createdAsc" <c:if test="${sort eq 'createdAsc'}">selected</c:if>>登録順（古い順）</option>
		        <option value="updatedDesc" <c:if test="${sort eq 'updatedDesc'}">selected</c:if>>更新順（新しい順）</option>
		        <option value="updatedAsc" <c:if test="${sort eq 'updatedAsc'}">selected</c:if>>更新順（古い順）</option>
		        <option value="nameAsc" <c:if test="${sort eq 'nameAsc'}">selected</c:if>>名前順（昇順）</option>
		        <option value="nameDesc" <c:if test="${sort eq 'nameDesc'}">selected</c:if>>名前順（降順）</option>
		    </select>
		</form>
	  
  </div>
  
  <!-- 生徒カード表示 -->
  <div class="student-grid" id="studentGrid"></div>
  <div class="pagination" id="pagination"></div>
      <button id="backToTop" onclick="window.scrollTo({top: 0, behavior: 'smooth'});" >
             ↑ TOP
      </button>
<button id="backToHome" onclick="location.href='<c:url value='/HomeServlet' />'">ホームへ戻る</button>
</div>
<script>
  const contextPath = "${pageContext.request.contextPath}";
  const students = [];
  <c:forEach var="s" items="${studentList}">
  students.push({
	  id: ${s.id},
	  name: "${fn:escapeXml(s.name)}",
	  school: "${fn:escapeXml(s.school_name)}",
	  gender: "${fn:escapeXml(s.gender)}",
	  updated_at: "${s.updated_at}"
	});

  </c:forEach>
</script>

<script src="<c:url value='/js/StudentList.js' />">
</script>
</body>
</html>
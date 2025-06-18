<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>検索結果</title>
    <link rel="stylesheet" href="<c:url value='/css/SearchResult.css' />">
    <style>
        /* 省略: スタイルは元のまま */
    </style>
</head>
<body>
<div class="wrapper">
    <!-- ヘッダー -->
    <h1 id="logo" style="text-align: center;">
            <a href="<c:url value='/HomeServlet' />">
                <img src="<c:url value='/img/K-Manage_logo.png' />"  alt="K-Manage">
            </a>
            
        </h1>
       <ul id="nav">
            <li><a href="<c:url value='/HomeServlet' />">ホーム</a></li>
            <li><a href="<c:url value='/StudentListServlet' />">生徒一覧</a></li>
            <li><a href="<c:url value='/RegistServlet' />">登録</a></li>
            <li><a href="<c:url value='/SearachServlet' />">検索</a></li>
            <li><a href="<c:url value='/LoginServlet' />">ログアウト</a></li>
        </ul>

    <h2>検索結果</h2>

    <p>検索結果：${totalRecords} 件</p>

    <form method="get" action="SearchResultServlet" style="text-align: right; margin-top: 10px;">
    <input type="hidden" name="name" value="${name}" />
    <input type="hidden" name="furigana" value="${furigana}" />
    <input type="hidden" name="schoolName" value="${schoolName}" />
    <input type="hidden" name="page" value="1" />
    
    <select name="sort" onchange="this.form.submit()" style="padding: 5px; font-size: 1rem;">
        <option value="" disabled <c:if test="${empty sort}">selected</c:if>>並び替え</option>
        <option value="createdDesc" <c:if test="${sort eq 'createdDesc'}">selected</c:if>>登録順（新しい順）</option>
        <option value="createdAsc" <c:if test="${sort eq 'createdAsc'}">selected</c:if>>登録順（古い順）</option>
        <option value="nameAsc" <c:if test="${sort eq 'nameAsc'}">selected</c:if>>名前順（昇順）</option>
        <option value="nameDesc" <c:if test="${sort eq 'nameDesc'}">selected</c:if>>名前順（降順）</option>
    </select>
</form>

    <c:choose>
        <c:when test="${not empty studentList}">
            <div class="student-grid">
                <c:forEach var="s" items="${studentList}">
    <div class="student-card-wrapper" style="position: relative;">
        <a href="<c:url value='/IndividualResultsServlet' />?studentId=${s.id}" style="text-decoration: none; color: inherit;">
            <div class="student-card" style="cursor: pointer;">
                <p><strong>氏名:</strong> ${s.name}</p>
                <p><strong>学校名:</strong> ${s.school_name}</p>
                <p><strong>性別:</strong> ${s.gender}</p>
            </div>
        </a>
        <form action="SearchResultServlet" method="post"
     		 onsubmit="return confirm('本当にこの生徒を削除しますか？');"
     		 style="position: absolute; top: 10px; right: 30px;">
    		<input type="hidden" name="deleteId" value="${s.id}" />
   			 <input type="submit" value="削除" />
		</form>
        
    </div>
</c:forEach>
            </div>

            <!-- ページネーション -->
            <c:if test="${totalPages > 1}">
                <div class="pagination" style="text-align: center; margin-top: 20px;">
                    <!-- 前へボタン -->
                    <c:if test="${currentPage > 1}">
                        <form method="get" action="SearchResultServlet" style="display: inline;">
                            <input type="hidden" name="name" value="${name}" />
                            <input type="hidden" name="furigana" value="${furigana}" />
                            <input type="hidden" name="schoolName" value="${schoolName}" />
                            <input type="hidden" name="sort" value="${sort}" />
                            <input type="hidden" name="page" value="${currentPage - 1}" />
                            <input type="submit" value="前へ" />
                        </form>
                    </c:if>

                    <!-- 現在のページ番号表示 -->
                    <span style="margin: 0 15px; font-weight: bold;">${currentPage} / ${totalPages}</span>

                    <!-- 次へボタン -->
                    <c:if test="${currentPage < totalPages}">
                        <form method="get" action="SearchResultServlet" style="display: inline;">
                            <input type="hidden" name="name" value="${name}" />
                            <input type="hidden" name="furigana" value="${furigana}" />
                            <input type="hidden" name="schoolName" value="${schoolName}" />
                            <input type="hidden" name="sort" value="${sort}" />
                            <input type="hidden" name="page" value="${currentPage + 1}" />
                            <input type="submit" value="次へ" />
                        </form>
                    </c:if>
                </div>
            </c:if>

        </c:when>
        <c:otherwise>
            <p>該当する生徒は見つかりませんでした。</p>
        </c:otherwise>
    </c:choose>
</div>

<script></script>
</body>
</html>

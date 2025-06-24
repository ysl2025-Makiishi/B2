<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>検索結果</title>
<link rel="stylesheet" href="<c:url value='/css/SearchResult.css' />">
<style>
/* 必要なスタイルはここに記述 */
</style>
</head>
<body>
<div class="wrapper">
    <!-- ヘッダー -->
    <h1 id="logo" style="text-align: center;">
        <a href="<c:url value='/HomeServlet' />">
            <img src="<c:url value='/img/K-Manage_logo.png' />" alt="K-Manage">
        </a>
    </h1>

    <ul id="nav">
        <li><a href="<c:url value='/HomeServlet' />">ホーム</a></li>
        <li><a href="<c:url value='/StudentListServlet' />">生徒一覧</a></li>
        <li><a href="<c:url value='/RegistServlet' />">登録</a></li>
        <li><a href="<c:url value='/SearchServlet' />">検索</a></li>
        <li><a href="<c:url value='/LogoutServlet' />" onclick="return confirm('本当に実行しますか？');">ログアウト</a></li>
    </ul>

    <h2>検索結果</h2>

    <p>検索結果：${totalRecords} 件</p>

    <!-- 並び替え -->
    <form method="get" action="<c:url value='/SearchResultServlet' />" style="text-align: right; margin-top: 10px;">
        <input type="hidden" name="name" value="${name}" />
        <input type="hidden" name="furigana" value="${furigana}" />
        <input type="hidden" name="schoolName" value="${schoolName}" />
        <input type="hidden" name="page" value="1" />
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

    <!-- 結果表示 -->
    <c:choose>
        <c:when test="${not empty studentList}">
            <div class="student-grid">
                <c:forEach var="s" items="${studentList}">
                    <div class="student-card-wrapper" style="position: relative;">
                        <c:url var="detailUrl" value="/IndividualResultsServlet">
                            <c:param name="studentId" value="${s.id}" />
                        </c:url>
                        <a href="${detailUrl}" style="text-decoration: none; color: inherit;">
                            <div class="student-card" style="cursor: pointer;">
                                <p><span class="label">氏名:</span> <span class="name">${s.name}</span></p>
                                <p><strong>学校名:</strong> ${s.school_name}</p>
                                <p>
                                    <strong>性別:</strong>
                                    <c:choose>
                                        <c:when test="${s.gender == 'M'}">男</c:when>
                                        <c:when test="${s.gender == 'F'}">女</c:when>
                                        <c:otherwise>無回答</c:otherwise>
                                    </c:choose>
                                </p>
                            </div>
                        </a>

                        <!-- 削除ボタン -->
                        <form action="<c:url value='/SearchResultServlet' />" method="post"
                              onsubmit="return confirm('本当にこの生徒を削除しますか？');"
                              style="position: absolute; top: 50px; right: 50px;">
                            <input type="hidden" name="deleteId" value="${s.id}" />
                            <input type="submit" value="削除" />
                        </form>
                    </div>
                </c:forEach>
            </div>

            <!-- ページネーション -->
            <c:if test="${totalPages > 1}">
                <div class="pagination" style="text-align: center; margin-top: 20px;">

                    <!-- 前へ -->
                    <c:if test="${currentPage > 1}">
                        <form method="get" action="<c:url value='/SearchResultServlet' />" style="display: inline;">
                            <input type="hidden" name="name" value="${name}" />
                            <input type="hidden" name="furigana" value="${furigana}" />
                            <input type="hidden" name="schoolName" value="${schoolName}" />
                            <input type="hidden" name="sort" value="${sort}" />
                            <input type="hidden" name="page" value="${currentPage - 1}" />
                            <input type="submit" value="前へ" />
                        </form>
                    </c:if>

                    <!-- 現在のページ -->
                    <span style="margin: 0 15px; font-weight: bold;">
                        ${currentPage} / ${totalPages}
                    </span>

                    <!-- 次へ -->
                    <c:if test="${currentPage < totalPages}">
                        <form method="get" action="<c:url value='/SearchResultServlet' />" style="display: inline;">
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

<!-- 上へ戻るボタン -->
<div class="student-grid" id="studentGrid"></div>
  <div class="pagination" id="pagination"></div>
      <button id="backToTop" onclick="window.scrollTo({top: 0, behavior: 'smooth'});" >
             ↑ TOP
      </button>
	
</div>

<script src="<c:url value='/js/SearchResult.js' />"></script>
</body>
</html>

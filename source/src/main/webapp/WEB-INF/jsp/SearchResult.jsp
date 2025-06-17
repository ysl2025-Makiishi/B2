<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>検索結果</title>
    <link rel="stylesheet" href="/B2/css/SearchResult.css">
    <style>
        /* 省略: スタイルは元のまま */
    </style>
</head>
<body>
<div class="wrapper">
    <!-- ヘッダー -->
    <h1 id="logo" style="text-align: center;">
        <a href="K-Manage_home.html">
            <img src="/B2/img/K-Manage_logo.png" width="200" height="200" alt="K-Manage">
        </a>
    </h1>
    <ul id="nav">
        <li><a href="home.html">ホーム</a></li>
        <li><a href="">生徒一覧</a></li>
        <li><a href="">登録</a></li>
        <li><a href="">検索</a></li>
        <li><a href="">宿題提案</a></li>
        <li><a href="">ログアウト</a></li>
    </ul>

    <h2>検索結果</h2>

    <p>検索結果：${totalRecords} 件</p>

    <form method="get" action="SearchResultServlet" style="text-align: right; margin-top: 10px;">
        <input type="hidden" name="nameKeyword" value="${nameKeyword}" />
        <input type="hidden" name="furiganaKeyword" value="${furiganaKeyword}" />
        <input type="hidden" name="schoolNameKeyword" value="${schoolNameKeyword}" />
        <input type="hidden" name="page" value="1" />
        <select name="sort" onchange="this.form.submit()" style="padding: 5px; font-size: 1rem;">
            <option value="" disabled ${sort == null || sort == '' ? 'selected' : ''}>並び替え</option>
            <option value="createdDesc" ${sort eq 'createdDesc' ? 'selected' : ''}>登録順（新しい順）</option>
            <option value="createdAsc" ${sort eq 'createdAsc' ? 'selected' : ''}>登録順（古い順）</option>
            <option value="nameAsc" ${sort eq 'nameAsc' ? 'selected' : ''}>名前順（昇順）</option>
            <option value="nameDesc" ${sort eq 'nameDesc' ? 'selected' : ''}>名前順（降順）</option>
        </select>
    </form>

    <c:choose>
        <c:when test="${not empty studentList}">
            <div class="student-grid">
                <c:forEach var="s" items="${studentList}">
                    <div class="student-card">
                        <p><strong>氏名:</strong> 
                            <a href="StudentDetailServlet?studentId=${s.id}">${s.name}</a>
                        </p>
                        <p><strong>学校名:</strong> ${s.school_name}</p>
                        <p><strong>性別:</strong> ${s.gender}</p>
                        <form action="DeleteStudentServlet" method="post" onsubmit="return confirm('本当にこの生徒を削除しますか？');">
                            <input type="hidden" name="studentId" value="${s.id}" />
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
                            <input type="hidden" name="nameKeyword" value="${nameKeyword}" />
                            <input type="hidden" name="furiganaKeyword" value="${furiganaKeyword}" />
                            <input type="hidden" name="schoolNameKeyword" value="${schoolNameKeyword}" />
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
                            <input type="hidden" name="nameKeyword" value="${nameKeyword}" />
                            <input type="hidden" name="furiganaKeyword" value="${furiganaKeyword}" />
                            <input type="hidden" name="schoolNameKeyword" value="${schoolNameKeyword}" />
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

<script src="/B2/js/SearchResult.js"></script>
</body>
</html>

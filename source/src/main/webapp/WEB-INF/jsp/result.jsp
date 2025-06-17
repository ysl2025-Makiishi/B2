<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>K-Manage</title>
<link rel="stylesheet" href="<c:url value='/css/K-style.css' />">
<link rel="stylesheet" href="<c:url value='/css/result.css' />">

</head>
<body>
  <div class="container">
    <h1><c:out value="${result.title}" /></h1>
    <hr>
    <p><c:out value="${result.message}" /></p>
    <a href="${result.backTo}" class="button">戻る</a>
  </div>
</body>
</html>

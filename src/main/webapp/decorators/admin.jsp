<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
<script src="${pageContext.request.contextPath}/assets/js/app.js" defer>
</script>
<title>
<sitemesh:write property="title"/> | Book Store</title>
<sitemesh:write property="head"/>
</head>
<body class="admin" data-decorator="admin">
<header>
<a class="brand" href="${pageContext.request.contextPath}/home">BOOK<span>STORE</span> <small>ADMIN</small>
</a>
<nav>
<a href="${pageContext.request.contextPath}/admin">Dashboard</a>
<a href="${pageContext.request.contextPath}/admin/books">Quản lý Books</a>
<a href="${pageContext.request.contextPath}/admin/authors">Quản lý Authors</a>
<a href="${pageContext.request.contextPath}/home">Trang chủ</a>
<form class="inline" method="post" action="${pageContext.request.contextPath}/logout">
<input type="hidden" name="csrf" value="${sessionScope.csrf}">
<button class="link" type="submit">Đăng xuất</button>
</form>
</nav>
</header>
<main>
<sitemesh:write property="body"/>
</main>
<footer>Cao Quốc Đạt - 24162019 - Mã đề 01</footer>
</body>
</html>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
<script src="${pageContext.request.contextPath}/assets/js/app.js" defer></script>
<title><sitemesh:write property="title"/> | Book Store</title>
<sitemesh:write property="head"/>
</head>
<body class="web" data-decorator="web">
<header>
<a class="brand" href="${pageContext.request.contextPath}/home">BOOK<span>STORE</span></a>
<nav>
<a href="${pageContext.request.contextPath}/home">Trang Chủ</a>
<a href="${pageContext.request.contextPath}/home#books">Sản phẩm</a>
<c:choose>
<c:when test="${not empty sessionScope.account}">
<c:if test="${!sessionScope.account.admin}">
<a href="${pageContext.request.contextPath}/cart">Giỏ hàng</a>
<a href="${pageContext.request.contextPath}/orders">Đơn hàng của tôi</a>
</c:if>
<c:if test="${sessionScope.account.admin}">
<a href="${pageContext.request.contextPath}/admin">Trang quản trị</a>
</c:if>
<span class="username"><c:out value="${sessionScope.account.fullname}"/></span>
<form class="inline" method="post" action="${pageContext.request.contextPath}/logout">
<input type="hidden" name="csrf" value="${sessionScope.csrf}">
<button class="link" type="submit">Đăng xuất</button>
</form>
</c:when>
<c:otherwise>
<a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
</c:otherwise>
</c:choose>
</nav>
</header>
<main>
<sitemesh:write property="body"/>
</main>
<footer>Cao Quốc Đạt - 24162019 - Mã đề 01</footer>
</body>
</html>

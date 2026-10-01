<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Trang quản trị</title>
</head>
<body>
<p class="eyebrow">BOOK STORE · QUẢN TRỊ</p>
<h1>Tổng quan</h1>
<p class="muted">Quản lý danh mục sách và tác giả.</p>
<div class="stats">
<a class="panel" href="${pageContext.request.contextPath}/admin/books">
<span>Books</span>
<strong>${bookCount}</strong>
<span>Quản lý sách →</span>
</a>
<a class="panel" href="${pageContext.request.contextPath}/admin/authors">
<span>Authors</span>
<strong>${authorCount}</strong>
<span>Quản lý tác giả →</span>
</a>
</div>
</body>
</html>

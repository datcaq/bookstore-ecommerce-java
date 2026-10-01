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
<title>Đăng nhập | Book Store</title>
</head>
<body class="auth">
<main class="auth-wrap">
<a class="brand" href="${pageContext.request.contextPath}/home">BOOK<span>STORE</span>
</a>
<section class="auth-card">
<p class="eyebrow">CHÀO MỪNG ĐẾN VỚI TỦ SÁCH</p>
<h1>Đăng nhập</h1>
<c:if test="${not empty error}">
<div class="alert error" role="alert">
<c:out value="${error}"/>
</div>
</c:if>
<c:if test="${param.saved == '1'}">
<div class="alert success">Đã lưu thành công.</div>
</c:if>
<c:if test="${param.deleted == '1'}">
<div class="alert success">Đã xóa thành công.</div>
</c:if>
<c:if test="${param.registered == '1'}">
<div class="alert success">Xác thực thành công. Bạn có thể đăng nhập.</div>
</c:if>
<form method="post" action="${pageContext.request.contextPath}/login">
<input type="hidden" name="csrf" value="${sessionScope.csrf}">
<label for="email">Email</label>
<input id="email" name="email" type="email" required maxlength="50" autocomplete="username" value="${fn:escapeXml(param.email)}">
<label for="password">Mật khẩu</label>
<input id="password" name="password" type="password" required maxlength="128" autocomplete="current-password">
<button type="submit">Đăng nhập</button>
</form>
<p>Chưa có tài khoản? <a href="${pageContext.request.contextPath}/register">Đăng ký</a>
</p>
</section>
</main>
<footer>Cao Quốc Đạt - 24162019 - Mã đề 01</footer>
</body>
</html>

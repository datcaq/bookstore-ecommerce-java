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
<title>Tạo tài khoản | Book Store</title>
</head>
<body class="auth">
<main class="auth-wrap">
<a class="brand" href="${pageContext.request.contextPath}/home">BOOK<span>STORE</span>
</a>
<section class="auth-card">
<p class="eyebrow">CHÀO MỪNG ĐẾN VỚI TỦ SÁCH</p>
<h1>Tạo tài khoản</h1>
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
<form method="post" action="${pageContext.request.contextPath}/register">
<input type="hidden" name="csrf" value="${sessionScope.csrf}">
<label for="email">Email</label>
<input id="email" name="email" type="email" required maxlength="50" autocomplete="email" value="${fn:escapeXml(param.email)}">
<label for="fullname">Họ tên</label>
<input id="fullname" name="fullname" type="text" required maxlength="50" autocomplete="name" value="${fn:escapeXml(param.fullname)}">
<label for="phone">Phone (số nguyên theo đề)</label>
<input id="phone" name="phone" type="number" min="0" max="2147483647" value="${fn:escapeXml(param.phone)}">
<label for="password">Mật khẩu</label>
<input id="password" name="password" type="password" required minlength="6" maxlength="128" autocomplete="new-password">
<label for="confirm">Xác nhận mật khẩu</label>
<input id="confirm" name="confirm" type="password" required minlength="6" maxlength="128" autocomplete="new-password">
<button type="submit">Đăng ký &amp; gửi OTP</button>
</form>
<p class="muted">Tài khoản chỉ được tạo sau khi xác thực email.</p>
<p>Đã có tài khoản? <a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
</p>
</section>
</main>
<footer>Cao Quốc Đạt - 24162019 - Mã đề 01</footer>
</body>
</html>

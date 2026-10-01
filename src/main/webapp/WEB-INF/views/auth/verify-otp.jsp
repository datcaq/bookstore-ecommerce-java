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
<title>Xác thực email | Book Store</title>
</head>
<body class="auth">
<main class="auth-wrap">
<a class="brand" href="${pageContext.request.contextPath}/home">BOOK<span>STORE</span>
</a>
<section class="auth-card">
<p class="eyebrow">CHÀO MỪNG ĐẾN VỚI TỦ SÁCH</p>
<h1>Xác thực email</h1>
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
<c:choose>
<c:when test="${not empty sessionScope.pendingRegistration}">
<p>Mã OTP đã gửi đến <strong>
<c:out value="${sessionScope.pendingRegistration.user.email}"/>
</strong>. Mã có hiệu lực 5 phút, tối đa 5 lần thử.</p>
<form method="post" action="${pageContext.request.contextPath}/verify-otp">
<input type="hidden" name="csrf" value="${sessionScope.csrf}">
<label for="otp">Mã OTP gồm 6 chữ số</label>
<input id="otp" name="otp" type="text" required pattern="[0-9]{6}" maxlength="6" inputmode="numeric" autocomplete="one-time-code">
<button type="submit">Xác thực tài khoản</button>
</form>
</c:when>
<c:otherwise>
<p>Không có đăng ký đang chờ xác thực.</p>
</c:otherwise>
</c:choose>
<p>
<a href="${pageContext.request.contextPath}/register">Đăng ký lại / nhận mã mới</a> (cách nhau ít nhất 60 giây).</p>
</section>
</main>
<footer>Cao Quốc Đạt - 24162019 - Mã đề 01</footer>
</body>
</html>

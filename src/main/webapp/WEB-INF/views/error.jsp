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
<title>Không thể thực hiện yêu cầu</title>
</head>
<body>
<main class="auth-wrap">
<section class="auth-card">
<h1>Không thể thực hiện yêu cầu</h1>
<c:choose>
<c:when test="${not empty error}">
<p>
<c:out value="${error}"/>
</p>
</c:when>
<c:when test="${requestScope['jakarta.servlet.error.status_code'] == 403}">
<p>Bạn không có quyền truy cập hoặc phiên biểu mẫu đã hết hạn.</p>
</c:when>
<c:otherwise>
<p>Đường dẫn hoặc dữ liệu yêu cầu không hợp lệ.</p>
</c:otherwise>
</c:choose>
<a class="button" href="${pageContext.request.contextPath}/home">Về trang chủ</a>
</section>
</main>
<footer>Cao Quốc Đạt - 24162019 - Mã đề 01</footer>
</body>
</html>

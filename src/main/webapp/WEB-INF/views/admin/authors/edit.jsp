<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Cập nhật Authors</title>
</head>
<body>
<a class="back" href="${pageContext.request.contextPath}/admin/authors">← Danh sách Authors</a>
<div class="panel form-panel">
<h1>Cập nhật tác giả</h1>
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
<form method="post" action="${pageContext.request.contextPath}/admin/authors/edit">
<input type="hidden" name="csrf" value="${sessionScope.csrf}">
<input type="hidden" name="id" value="${author.id}">
<label for="author_name">Tên tác giả</label>
<input id="author_name" name="author_name" type="text" required maxlength="100" value="${fn:escapeXml(author.name)}">
<label for="date_of_birth">Ngày sinh</label>
<input id="date_of_birth" name="date_of_birth" type="date"  value="${fn:escapeXml(author.dateOfBirth)}">
<div class="form-actions">
<button type="submit">Lưu</button>
<a href="${pageContext.request.contextPath}/admin/authors">Hủy</a>
</div>
</form>
</div>
</body>
</html>

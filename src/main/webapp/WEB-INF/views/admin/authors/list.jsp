<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Authors</title>
</head>
<body>
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
<div class="section-head">
<div>
<p class="eyebrow">QUẢN LÝ DANH MỤC</p>
<h1>Authors</h1>
</div>
<a class="button" href="${pageContext.request.contextPath}/admin/authors/add">+ Thêm tác giả</a>
</div>
<div class="table-wrap">
<table>
<thead>
<tr>
<th>Author ID</th>
<th>Author Name</th>
<th>Date of Birth</th>
<th>Actions</th>
</tr>
</thead>
<tbody>
<c:forEach items="${authors}" var="item">
<tr>
<td>${item.id}</td>
<td>
<c:out value="${item.name}"/>
</td>
<td>${item.dateOfBirth}</td>
<td class="actions">
<a href="${pageContext.request.contextPath}/admin/authors/edit?id=${item.id}">Sửa</a>
<form method="post" data-confirm="Xóa mục này và các liên kết liên quan?" action="${pageContext.request.contextPath}/admin/authors/delete">
<input type="hidden" name="csrf" value="${sessionScope.csrf}">
<input type="hidden" name="id" value="${item.id}">
<button class="danger" type="submit">Xóa</button>
</form>
</td>
</tr>
</c:forEach>
<c:if test="${empty authors}">
<tr>
<td colspan="4">Chưa có dữ liệu.</td>
</tr>
</c:if>
</tbody>
</table>
</div>
<jsp:include page="/WEB-INF/views/shared/pagination.jsp"/>
</body>
</html>

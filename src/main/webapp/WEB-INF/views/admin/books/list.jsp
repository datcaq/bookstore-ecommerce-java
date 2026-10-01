<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Books</title>
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
<h1>Books</h1>
</div>
<a class="button" href="${pageContext.request.contextPath}/admin/books/add">+ Thêm sách</a>
</div>
<div class="table-wrap">
<table>
<thead>
<tr>
<th>Book ID</th>
<th>ISBN</th>
<th>Title</th>
<th>Authors</th>
<th>Publisher</th>
<th>Price</th>
<th>Publish Date</th>
<th>Quantity</th>
<th>Cover</th>
<th>Actions</th>
</tr>
</thead>
<tbody>
<c:forEach items="${books}" var="item">
<tr>
<td>${item.id}</td>
<td>${item.isbn}</td>
<td>
<a href="${pageContext.request.contextPath}/book/detail?id=${item.id}">
<c:out value="${item.title}"/>
</a>
</td>
<td>
<c:forEach items="${item.authors}" var="a" varStatus="st">
<c:out value="${a.name}"/>
<c:if test="${not st.last}">, </c:if>
</c:forEach>
</td>
<td>
<c:out value="${item.publisher}"/>
</td>
<td>${item.price}</td>
<td>${item.publishDate}</td>
<td>${item.quantity}</td>
<td>
<c:set var="book" value="${item}"/>
<c:url value="${book.coverPath}" var="coverUrl"/>
<img class="cover" src="${fn:escapeXml(coverUrl)}" alt="Bìa ${fn:escapeXml(book.title)}" data-fallback="${pageContext.request.contextPath}/assets/images/book-placeholder.svg">
</td>
<td class="actions">
<a href="${pageContext.request.contextPath}/admin/books/edit?id=${item.id}">Sửa</a>
<form method="post" data-confirm="Xóa mục này và các liên kết liên quan?" action="${pageContext.request.contextPath}/admin/books/delete">
<input type="hidden" name="csrf" value="${sessionScope.csrf}">
<input type="hidden" name="id" value="${item.id}">
<button class="danger" type="submit">Xóa</button>
</form>
</td>
</tr>
</c:forEach>
<c:if test="${empty books}">
<tr>
<td colspan="10">Chưa có dữ liệu.</td>
</tr>
</c:if>
</tbody>
</table>
</div>
<jsp:include page="/WEB-INF/views/shared/pagination.jsp"/>
</body>
</html>

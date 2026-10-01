<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Cập nhật Books</title>
</head>
<body>
<a class="back" href="${pageContext.request.contextPath}/admin/books">← Danh sách Books</a>
<div class="panel form-panel">
<h1>Cập nhật sách</h1>
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
<form method="post" action="${pageContext.request.contextPath}/admin/books/edit">
<input type="hidden" name="csrf" value="${sessionScope.csrf}">
<input type="hidden" name="id" value="${book.id}">
<label for="isbn">ISBN</label>
<input id="isbn" name="isbn" type="number" required min="0" max="2147483647" value="${fn:escapeXml(book.isbn)}">
<label for="title">Tiêu đề</label>
<input id="title" name="title" type="text" required maxlength="200" value="${fn:escapeXml(book.title)}">
<label for="publisher">Publisher</label>
<input id="publisher" name="publisher" type="text" maxlength="100" value="${fn:escapeXml(book.publisher)}">
<label for="price">Giá</label>
<input id="price" name="price" type="number" required min="0" max="9999.99" step="0.01" value="${fn:escapeXml(book.price)}">
<label for="publish_date">Ngày xuất bản</label>
<input id="publish_date" name="publish_date" type="date"  value="${fn:escapeXml(book.publishDate)}">
<label for="quantity">Số lượng</label>
<input id="quantity" name="quantity" type="number" required min="0" max="2147483647" value="${fn:escapeXml(book.quantity)}">
<label for="cover_image">Ảnh bìa (URL hoặc tên file local)</label>
<input id="cover_image" name="cover_image" type="text" maxlength="100" value="${fn:escapeXml(book.coverImage)}">
<label for="description">Mô tả</label>
<textarea id="description" name="description" rows="5" maxlength="10000">
<c:out value="${book.description}"/>
</textarea>
<fieldset>
<legend>Tác giả (chọn một hoặc nhiều)</legend>
<c:forEach items="${authors}" var="a">
<label class="check">
<input type="checkbox" name="authors" value="${a.id}" ${selectedAuthors.contains(a.id) ? 'checked' : ''}>
<c:out value="${a.name}"/>
</label>
</c:forEach>
<c:if test="${empty authors}">
<p>Hãy thêm tác giả trước khi thêm sách.</p>
</c:if>
</fieldset>
<div class="form-actions">
<button type="submit">Lưu</button>
<a href="${pageContext.request.contextPath}/admin/books">Hủy</a>
</div>
</form>
</div>
</body>
</html>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Trang chủ</title>
</head>
<body>
<section class="hero">
<div>
<p class="eyebrow">MỖI TRANG SÁCH, MỘT KHỞI ĐẦU</p>
<h1>Khám phá những<br>câu chuyện mới.</h1>
<p>Chọn một cuốn sách, gặp một góc nhìn khác.</p>
<a class="button" href="#books">Khám phá tủ sách ↓</a>
</div>
<div class="hero-art" aria-hidden="true">
<span>ĐỌC</span>
<span>HIỂU</span>
<span>TRƯỞNG THÀNH</span>
</div>
</section>
<section id="books">
<div class="section-head">
<h2>Tủ sách của bạn</h2>
<span class="muted">${total} cuốn sách</span>
</div>
<c:if test="${empty books}">
<p class="empty">Chưa có sách. Quản trị viên có thể thêm sách tại Trang quản trị.</p>
</c:if>
<div class="grid">
<c:forEach items="${books}" var="book">
<article class="book-card">
<c:url value="${book.coverPath}" var="coverUrl"/>
<img class="cover" src="${fn:escapeXml(coverUrl)}" alt="Bìa ${fn:escapeXml(book.title)}" data-fallback="${pageContext.request.contextPath}/assets/images/book-placeholder.svg">
<div class="card-content">
<h3>
<a href="${pageContext.request.contextPath}/book/detail?id=${book.id}">
<c:out value="${book.title}"/>
</a>
</h3>
<dl>
<dt>Giá</dt>
<dd style="font-weight: 700; color: #b27844;">
<c:out value="${book.price}"/>
</dd>
<dt>Mã isbn</dt>
<dd>
<c:out value="${book.isbn}"/>
</dd>
<dt>Tác giả</dt>
<dd>
<c:forEach items="${book.authors}" var="a" varStatus="st">
<c:out value="${a.name}"/>
<c:if test="${not st.last}">, </c:if>
</c:forEach>
</dd>
<dt>Publisher</dt>
<dd>
<c:out value="${book.publisher}"/>
</dd>
<dt>Publisher date</dt>
<dd>
<c:out value="${book.publishDate}"/>
</dd>
<dt>Quantity</dt>
<dd>
<c:out value="${book.quantity}"/>
</dd>
</dl>
<c:choose>
<c:when test="${book.quantity > 0}">
<form method="post" action="${pageContext.request.contextPath}/cart/add" style="margin-top: 14px;">
<input type="hidden" name="csrf" value="${sessionScope.csrf}">
<input type="hidden" name="bookId" value="${book.id}">
<input type="hidden" name="quantity" value="1">
<button type="submit" style="width: 100%;">Thêm vào giỏ</button>
</form>
</c:when>
<c:otherwise>
<div style="margin-top: 14px; text-align: center; color: #a44a36; font-weight: 600; padding: 8px; background: #f6eae5; border-radius: 5px;">Hết hàng</div>
</c:otherwise>
</c:choose>
<a class="review-count" href="${pageContext.request.contextPath}/book/detail?id=${book.id}#reviews">Review (${book.reviewCount}) →</a>
</div>
</article>
</c:forEach>
</div>
<jsp:include page="/WEB-INF/views/shared/pagination.jsp"/>
</section>
</body>
</html>

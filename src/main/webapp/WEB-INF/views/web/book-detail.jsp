<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Chi tiết sách</title>
</head>
<body>
<a class="back" href="${pageContext.request.contextPath}/home">← Trở về tủ sách</a>
<c:if test="${not empty error}">
<div class="alert error" role="alert">
<c:out value="${error}"/>
</div>
</c:if>
<c:if test="${not empty param.error}">
<div class="alert error" role="alert">
<c:out value="${param.error}"/>
</div>
</c:if>
<c:if test="${param.saved == '1'}">
<div class="alert success">Đã lưu thành công.</div>
</c:if>
<c:if test="${param.deleted == '1'}">
<div class="alert success">Đã xóa thành công.</div>
</c:if>
<section class="detail">
<div>
<c:url value="${book.coverPath}" var="coverUrl"/>
<img class="cover" src="${fn:escapeXml(coverUrl)}" alt="Bìa ${fn:escapeXml(book.title)}" data-fallback="${pageContext.request.contextPath}/assets/images/book-placeholder.svg">
</div>
<div>
<p class="eyebrow">CHI TIẾT CUỐN SÁCH</p>
<h1>
<c:out value="${book.title}"/>
</h1>
<p class="price">Giá:
<c:out value="${book.price}"/>
</p>
<dl>
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

<div style="margin: 20px 0; padding: 18px; background: #f0f4ee; border-radius: 8px;">
<c:choose>
<c:when test="${book.quantity > 0}">
<form method="post" action="${pageContext.request.contextPath}/cart/add" style="display: flex; align-items: center; gap: 15px; flex-wrap: wrap;">
<input type="hidden" name="csrf" value="${sessionScope.csrf}">
<input type="hidden" name="bookId" value="${book.id}">
<input type="hidden" name="fromDetail" value="1">
<label for="cart-qty" style="margin: 0; font-weight: 600;">Số lượng:</label>
<input type="number" id="cart-qty" name="quantity" value="1" min="1" max="${book.quantity}" style="width: 80px; padding: 8px 10px;" required>
<button type="submit" class="button">Thêm vào giỏ hàng</button>
</form>
</c:when>
<c:otherwise>
<div style="color: #a44a36; font-weight: 600;">Sản phẩm hiện đã hết hàng trong kho.</div>
</c:otherwise>
</c:choose>
</div>

<p>Reviews (${book.reviewCount})</p>
<h3>Mô tả</h3>
<p class="preline">
<c:out value="${book.description}"/>
</p>
</div>
</section>
<section id="reviews">
<h2>Reviews (${book.reviewCount})</h2>
<c:if test="${empty reviews}">
<p class="empty">Chưa có đánh giá. Hãy chia sẻ cảm nhận đầu tiên!</p>
</c:if>
<c:forEach items="${reviews}" var="review">
<article class="review">
<strong>
<c:out value="${review.user.fullname}"/>
</strong>
<span class="stars">Rating: ${review.rating}/5</span>
<p class="preline">
<c:out value="${review.reviewText}"/>
</p>
</article>
</c:forEach>
<c:choose>
<c:when test="${not empty sessionScope.account}">
<div class="panel">
<h2>Chia sẻ cảm nhận</h2>
<p class="muted">Mỗi tài khoản có một đánh giá cho mỗi sách. Gửi lại để cập nhật đánh giá của bạn.</p>
<form method="post" action="${pageContext.request.contextPath}/book/review">
<input type="hidden" name="csrf" value="${sessionScope.csrf}">
<input type="hidden" name="bookId" value="${book.id}">
<label for="rating">Rating</label>
<select id="rating" name="rating" required>
<option value="5">5 - Rất hay</option>
<option value="4">4 - Hay</option>
<option value="3">3 - Bình thường</option>
<option value="2">2 - Chưa tốt</option>
<option value="1">1 - Không hài lòng</option>
</select>
<label for="review_text">Nội dung đánh giá</label>
<textarea id="review_text" name="review_text" maxlength="2000" required rows="4">
<c:out value="${param.review_text}"/>
</textarea>
<button type="submit">Submit · Gửi đánh giá</button>
</form>
</div>
</c:when>
<c:otherwise>
<p>
<a href="${pageContext.request.contextPath}/login">Đăng nhập</a> để thêm đánh giá.</p>
</c:otherwise>
</c:choose>
</section>
</body>
</html>

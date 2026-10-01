<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Giỏ hàng</title>
</head>
<body>
<div style="margin-bottom: 24px;">
<a class="back" href="${pageContext.request.contextPath}/home">← Tiếp tục mua sắm</a>
</div>

<c:if test="${not empty error}">
<div class="alert error" role="alert">
<c:out value="${error}"/>
</div>
</c:if>
<c:if test="${saved == '1'}">
<div class="alert success">Đã cập nhật giỏ hàng thành công.</div>
</c:if>

<div class="section-head">
<h1>Giỏ hàng của bạn</h1>
<c:if test="${not empty cart.items}">
<span class="muted">${fn:length(cart.items)} sản phẩm</span>
</c:if>
</div>

<c:choose>
<c:when test="${empty cart.items}">
<div class="empty">
<p style="margin-bottom: 16px;">Giỏ hàng của bạn đang trống.</p>
<a class="button" href="${pageContext.request.contextPath}/home">Khám phá sách ngay</a>
</div>
</c:when>
<c:otherwise>
<div class="table-wrap" style="margin-bottom: 30px;">
<table>
<thead>
<tr>
<th style="width: 80px;">Ảnh</th>
<th>Tên sách</th>
<th style="width: 110px;">Đơn giá</th>
<th style="width: 180px;">Số lượng</th>
<th style="width: 120px;">Thành tiền</th>
<th style="width: 90px; text-align: center;">Thao tác</th>
</tr>
</thead>
<tbody>
<c:forEach items="${cart.items}" var="item">
<tr>
<td>
<c:url value="${item.book.coverPath}" var="coverUrl"/>
<img class="cover" src="${fn:escapeXml(coverUrl)}" alt="Bìa ${fn:escapeXml(item.book.title)}" data-fallback="${pageContext.request.contextPath}/assets/images/book-placeholder.svg">
</td>
<td>
<strong>
<a href="${pageContext.request.contextPath}/book/detail?id=${item.book.id}">
<c:out value="${item.book.title}"/>
</a>
</strong>
<div class="muted" style="font-size: 12px; margin-top: 4px;">
Kho còn: <c:out value="${item.book.quantity}"/> cuốn
</div>
</td>
<td style="font-weight: 600; color: #b27844;">
<c:out value="${item.book.price}"/>
</td>
<td>
<div style="display: flex; align-items: center; gap: 6px;">
<form method="post" action="${pageContext.request.contextPath}/cart/update" class="inline">
<input type="hidden" name="csrf" value="${sessionScope.csrf}">
<input type="hidden" name="bookId" value="${item.book.id}">
<input type="hidden" name="quantity" value="${item.quantity - 1}">
<button type="submit" style="padding: 4px 10px; font-size: 13px;" ${item.quantity <= 1 ? 'disabled' : ''}>-</button>
</form>

<form method="post" action="${pageContext.request.contextPath}/cart/update" class="inline" style="display: flex; gap: 4px; align-items: center;">
<input type="hidden" name="csrf" value="${sessionScope.csrf}">
<input type="hidden" name="bookId" value="${item.book.id}">
<input type="number" name="quantity" value="${item.quantity}" min="1" max="${item.book.quantity}" style="width: 55px; text-align: center; padding: 4px 6px;">
<button type="submit" style="padding: 4px 8px; font-size: 12px; background: #456c58;">Sửa</button>
</form>

<form method="post" action="${pageContext.request.contextPath}/cart/update" class="inline">
<input type="hidden" name="csrf" value="${sessionScope.csrf}">
<input type="hidden" name="bookId" value="${item.book.id}">
<input type="hidden" name="quantity" value="${item.quantity + 1}">
<button type="submit" style="padding: 4px 10px; font-size: 13px;" ${item.quantity >= item.book.quantity ? 'disabled' : ''}>+</button>
</form>
</div>
</td>
<td style="font-weight: 700; color: #1e473b; font-size: 15px;">
<c:out value="${item.subtotal}"/>
</td>
<td style="text-align: center;">
<form method="post" action="${pageContext.request.contextPath}/cart/remove" class="inline">
<input type="hidden" name="csrf" value="${sessionScope.csrf}">
<input type="hidden" name="bookId" value="${item.book.id}">
<button type="submit" class="danger" onclick="return confirm('Bạn có chắc muốn xóa sách này khỏi giỏ?');">Xóa</button>
</form>
</td>
</tr>
</c:forEach>
</tbody>
</table>
</div>

<div style="display: flex; justify-content: flex-end; margin-top: 20px;">
<div class="panel" style="max-width: 400px; width: 100%; margin-top: 0;">
<h3 style="margin-bottom: 16px;">Tổng kết đơn hàng</h3>
<div style="display: flex; justify-content: space-between; margin-bottom: 12px; font-size: 16px;">
<span>Tạm tính:</span>
<span style="font-weight: 600;"><c:out value="${total}"/></span>
</div>
<div style="display: flex; justify-content: space-between; margin-bottom: 20px; font-size: 18px; font-weight: 700; color: #1e473b; border-top: 1px solid #dce2d7; padding-top: 12px;">
<span>Tổng thanh toán:</span>
<span style="color: #b27844;"><c:out value="${total}"/></span>
</div>
<a class="button" href="${pageContext.request.contextPath}/checkout" style="display: block; text-align: center; font-size: 16px; padding: 14px;">
Tiến hành thanh toán (COD) →
</a>
</div>
</div>
</c:otherwise>
</c:choose>
</body>
</html>

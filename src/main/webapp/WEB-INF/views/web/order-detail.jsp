<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Chi tiết đơn hàng #${order.id}</title>
<style>
.status-badge {
  display: inline-block;
  padding: 4px 10px;
  border-radius: 4px;
  font-size: 13px;
  font-weight: 600;
  background: #e9eee6;
  color: #223b34;
}
.status-NEW { background: #e0eef7; color: #1e557b; }
.status-CONFIRMED { background: #e3f2fd; color: #1565c0; }
.status-PREPARING { background: #fff3e0; color: #b76e00; }
.status-SHIPPING { background: #f3e5f5; color: #6a1b9a; }
.status-DELIVERING { background: #e8eaf6; color: #283593; }
.status-DELIVERED { background: #e8f5e9; color: #2e7d32; }
.status-CANCELLED { background: #ffebee; color: #c62828; }
.status-RETURNED { background: #eceff1; color: #455a64; }
</style>
</head>
<body>
<div style="margin-bottom: 24px;">
<a class="back" href="${pageContext.request.contextPath}/orders">← Quay lại danh sách đơn hàng</a>
</div>

<div class="section-head">
<h1>Chi tiết đơn hàng #<c:out value="${order.id}"/></h1>
<span class="status-badge status-${order.status}">
<c:out value="${order.statusLabel}"/>
</span>
</div>

<div class="panel form-panel" style="max-width: 100%; margin-bottom: 28px;">
<h3 style="border-bottom: 1px solid #e0e5dd; padding-bottom: 12px; margin-bottom: 18px;">
Thông tin đơn hàng
</h3>

<dl style="grid-template-columns: 180px 1fr; gap: 12px 20px;">
<dt>Mã đơn hàng:</dt>
<dd style="font-weight: 700; color: #1e473b;">#<c:out value="${order.id}"/></dd>

<dt>Ngày đặt:</dt>
<dd><c:out value="${order.orderDate}"/></dd>

<dt>Trạng thái hiện tại:</dt>
<dd>
<span class="status-badge status-${order.status}">
<c:out value="${order.statusLabel}"/>
</span>
</dd>

<dt>Phương thức thanh toán:</dt>
<dd>
<strong><c:out value="${order.paymentMethod}"/></strong> - Thanh toán khi nhận hàng
</dd>

<dt>Người nhận:</dt>
<dd><strong><c:out value="${order.receiverName}"/></strong></dd>

<dt>Số điện thoại:</dt>
<dd><c:out value="${order.receiverPhone}"/></dd>

<dt>Địa chỉ giao hàng:</dt>
<dd><c:out value="${order.shippingAddress}"/></dd>

<dt>Tổng tiền thanh toán:</dt>
<dd style="font-size: 20px; font-weight: 700; color: #b27844;"><c:out value="${order.totalAmount}"/></dd>
</dl>
</div>

<div class="panel" style="margin-top: 0;">
<h3 style="border-bottom: 1px solid #e0e5dd; padding-bottom: 12px; margin-bottom: 18px;">
Danh sách sản phẩm đã đặt
</h3>

<div class="table-wrap">
<table>
<thead>
<tr>
<th style="width: 80px;">Ảnh</th>
<th>Tên sách</th>
<th style="width: 130px; text-align: right;">Đơn giá lúc mua</th>
<th style="width: 100px; text-align: center;">Số lượng</th>
<th style="width: 140px; text-align: right;">Thành tiền</th>
</tr>
</thead>
<tbody>
<c:forEach items="${order.items}" var="item">
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
</td>
<td style="text-align: right; font-weight: 600; color: #b27844;">
<c:out value="${item.unitPrice}"/>
</td>
<td style="text-align: center;">
<c:out value="${item.quantity}"/>
</td>
<td style="text-align: right; font-weight: 700; color: #1e473b; font-size: 15px;">
<c:out value="${item.subtotal}"/>
</td>
</tr>
</c:forEach>
</tbody>
</table>
</div>

<div style="display: flex; justify-content: flex-end; margin-top: 20px;">
<div style="text-align: right; font-size: 18px; font-weight: 700; color: #1e473b;">
Tổng cộng: <span style="color: #b27844;"><c:out value="${order.totalAmount}"/></span>
</div>
</div>
</div>
</body>
</html>

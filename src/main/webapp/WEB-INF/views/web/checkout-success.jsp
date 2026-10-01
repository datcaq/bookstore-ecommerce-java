<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Đặt hàng thành công</title>
</head>
<body>
<div class="panel form-panel" style="text-align: center; padding: 42px 36px;">
<div style="width: 68px; height: 68px; background: #e4f0df; color: #356138; border-radius: 50%; display: flex; align-items: center; justify-content: center; font-size: 36px; margin: 0 auto 20px;">
✓
</div>

<h1 style="color: #1e473b; margin-bottom: 8px;">Đặt hàng thành công!</h1>
<p class="muted" style="margin-bottom: 28px;">
Cảm ơn bạn đã đặt hàng. Đơn hàng của bạn đang được xử lý và sẽ được giao trong thời gian sớm nhất.
</p>

<div class="panel" style="text-align: left; background: #fafbf9; border: 1px solid #dce2d7; margin-bottom: 28px;">
<h3 style="border-bottom: 1px solid #e0e5dd; padding-bottom: 12px; margin-bottom: 16px;">
Thông tin đơn hàng #${order.id}
</h3>

<dl style="grid-template-columns: 160px 1fr; gap: 10px 16px;">
<dt>Mã đơn hàng:</dt>
<dd style="font-weight: 700; color: #1e473b;">#<c:out value="${order.id}"/></dd>

<dt>Ngày đặt:</dt>
<dd><c:out value="${order.orderDate}"/></dd>

<dt>Phương thức thanh toán:</dt>
<dd><strong>COD</strong> - Thanh toán khi nhận hàng</dd>

<dt>Trạng thái đơn hàng:</dt>
<dd><span style="display: inline-block; background: #dce9df; color: #1e473b; padding: 3px 10px; border-radius: 4px; font-weight: 600; font-size: 13px;"><c:out value="${order.statusLabel}"/></span></dd>

<dt>Người nhận:</dt>
<dd><c:out value="${order.receiverName}"/></dd>

<dt>Số điện thoại:</dt>
<dd><c:out value="${order.receiverPhone}"/></dd>

<dt>Địa chỉ giao hàng:</dt>
<dd><c:out value="${order.shippingAddress}"/></dd>

<dt>Tổng tiền thanh toán:</dt>
<dd style="font-size: 18px; font-weight: 700; color: #b27844;"><c:out value="${order.totalAmount}"/></dd>
</dl>
</div>

<c:if test="${not empty order.items}">
<div class="panel" style="text-align: left; background: #ffffff; border: 1px solid #dce2d7; margin-bottom: 28px;">
<h3 style="border-bottom: 1px solid #e0e5dd; padding-bottom: 12px; margin-bottom: 16px;">
Danh sách sản phẩm đã đặt
</h3>
<table style="width: 100%;">
<thead>
<tr>
<th>Sản phẩm</th>
<th style="text-align: center; width: 100px;">Số lượng</th>
<th style="text-align: right; width: 120px;">Đơn giá</th>
<th style="text-align: right; width: 120px;">Thành tiền</th>
</tr>
</thead>
<tbody>
<c:forEach items="${order.items}" var="it">
<tr>
<td><c:out value="${it.book.title}"/></td>
<td style="text-align: center;"><c:out value="${it.quantity}"/></td>
<td style="text-align: right;"><c:out value="${it.unitPrice}"/></td>
<td style="text-align: right; font-weight: 600; color: #1e473b;"><c:out value="${it.subtotal}"/></td>
</tr>
</c:forEach>
</tbody>
</table>
</div>
</c:if>

<div style="display: flex; justify-content: center; gap: 16px; flex-wrap: wrap;">
<a class="button" href="${pageContext.request.contextPath}/orders" style="background: #ffffff; color: #245742; border: 1px solid #245742; padding: 12px 24px; font-size: 15px;">
Xem lịch sử đơn hàng
</a>
<a class="button" href="${pageContext.request.contextPath}/home" style="padding: 12px 28px; font-size: 15px;">
Tiếp tục mua sắm
</a>
</div>
</div>
</body>
</html>

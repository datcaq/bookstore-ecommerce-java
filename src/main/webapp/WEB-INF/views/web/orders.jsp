<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Lịch sử đơn hàng</title>
<style>
.status-tabs {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
  margin-bottom: 24px;
}
.status-tab {
  display: inline-block;
  padding: 8px 14px;
  background: #ffffff;
  color: #245742;
  border: 1px solid #d9e0d7;
  border-radius: 4px;
  font-size: 13px;
  font-weight: 600;
  text-decoration: none;
}
.status-tab:hover {
  background: #eaf0e5;
  text-decoration: none;
}
.status-tab.active {
  background: #245742;
  color: #ffffff;
  border-color: #245742;
}
.status-badge {
  display: inline-block;
  padding: 4px 10px;
  border-radius: 4px;
  font-size: 12px;
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
<a class="back" href="${pageContext.request.contextPath}/home">← Trở về trang chủ</a>
</div>

<div class="section-head">
<h1>Lịch sử đơn hàng</h1>
<c:if test="${not empty orders}">
<span class="muted">${fn:length(orders)} đơn hàng</span>
</c:if>
</div>

<!-- Bộ lọc trạng thái đơn hàng -->
<div class="status-tabs">
<a href="${pageContext.request.contextPath}/orders" 
   class="status-tab ${empty selectedStatus ? 'active' : ''}">
Tất cả
</a>
<c:forEach items="${statusMap}" var="entry">
<a href="${pageContext.request.contextPath}/orders?status=${entry.key}" 
   class="status-tab ${selectedStatus eq entry.key ? 'active' : ''}">
<c:out value="${entry.value}"/>
</a>
</c:forEach>
</div>

<c:choose>
<c:when test="${empty orders}">
<div class="empty">
<c:choose>
<c:when test="${empty selectedStatus}">
<p style="margin-bottom: 12px;">Bạn chưa có đơn hàng nào.</p>
<a class="button" href="${pageContext.request.contextPath}/home">Khám phá sách ngay</a>
</c:when>
<c:otherwise>
<p style="margin-bottom: 12px;">Không có đơn hàng ở trạng thái này.</p>
<a class="button" href="${pageContext.request.contextPath}/orders">Xem tất cả đơn hàng</a>
</c:otherwise>
</c:choose>
</div>
</c:when>
<c:otherwise>
<div class="table-wrap">
<table>
<thead>
<tr>
<th style="width: 90px;">Mã đơn</th>
<th style="width: 160px;">Ngày đặt</th>
<th>Người nhận</th>
<th style="width: 120px;">Số điện thoại</th>
<th style="width: 120px;">Tổng tiền</th>
<th style="width: 100px;">Thanh toán</th>
<th style="width: 140px; text-align: center;">Trạng thái</th>
<th style="width: 110px; text-align: center;">Thao tác</th>
</tr>
</thead>
<tbody>
<c:forEach items="${orders}" var="ord">
<tr>
<td style="font-weight: 700; color: #1e473b;">
#<c:out value="${ord.id}"/>
</td>
<td>
<c:out value="${ord.orderDate}"/>
</td>
<td>
<strong><c:out value="${ord.receiverName}"/></strong>
</td>
<td>
<c:out value="${ord.receiverPhone}"/>
</td>
<td style="font-weight: 700; color: #b27844;">
<c:out value="${ord.totalAmount}"/>
</td>
<td>
<c:out value="${ord.paymentMethod}"/>
</td>
<td style="text-align: center;">
<span class="status-badge status-${ord.status}">
<c:out value="${ord.statusLabel}"/>
</span>
</td>
<td style="text-align: center;">
<a class="button" href="${pageContext.request.contextPath}/orders/detail?id=${ord.id}" style="padding: 6px 12px; font-size: 13px;">
Chi tiết
</a>
</td>
</tr>
</c:forEach>
</tbody>
</table>
</div>
</c:otherwise>
</c:choose>
</body>
</html>

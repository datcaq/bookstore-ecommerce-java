<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Thanh toán đơn hàng</title>
</head>
<body>
<div style="margin-bottom: 24px;">
<a class="back" href="${pageContext.request.contextPath}/cart">← Quay lại giỏ hàng</a>
</div>

<c:if test="${not empty error}">
<div class="alert error" role="alert">
<c:out value="${error}"/>
</div>
</c:if>

<div class="section-head">
<h1>Thanh toán đơn hàng (COD)</h1>
</div>

<div class="stats" style="grid-template-columns: 1.2fr 1fr; gap: 30px; align-items: start;">
<div class="panel" style="margin-top: 0;">
<h3>Thông tin nhận hàng</h3>
<form method="post" action="${pageContext.request.contextPath}/checkout">
<input type="hidden" name="csrf" value="${sessionScope.csrf}">

<label for="receiverName">Họ tên người nhận <span style="color: #a44a36;">*</span></label>
<input type="text" id="receiverName" name="receiverName" maxlength="100" required 
       value="<c:out value="${not empty receiverName ? receiverName : sessionScope.account.fullname}"/>" 
       placeholder="Nguyễn Văn A">

<label for="receiverPhone">Số điện thoại người nhận <span style="color: #a44a36;">*</span></label>
<input type="text" id="receiverPhone" name="receiverPhone" maxlength="20" required 
       value="<c:out value="${not empty receiverPhone ? receiverPhone : (sessionScope.account.phone != null ? '0' : '')}"/>" 
       placeholder="0912345678">

<label for="shippingAddress">Địa chỉ nhận hàng <span style="color: #a44a36;">*</span></label>
<textarea id="shippingAddress" name="shippingAddress" rows="3" maxlength="255" required 
          placeholder="Số nhà, tên đường, phường/xã, quận/huyện, tỉnh/thành phố"><c:out value="${shippingAddress}"/></textarea>

<fieldset style="margin: 24px 0 16px; padding: 18px; border: 1px solid #dce2d7; border-radius: 6px; background: #fafbf9;">
<legend style="padding: 0 8px; font-weight: 600; color: #245742;">Phương thức thanh toán</legend>
<div class="check" style="margin-top: 6px;">
<input type="radio" id="payment_cod" name="payment_method" value="COD" checked disabled>
<label for="payment_cod" style="margin: 0; font-weight: 600;">
COD - Thanh toán khi nhận hàng
</label>
</div>
<p class="muted" style="margin: 8px 0 0 24px; font-size: 13px;">
Thanh toán bằng tiền mặt trực tiếp cho nhân viên giao hàng khi nhận được sách.
</p>
</fieldset>

<button type="submit" style="width: 100%; font-size: 16px; padding: 14px; margin-top: 10px;">
Xác nhận đặt hàng COD
</button>
</form>
</div>

<div class="panel" style="margin-top: 0; background: #ffffff;">
<h3>Tóm tắt đơn hàng</h3>
<div style="border-bottom: 1px solid #e5e8df; padding-bottom: 14px; margin-bottom: 14px;">
<c:forEach items="${cart.items}" var="item">
<div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 12px;">
<div style="flex: 1; padding-right: 12px;">
<div style="font-weight: 600;"><c:out value="${item.book.title}"/></div>
<div class="muted" style="font-size: 13px;">
<c:out value="${item.quantity}"/> x <c:out value="${item.book.price}"/>
</div>
</div>
<div style="font-weight: 600; color: #1e473b; white-space: nowrap;">
<c:out value="${item.subtotal}"/>
</div>
</div>
</c:forEach>
</div>

<div style="display: flex; justify-content: space-between; margin-bottom: 10px; font-size: 15px;">
<span>Tạm tính:</span>
<span style="font-weight: 600;"><c:out value="${total}"/></span>
</div>
<div style="display: flex; justify-content: space-between; margin-bottom: 16px; font-size: 15px;">
<span>Phí vận chuyển:</span>
<span style="font-weight: 600; color: #245742;">Miễn phí</span>
</div>
<div style="display: flex; justify-content: space-between; font-size: 19px; font-weight: 700; color: #1e473b; border-top: 1px solid #dce2d7; padding-top: 14px;">
<span>Tổng cộng:</span>
<span style="color: #b27844;"><c:out value="${total}"/></span>
</div>
</div>
</div>
</body>
</html>

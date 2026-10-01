USE BookStoreGK_24162019;
GO

-- =======================================================================
-- BÀI TẬP 11 - MỤC 3: SCRIPT MẪU ĐỔI TRẠNG THÁI ĐƠN HÀNG ĐỂ KIỂM THỬ
-- Sinh viên: Cao Quốc Đạt - MSSV: 24162019
-- =======================================================================
-- Hướng dẫn kiểm thử:
-- 1. Đặt hàng qua giao diện để tạo Order mới (mặc định status = 'NEW').
-- 2. Chạy từng lệnh UPDATE dưới đây trong SSMS để đổi trạng thái đơn hàng (ví dụ cho order_id = 1).
-- 3. Quay lại trình duyệt và F5/reload trang /orders hoặc /orders/detail?id=1 để quan sát.
-- =======================================================================

-- 1. Đổi thành: "Đã xác nhận"
-- UPDATE orders SET status = 'CONFIRMED' WHERE order_id = 1;

-- 2. Đổi thành: "Chuẩn bị hàng"
-- UPDATE orders SET status = 'PREPARING' WHERE order_id = 1;

-- 3. Đổi thành: "Vận chuyển"
-- UPDATE orders SET status = 'SHIPPING' WHERE order_id = 1;

-- 4. Đổi thành: "Giao hàng"
-- UPDATE orders SET status = 'DELIVERING' WHERE order_id = 1;

-- 5. Đổi thành: "Đã giao"
-- UPDATE orders SET status = 'DELIVERED' WHERE order_id = 1;

-- 6. Đổi thành: "Đơn hàng hủy"
-- UPDATE orders SET status = 'CANCELLED' WHERE order_id = 1;

-- 7. Đổi thành: "Đơn hàng hoàn"
-- UPDATE orders SET status = 'RETURNED' WHERE order_id = 1;

-- 8. Trở về trạng thái ban đầu: "Đơn hàng mới"
-- UPDATE orders SET status = 'NEW' WHERE order_id = 1;

-- Xem danh sách tất cả các đơn hàng hiện có
SELECT order_id, user_id, order_date, total_amount, payment_method, status, receiver_name, receiver_phone, shipping_address 
FROM orders 
ORDER BY order_date DESC;
GO

package vn.edu.hcmute.kiemtragk.dao;

import vn.edu.hcmute.kiemtragk.entity.Order_24162019;

public interface OrderDAO_24162019 {
  Order_24162019 createOrderFromCart(
      int userId, String receiverName, String receiverPhone, String shippingAddress);

  Order_24162019 findById(int orderId);
}

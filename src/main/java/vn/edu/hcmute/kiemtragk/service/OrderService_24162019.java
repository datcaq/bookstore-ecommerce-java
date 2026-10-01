package vn.edu.hcmute.kiemtragk.service;

import java.util.List;
import vn.edu.hcmute.kiemtragk.entity.Order_24162019;

public interface OrderService_24162019 {
  Order_24162019 checkout(
      int userId, String receiverName, String receiverPhone, String shippingAddress);

  Order_24162019 findById(int orderId);

  List<Order_24162019> getOrderHistory(int userId, String status);

  Order_24162019 getOrderDetail(int orderId, int userId);
}

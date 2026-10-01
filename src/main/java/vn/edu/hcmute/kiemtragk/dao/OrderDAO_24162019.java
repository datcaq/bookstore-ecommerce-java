package vn.edu.hcmute.kiemtragk.dao;

import java.util.List;
import vn.edu.hcmute.kiemtragk.entity.Order_24162019;

public interface OrderDAO_24162019 {
  Order_24162019 createOrderFromCart(
      int userId, String receiverName, String receiverPhone, String shippingAddress);

  Order_24162019 findById(int orderId);

  List<Order_24162019> findByUser(int userId);

  List<Order_24162019> findByUserAndStatus(int userId, String status);

  Order_24162019 findByIdAndUser(int orderId, int userId);
}

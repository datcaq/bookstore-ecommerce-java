package vn.edu.hcmute.kiemtragk.service.impl;

import vn.edu.hcmute.kiemtragk.dao.OrderDAO_24162019;
import vn.edu.hcmute.kiemtragk.dao.impl.OrderDAOImpl_24162019;
import vn.edu.hcmute.kiemtragk.entity.Order_24162019;
import vn.edu.hcmute.kiemtragk.service.OrderService_24162019;
import vn.edu.hcmute.kiemtragk.util.Validation_24162019;

public class OrderServiceImpl_24162019 implements OrderService_24162019 {
  private final OrderDAO_24162019 dao;

  public OrderServiceImpl_24162019() {
    this(new OrderDAOImpl_24162019());
  }

  public OrderServiceImpl_24162019(OrderDAO_24162019 d) {
    this.dao = d;
  }

  @Override
  public Order_24162019 checkout(
      int userId, String receiverName, String receiverPhone, String shippingAddress) {
    String name = Validation_24162019.required(receiverName, 100, "Họ tên người nhận");
    String phone = Validation_24162019.required(receiverPhone, 20, "Số điện thoại");
    String address = Validation_24162019.required(shippingAddress, 255, "Địa chỉ giao hàng");

    if (!phone.matches("^0[0-9]{8,11}$")) {
      throw new IllegalArgumentException("Số điện thoại không hợp lệ (cần bắt đầu bằng số 0, gồm 9-12 chữ số).");
    }

    return dao.createOrderFromCart(userId, name, phone, address);
  }

  @Override
  public Order_24162019 findById(int orderId) {
    return dao.findById(orderId);
  }
}

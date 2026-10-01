package vn.edu.hcmute.kiemtragk.dao.impl;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import vn.edu.hcmute.kiemtragk.dao.OrderDAO_24162019;
import vn.edu.hcmute.kiemtragk.entity.*;

public class OrderDAOImpl_24162019 extends BaseDAO_24162019 implements OrderDAO_24162019 {
  public OrderDAOImpl_24162019() {}

  public OrderDAOImpl_24162019(EntityManagerFactory f) {
    super(f);
  }

  @Override
  public Order_24162019 createOrderFromCart(
      int userId, String receiverName, String receiverPhone, String shippingAddress) {
    return write(
        em -> {
          User_24162019 user = em.find(User_24162019.class, userId);
          if (user == null) {
            throw new IllegalArgumentException("Tài khoản không tồn tại.");
          }

          Cart_24162019 cart =
              em.createQuery("select c from Cart c where c.user.id=:uid", Cart_24162019.class)
                  .setParameter("uid", userId)
                  .getResultStream()
                  .findFirst()
                  .orElse(null);

          if (cart == null) {
            throw new IllegalArgumentException("Giỏ hàng đang trống.");
          }

          List<CartItem_24162019> cartItems =
              em.createQuery(
                      "select ci from CartItem ci join fetch ci.book where ci.cart.id=:cid",
                      CartItem_24162019.class)
                  .setParameter("cid", cart.getId())
                  .getResultList();

          if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("Giỏ hàng đang trống.");
          }

          // Validate stock for all items first with pessimistic lock
          for (CartItem_24162019 ci : cartItems) {
            Book_24162019 book =
                em.find(Book_24162019.class, ci.getBook().getId(), LockModeType.PESSIMISTIC_WRITE);
            if (book == null) {
              throw new IllegalArgumentException("Sách '" + ci.getBook().getTitle() + "' không còn tồn tại.");
            }
            int stock = book.getQuantity() != null ? book.getQuantity() : 0;
            if (stock < ci.getQuantity()) {
              throw new IllegalArgumentException(
                  "Sách '"
                      + book.getTitle()
                      + "' không đủ số lượng trong kho (còn "
                      + stock
                      + ", yêu cầu "
                      + ci.getQuantity()
                      + ").");
            }
          }

          // Create Order
          Order_24162019 order = new Order_24162019();
          order.setUser(user);
          order.setOrderDate(LocalDateTime.now());
          order.setPaymentMethod("COD");
          order.setStatus("NEW");
          order.setReceiverName(receiverName);
          order.setReceiverPhone(receiverPhone);
          order.setShippingAddress(shippingAddress);
          order.setTotalAmount(BigDecimal.ZERO);

          em.persist(order);
          em.flush();

          BigDecimal totalAmount = BigDecimal.ZERO;

          // Create OrderItems and deduct stock
          for (CartItem_24162019 ci : cartItems) {
            Book_24162019 book =
                em.find(Book_24162019.class, ci.getBook().getId(), LockModeType.PESSIMISTIC_WRITE);
            BigDecimal unitPrice = book.getPrice() != null ? book.getPrice() : BigDecimal.ZERO;
            BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(ci.getQuantity()));
            totalAmount = totalAmount.add(subtotal);

            OrderItem_24162019 orderItem =
                new OrderItem_24162019(order, book, ci.getQuantity(), unitPrice, subtotal);
            em.persist(orderItem);

            // Deduct stock
            book.setQuantity(book.getQuantity() - ci.getQuantity());
          }

          order.setTotalAmount(totalAmount);

          // Clear cart items
          em.createQuery("delete from CartItem ci where ci.cart.id=:cid")
              .setParameter("cid", cart.getId())
              .executeUpdate();

          cart.setUpdatedAt(LocalDateTime.now());

          return order;
        });
  }

  @Override
  public Order_24162019 findById(int orderId) {
    return read(
        em -> {
          Order_24162019 order = em.find(Order_24162019.class, orderId);
          if (order != null) {
            List<OrderItem_24162019> items =
                em.createQuery(
                        "select oi from OrderItem oi join fetch oi.book where oi.order.id=:oid",
                        OrderItem_24162019.class)
                    .setParameter("oid", orderId)
                    .getResultList();
            order.setItems(items);
          }
          return order;
        });
  }

  @Override
  public List<Order_24162019> findByUser(int userId) {
    return read(
        em ->
            em.createQuery(
                    "select o from Order o where o.user.id=:uid order by o.orderDate desc, o.id desc",
                    Order_24162019.class)
                .setParameter("uid", userId)
                .getResultList());
  }

  @Override
  public List<Order_24162019> findByUserAndStatus(int userId, String status) {
    return read(
        em ->
            em.createQuery(
                    "select o from Order o where o.user.id=:uid and o.status=:status order by o.orderDate desc, o.id desc",
                    Order_24162019.class)
                .setParameter("uid", userId)
                .setParameter("status", status)
                .getResultList());
  }

  @Override
  public Order_24162019 findByIdAndUser(int orderId, int userId) {
    return read(
        em -> {
          Order_24162019 order =
              em.createQuery(
                      "select o from Order o where o.id=:oid and o.user.id=:uid",
                      Order_24162019.class)
                  .setParameter("oid", orderId)
                  .setParameter("uid", userId)
                  .getResultStream()
                  .findFirst()
                  .orElse(null);

          if (order != null) {
            List<OrderItem_24162019> items =
                em.createQuery(
                        "select oi from OrderItem oi join fetch oi.book where oi.order.id=:oid",
                        OrderItem_24162019.class)
                    .setParameter("oid", orderId)
                    .getResultList();
            order.setItems(items);
          }
          return order;
        });
  }
}

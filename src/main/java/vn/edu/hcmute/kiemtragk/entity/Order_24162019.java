package vn.edu.hcmute.kiemtragk.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import vn.edu.hcmute.kiemtragk.util.OrderStatusUtil_24162019;

@Entity(name = "Order")
@Table(name = "orders")
public class Order_24162019 implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_id")
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User_24162019 user;

  @Column(name = "order_date", nullable = false)
  private LocalDateTime orderDate;

  @Column(name = "total_amount", precision = 10, scale = 2, nullable = false)
  private BigDecimal totalAmount;

  @Column(name = "payment_method", length = 50, nullable = false)
  private String paymentMethod;

  @Column(name = "status", length = 50, nullable = false)
  private String status;

  @Column(name = "receiver_name", length = 100, nullable = false)
  @org.hibernate.annotations.Nationalized
  private String receiverName;

  @Column(name = "receiver_phone", length = 20, nullable = false)
  private String receiverPhone;

  @Column(name = "shipping_address", length = 255, nullable = false)
  @org.hibernate.annotations.Nationalized
  private String shippingAddress;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<OrderItem_24162019> items = new ArrayList<>();

  public Order_24162019() {}

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public User_24162019 getUser() {
    return user;
  }

  public void setUser(User_24162019 user) {
    this.user = user;
  }

  public LocalDateTime getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(LocalDateTime orderDate) {
    this.orderDate = orderDate;
  }

  public BigDecimal getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(BigDecimal totalAmount) {
    this.totalAmount = totalAmount;
  }

  public String getPaymentMethod() {
    return paymentMethod;
  }

  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getStatusLabel() {
    return OrderStatusUtil_24162019.getLabel(this.status);
  }

  public String getReceiverName() {
    return receiverName;
  }

  public void setReceiverName(String receiverName) {
    this.receiverName = receiverName;
  }

  public String getReceiverPhone() {
    return receiverPhone;
  }

  public void setReceiverPhone(String receiverPhone) {
    this.receiverPhone = receiverPhone;
  }

  public String getShippingAddress() {
    return shippingAddress;
  }

  public void setShippingAddress(String shippingAddress) {
    this.shippingAddress = shippingAddress;
  }

  public List<OrderItem_24162019> getItems() {
    return items;
  }

  public void setItems(List<OrderItem_24162019> items) {
    this.items = items;
  }
}

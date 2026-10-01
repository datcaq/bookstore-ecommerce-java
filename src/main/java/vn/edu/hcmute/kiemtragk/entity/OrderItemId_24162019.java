package vn.edu.hcmute.kiemtragk.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrderItemId_24162019 implements Serializable {
  private static final long serialVersionUID = 1L;

  @Column(name = "order_id")
  private Integer orderId;

  @Column(name = "book_id")
  private Integer bookId;

  public OrderItemId_24162019() {}

  public OrderItemId_24162019(Integer orderId, Integer bookId) {
    this.orderId = orderId;
    this.bookId = bookId;
  }

  public Integer getOrderId() {
    return orderId;
  }

  public void setOrderId(Integer orderId) {
    this.orderId = orderId;
  }

  public Integer getBookId() {
    return bookId;
  }

  public void setBookId(Integer bookId) {
    this.bookId = bookId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof OrderItemId_24162019)) return false;
    OrderItemId_24162019 that = (OrderItemId_24162019) o;
    return Objects.equals(orderId, that.orderId) && Objects.equals(bookId, that.bookId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(orderId, bookId);
  }
}

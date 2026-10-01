package vn.edu.hcmute.kiemtragk.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity(name = "OrderItem")
@Table(name = "order_item")
public class OrderItem_24162019 implements Serializable {
  private static final long serialVersionUID = 1L;

  @EmbeddedId
  private OrderItemId_24162019 id;

  @MapsId("orderId")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  private Order_24162019 order;

  @MapsId("bookId")
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "book_id")
  private Book_24162019 book;

  @Column(nullable = false)
  private Integer quantity;

  @Column(name = "unit_price", precision = 6, scale = 2, nullable = false)
  private BigDecimal unitPrice;

  @Column(precision = 10, scale = 2, nullable = false)
  private BigDecimal subtotal;

  public OrderItem_24162019() {}

  public OrderItem_24162019(Order_24162019 order, Book_24162019 book, Integer quantity, BigDecimal unitPrice, BigDecimal subtotal) {
    this.order = order;
    this.book = book;
    this.quantity = quantity;
    this.unitPrice = unitPrice;
    this.subtotal = subtotal;
    if (order != null && book != null) {
      this.id = new OrderItemId_24162019(order.getId(), book.getId());
    }
  }

  public OrderItemId_24162019 getId() {
    return id;
  }

  public void setId(OrderItemId_24162019 id) {
    this.id = id;
  }

  public Order_24162019 getOrder() {
    return order;
  }

  public void setOrder(Order_24162019 order) {
    this.order = order;
  }

  public Book_24162019 getBook() {
    return book;
  }

  public void setBook(Book_24162019 book) {
    this.book = book;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public BigDecimal getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
  }

  public BigDecimal getSubtotal() {
    return subtotal;
  }

  public void setSubtotal(BigDecimal subtotal) {
    this.subtotal = subtotal;
  }
}

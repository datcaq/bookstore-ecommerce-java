package vn.edu.hcmute.kiemtragk.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity(name = "CartItem")
@Table(name = "cart_item")
public class CartItem_24162019 implements Serializable {
  private static final long serialVersionUID = 1L;

  @EmbeddedId
  private CartItemId_24162019 id;

  @MapsId("cartId")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cart_id")
  private Cart_24162019 cart;

  @MapsId("bookId")
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "book_id")
  private Book_24162019 book;

  @Column(nullable = false)
  private Integer quantity;

  public CartItem_24162019() {}

  public CartItem_24162019(Cart_24162019 cart, Book_24162019 book, Integer quantity) {
    this.cart = cart;
    this.book = book;
    this.quantity = quantity;
    if (cart != null && book != null) {
      this.id = new CartItemId_24162019(cart.getId(), book.getId());
    }
  }

  public CartItemId_24162019 getId() {
    return id;
  }

  public void setId(CartItemId_24162019 id) {
    this.id = id;
  }

  public Cart_24162019 getCart() {
    return cart;
  }

  public void setCart(Cart_24162019 cart) {
    this.cart = cart;
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

  public BigDecimal getSubtotal() {
    if (book != null && book.getPrice() != null && quantity != null) {
      return book.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
    return BigDecimal.ZERO;
  }
}

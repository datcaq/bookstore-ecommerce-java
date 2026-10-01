package vn.edu.hcmute.kiemtragk.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CartItemId_24162019 implements Serializable {
  private static final long serialVersionUID = 1L;

  @Column(name = "cart_id")
  private Integer cartId;

  @Column(name = "book_id")
  private Integer bookId;

  public CartItemId_24162019() {}

  public CartItemId_24162019(Integer cartId, Integer bookId) {
    this.cartId = cartId;
    this.bookId = bookId;
  }

  public Integer getCartId() {
    return cartId;
  }

  public void setCartId(Integer cartId) {
    this.cartId = cartId;
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
    if (!(o instanceof CartItemId_24162019)) return false;
    CartItemId_24162019 that = (CartItemId_24162019) o;
    return Objects.equals(cartId, that.cartId) && Objects.equals(bookId, that.bookId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cartId, bookId);
  }
}

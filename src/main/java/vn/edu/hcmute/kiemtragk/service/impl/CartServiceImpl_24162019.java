package vn.edu.hcmute.kiemtragk.service.impl;

import java.math.BigDecimal;
import vn.edu.hcmute.kiemtragk.dao.CartDAO_24162019;
import vn.edu.hcmute.kiemtragk.dao.impl.CartDAOImpl_24162019;
import vn.edu.hcmute.kiemtragk.entity.CartItem_24162019;
import vn.edu.hcmute.kiemtragk.entity.Cart_24162019;
import vn.edu.hcmute.kiemtragk.service.CartService_24162019;

public class CartServiceImpl_24162019 implements CartService_24162019 {
  private final CartDAO_24162019 dao;

  public CartServiceImpl_24162019() {
    this(new CartDAOImpl_24162019());
  }

  public CartServiceImpl_24162019(CartDAO_24162019 d) {
    this.dao = d;
  }

  @Override
  public Cart_24162019 getCartByUser(int userId) {
    return dao.findByUserId(userId);
  }

  @Override
  public void addToCart(int userId, int bookId, int quantity) {
    if (quantity < 1) {
      throw new IllegalArgumentException("Số lượng phải từ 1 trở lên.");
    }
    dao.addToCart(userId, bookId, quantity);
  }

  @Override
  public void updateQuantity(int userId, int bookId, int quantity) {
    dao.updateQuantity(userId, bookId, quantity);
  }

  @Override
  public void removeItem(int userId, int bookId) {
    dao.removeItem(userId, bookId);
  }

  @Override
  public void clearCart(int userId) {
    dao.clearCart(userId);
  }

  @Override
  public BigDecimal calculateTotal(Cart_24162019 cart) {
    if (cart == null || cart.getItems() == null || cart.getItems().isEmpty()) {
      return BigDecimal.ZERO;
    }
    BigDecimal total = BigDecimal.ZERO;
    for (CartItem_24162019 item : cart.getItems()) {
      if (item.getSubtotal() != null) {
        total = total.add(item.getSubtotal());
      }
    }
    return total;
  }

  @Override
  public int countItems(int userId) {
    return dao.countItems(userId);
  }
}

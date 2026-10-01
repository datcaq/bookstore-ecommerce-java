package vn.edu.hcmute.kiemtragk.service;

import java.math.BigDecimal;
import vn.edu.hcmute.kiemtragk.entity.Cart_24162019;

public interface CartService_24162019 {
  Cart_24162019 getCartByUser(int userId);

  void addToCart(int userId, int bookId, int quantity);

  void updateQuantity(int userId, int bookId, int quantity);

  void removeItem(int userId, int bookId);

  void clearCart(int userId);

  BigDecimal calculateTotal(Cart_24162019 cart);

  int countItems(int userId);
}

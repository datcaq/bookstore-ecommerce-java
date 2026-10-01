package vn.edu.hcmute.kiemtragk.dao;

import vn.edu.hcmute.kiemtragk.entity.Cart_24162019;

public interface CartDAO_24162019 {
  Cart_24162019 findByUserId(int userId);

  void addToCart(int userId, int bookId, int quantity);

  void updateQuantity(int userId, int bookId, int quantity);

  void removeItem(int userId, int bookId);

  void clearCart(int userId);

  int countItems(int userId);
}

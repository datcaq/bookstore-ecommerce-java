package vn.edu.hcmute.kiemtragk.dao.impl;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import vn.edu.hcmute.kiemtragk.dao.CartDAO_24162019;
import vn.edu.hcmute.kiemtragk.entity.*;

public class CartDAOImpl_24162019 extends BaseDAO_24162019 implements CartDAO_24162019 {
  public CartDAOImpl_24162019() {}

  public CartDAOImpl_24162019(EntityManagerFactory f) {
    super(f);
  }

  private Cart_24162019 getOrCreateCart(EntityManager em, int userId) {
    Cart_24162019 cart =
        em.createQuery("select c from Cart c where c.user.id=:uid", Cart_24162019.class)
            .setParameter("uid", userId)
            .getResultStream()
            .findFirst()
            .orElse(null);

    if (cart == null) {
      User_24162019 user = em.find(User_24162019.class, userId);
      if (user == null) {
        throw new IllegalArgumentException("Tài khoản không tồn tại.");
      }
      cart = new Cart_24162019();
      cart.setUser(user);
      cart.setCreatedAt(LocalDateTime.now());
      cart.setUpdatedAt(LocalDateTime.now());
      em.persist(cart);
      em.flush();
    }
    return cart;
  }

  @Override
  public Cart_24162019 findByUserId(int userId) {
    return read(
        em -> {
          Cart_24162019 cart =
              em.createQuery("select c from Cart c where c.user.id=:uid", Cart_24162019.class)
                  .setParameter("uid", userId)
                  .getResultStream()
                  .findFirst()
                  .orElse(null);

          if (cart == null) {
            // Create if not exists
            cart = write(wEm -> getOrCreateCart(wEm, userId));
            return findByUserId(userId);
          }

          List<CartItem_24162019> items =
              em.createQuery(
                      "select ci from CartItem ci join fetch ci.book where ci.cart.id=:cid order by ci.book.id",
                      CartItem_24162019.class)
                  .setParameter("cid", cart.getId())
                  .getResultList();

          cart.setItems(items);
          return cart;
        });
  }

  @Override
  public void addToCart(int userId, int bookId, int quantity) {
    if (quantity < 1) {
      throw new IllegalArgumentException("Số lượng thêm vào giỏ phải từ 1 trở lên.");
    }
    write(
        em -> {
          Book_24162019 book = em.find(Book_24162019.class, bookId);
          if (book == null) {
            throw new IllegalArgumentException("Sách không tồn tại.");
          }

          int stock = book.getQuantity() != null ? book.getQuantity() : 0;
          if (stock <= 0) {
            throw new IllegalArgumentException("Sách '" + book.getTitle() + "' đã hết hàng.");
          }

          Cart_24162019 cart = getOrCreateCart(em, userId);
          CartItemId_24162019 itemId = new CartItemId_24162019(cart.getId(), bookId);
          CartItem_24162019 item = em.find(CartItem_24162019.class, itemId);

          int targetQuantity = quantity;
          if (item != null) {
            targetQuantity = item.getQuantity() + quantity;
          }

          if (targetQuantity > stock) {
            throw new IllegalArgumentException(
                "Số lượng yêu cầu ("
                    + targetQuantity
                    + ") vượt quá số lượng trong kho (còn "
                    + stock
                    + " cuốn).");
          }

          if (item == null) {
            item = new CartItem_24162019(cart, book, targetQuantity);
            em.persist(item);
          } else {
            item.setQuantity(targetQuantity);
          }

          cart.setUpdatedAt(LocalDateTime.now());
          return null;
        });
  }

  @Override
  public void updateQuantity(int userId, int bookId, int quantity) {
    write(
        em -> {
          Cart_24162019 cart = getOrCreateCart(em, userId);
          CartItemId_24162019 itemId = new CartItemId_24162019(cart.getId(), bookId);
          CartItem_24162019 item = em.find(CartItem_24162019.class, itemId);

          if (item == null) {
            return null;
          }

          if (quantity <= 0) {
            em.remove(item);
          } else {
            Book_24162019 book = em.find(Book_24162019.class, bookId);
            if (book == null) {
              throw new IllegalArgumentException("Sách không tồn tại.");
            }
            int stock = book.getQuantity() != null ? book.getQuantity() : 0;
            if (quantity > stock) {
              throw new IllegalArgumentException(
                  "Số lượng yêu cầu ("
                      + quantity
                      + ") vượt quá số lượng trong kho (còn "
                      + stock
                      + " cuốn).");
            }
            item.setQuantity(quantity);
          }

          cart.setUpdatedAt(LocalDateTime.now());
          return null;
        });
  }

  @Override
  public void removeItem(int userId, int bookId) {
    write(
        em -> {
          Cart_24162019 cart = getOrCreateCart(em, userId);
          CartItemId_24162019 itemId = new CartItemId_24162019(cart.getId(), bookId);
          CartItem_24162019 item = em.find(CartItem_24162019.class, itemId);
          if (item != null) {
            em.remove(item);
            cart.setUpdatedAt(LocalDateTime.now());
          }
          return null;
        });
  }

  @Override
  public void clearCart(int userId) {
    write(
        em -> {
          Cart_24162019 cart = getOrCreateCart(em, userId);
          em.createQuery("delete from CartItem ci where ci.cart.id=:cid")
              .setParameter("cid", cart.getId())
              .executeUpdate();
          cart.setUpdatedAt(LocalDateTime.now());
          return null;
        });
  }

  @Override
  public int countItems(int userId) {
    return read(
        em -> {
          Long count =
              em.createQuery(
                      "select count(ci) from CartItem ci where ci.cart.user.id=:uid", Long.class)
                  .setParameter("uid", userId)
                  .getSingleResult();
          return count != null ? count.intValue() : 0;
        });
  }
}

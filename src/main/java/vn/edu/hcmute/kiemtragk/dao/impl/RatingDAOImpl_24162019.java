package vn.edu.hcmute.kiemtragk.dao.impl;

import jakarta.persistence.*;
import java.util.*;
import vn.edu.hcmute.kiemtragk.dao.*;
import vn.edu.hcmute.kiemtragk.entity.*;

public class RatingDAOImpl_24162019 extends BaseDAO_24162019 implements RatingDAO_24162019 {
  public RatingDAOImpl_24162019() {}

  public RatingDAOImpl_24162019(EntityManagerFactory f) {
    super(f);
  }

  public List<Rating_24162019> forBook(int id) {
    return read(
        em ->
            em.createQuery(
                    "select r from Rating r join fetch r.user where r.book.id=:id order by"
                        + " r.user.id",
                    Rating_24162019.class)
                .setParameter("id", id)
                .getResultList());
  }

  public void save(int uid, int bid, short stars, String review) {
    write(
        em -> {
          Book_24162019 book = em.find(Book_24162019.class, bid, LockModeType.PESSIMISTIC_WRITE);
          User_24162019 user = em.find(User_24162019.class, uid);
          if (book == null || user == null)
            throw new IllegalArgumentException("Sách hoặc tài khoản không tồn tại.");
          RatingId_24162019 key = new RatingId_24162019(uid, bid);
          Rating_24162019 r = em.find(Rating_24162019.class, key);
          if (r == null) {
            r = new Rating_24162019();
            r.setId(key);
            r.setUser(user);
            r.setBook(book);
            r.setRating(stars);
            r.setReviewText(review);
            em.persist(r);
          } else {
            r.setRating(stars);
            r.setReviewText(review);
          }
          return null;
        });
  }
}

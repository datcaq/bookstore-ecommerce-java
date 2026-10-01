package vn.edu.hcmute.kiemtragk.dao.impl;

import jakarta.persistence.*;
import java.util.*;
import vn.edu.hcmute.kiemtragk.dao.*;
import vn.edu.hcmute.kiemtragk.entity.*;

public class BookDAOImpl_24162019 extends BaseDAO_24162019 implements BookDAO_24162019 {
  public BookDAOImpl_24162019() {}

  public BookDAOImpl_24162019(EntityManagerFactory f) {
    super(f);
  }

  private void hydrate(EntityManager em, Book_24162019 b) {
    b.getAuthors().size();
    b.setReviewCount(
        em.createQuery("select count(r) from Rating r where r.book.id=:id", Long.class)
            .setParameter("id", b.getId())
            .getSingleResult());
  }

  public List<Book_24162019> page(int page, int size) {
    return read(
        em -> {
          List<Book_24162019> list =
              em.createQuery("select b from Book b order by b.id", Book_24162019.class)
                  .setFirstResult((page - 1) * size)
                  .setMaxResults(size)
                  .getResultList();
          list.forEach(b -> hydrate(em, b));
          return list;
        });
  }

  public long count() {
    return read(em -> em.createQuery("select count(b) from Book b", Long.class).getSingleResult());
  }

  public Book_24162019 find(int id) {
    return read(
        em -> {
          Book_24162019 b = em.find(Book_24162019.class, id);
          if (b != null) hydrate(em, b);
          return b;
        });
  }

  public void save(Book_24162019 b, Set<Integer> ids) {
    write(
        em -> {
          Set<Author_24162019> authors = new LinkedHashSet<>();
          for (int id : ids) {
            Author_24162019 a = em.find(Author_24162019.class, id);
            if (a == null) throw new IllegalArgumentException("Tác giả không tồn tại.");
            authors.add(a);
          }
          b.setAuthors(authors);
          if (b.getId() == null) em.persist(b);
          else {
            if (em.find(Book_24162019.class, b.getId()) == null)
              throw new IllegalArgumentException("Sách không tồn tại.");
            em.merge(b);
          }
          return null;
        });
  }

  public void delete(int id) {
    write(
        em -> {
          Book_24162019 b = em.find(Book_24162019.class, id);
          if (b == null) throw new IllegalArgumentException("Sách không tồn tại.");
          em.createQuery("delete from Rating r where r.book.id=:id")
              .setParameter("id", id)
              .executeUpdate();
          b.getAuthors().clear();
          em.flush();
          em.remove(b);
          return null;
        });
  }
}

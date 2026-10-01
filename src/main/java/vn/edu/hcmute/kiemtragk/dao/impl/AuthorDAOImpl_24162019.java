package vn.edu.hcmute.kiemtragk.dao.impl;

import jakarta.persistence.*;
import java.util.*;
import vn.edu.hcmute.kiemtragk.dao.*;
import vn.edu.hcmute.kiemtragk.entity.*;

public class AuthorDAOImpl_24162019 extends BaseDAO_24162019 implements AuthorDAO_24162019 {
  public AuthorDAOImpl_24162019() {}

  public AuthorDAOImpl_24162019(EntityManagerFactory f) {
    super(f);
  }

  public List<Author_24162019> page(int page, int size) {
    return read(
        em ->
            em.createQuery("select a from Author a order by a.id", Author_24162019.class)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList());
  }

  public List<Author_24162019> all() {
    return read(
        em ->
            em.createQuery("select a from Author a order by a.name", Author_24162019.class)
                .getResultList());
  }

  public long count() {
    return read(
        em -> em.createQuery("select count(a) from Author a", Long.class).getSingleResult());
  }

  public Author_24162019 find(int id) {
    return read(em -> em.find(Author_24162019.class, id));
  }

  public void save(Author_24162019 a) {
    write(
        em -> {
          if (a.getId() == null) em.persist(a);
          else {
            if (em.find(Author_24162019.class, a.getId()) == null)
              throw new IllegalArgumentException("Tác giả không tồn tại.");
            em.merge(a);
          }
          return null;
        });
  }

  public void delete(int id) {
    write(
        em -> {
          Author_24162019 a = em.find(Author_24162019.class, id);
          if (a == null) throw new IllegalArgumentException("Tác giả không tồn tại.");
          em.createNativeQuery("delete from book_author where author_id=:id")
              .setParameter("id", id)
              .executeUpdate();
          em.remove(a);
          return null;
        });
  }
}

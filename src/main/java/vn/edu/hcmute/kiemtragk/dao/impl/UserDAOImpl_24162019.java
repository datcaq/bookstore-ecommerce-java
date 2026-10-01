package vn.edu.hcmute.kiemtragk.dao.impl;

import jakarta.persistence.*;
import vn.edu.hcmute.kiemtragk.dao.*;
import vn.edu.hcmute.kiemtragk.entity.*;

public class UserDAOImpl_24162019 extends BaseDAO_24162019 implements UserDAO_24162019 {
  public UserDAOImpl_24162019() {}

  public UserDAOImpl_24162019(EntityManagerFactory f) {
    super(f);
  }

  public User_24162019 findByEmail(String email) {
    return read(
        em ->
            em.createQuery("select u from User u where u.email=:email", User_24162019.class)
                .setParameter("email", email)
                .getResultStream()
                .findFirst()
                .orElse(null));
  }

  public User_24162019 create(User_24162019 u) {
    return write(
        em -> {
          em.persist(u);
          return u;
        });
  }

  public void updateLastLogin(int id) {
    write(
        em -> {
          User_24162019 u = em.find(User_24162019.class, id);
          u.setLastLogin(java.time.LocalDateTime.now());
          return null;
        });
  }
}

package vn.edu.hcmute.kiemtragk.dao.impl;

import jakarta.persistence.*;
import java.util.function.Function;
import vn.edu.hcmute.kiemtragk.config.JpaConfig_24162019;

public abstract class BaseDAO_24162019 {
  private final EntityManagerFactory factory;

  protected BaseDAO_24162019() {
    factory = null;
  }

  protected BaseDAO_24162019(EntityManagerFactory f) {
    factory = f;
  }

  protected <T> T read(Function<EntityManager, T> work) {
    EntityManager em =
        (factory == null ? JpaConfig_24162019.factory() : factory).createEntityManager();
    try {
      return work.apply(em);
    } finally {
      em.close();
    }
  }

  protected <T> T write(Function<EntityManager, T> work) {
    return read(
        em -> {
          EntityTransaction tx = em.getTransaction();
          try {
            tx.begin();
            T v = work.apply(em);
            tx.commit();
            return v;
          } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
          }
        });
  }
}

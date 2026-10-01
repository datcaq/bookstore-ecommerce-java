package vn.edu.hcmute.kiemtragk.config;

import jakarta.persistence.*;
import java.util.*;

public final class JpaConfig_24162019 {
  private static EntityManagerFactory factory;

  public static synchronized EntityManagerFactory factory() {
    if (factory == null) {
      Properties p = LocalConfig_24162019.load("db-local.properties");
      Map<String, Object> opts = new HashMap<>();
      opts.put(
          "jakarta.persistence.jdbc.url",
          LocalConfig_24162019.value(
              p,
              "DB_URL",
              "jdbc:sqlserver://localhost;instanceName=SQLEXPRESS;databaseName=BookStoreGK_24162019;encrypt=true;trustServerCertificate=true"));
      opts.put("jakarta.persistence.jdbc.user", LocalConfig_24162019.value(p, "DB_USERNAME", "sa"));
      opts.put(
          "jakarta.persistence.jdbc.password",
          LocalConfig_24162019.value(p, "DB_PASSWORD", "YOUR_SQL_SERVER_PASSWORD"));
      factory = Persistence.createEntityManagerFactory("BookStoreGK_24162019", opts);
    }
    return factory;
  }

  public static synchronized void close() {
    if (factory != null && factory.isOpen()) factory.close();
    factory = null;
  }
}

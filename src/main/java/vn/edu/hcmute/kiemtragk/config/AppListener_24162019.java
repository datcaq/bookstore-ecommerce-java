package vn.edu.hcmute.kiemtragk.config;

import jakarta.servlet.*;

public class AppListener_24162019 implements ServletContextListener {
  public void contextDestroyed(ServletContextEvent event) {
    JpaConfig_24162019.close();
  }
}

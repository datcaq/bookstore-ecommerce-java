package vn.edu.hcmute.kiemtragk.config;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public final class LocalConfig_24162019 {
  public static Properties load(String file) {
    Properties p = new Properties();
    try (InputStream in = LocalConfig_24162019.class.getClassLoader().getResourceAsStream(file)) {
      if (in != null) p.load(new InputStreamReader(in, StandardCharsets.UTF_8));
    } catch (IOException e) {
      throw new IllegalStateException("Không đọc được cấu hình local", e);
    }
    return p;
  }

  public static String value(Properties p, String key, String fallback) {
    String env = System.getenv(key);
    return env != null ? env : p.getProperty(key, fallback);
  }
}

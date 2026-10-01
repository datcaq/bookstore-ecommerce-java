package vn.edu.hcmute.kiemtragk.util;

public final class Validation_24162019 {
  public static String utf8(String s, int maxBytes, String label) {
    if (s != null && s.getBytes(java.nio.charset.StandardCharsets.UTF_8).length > maxBytes)
      throw new IllegalArgumentException(
          label + " vượt giới hạn " + maxBytes + " byte UTF-8 của cột varchar trong đề.");
    return s;
  }

  public static String legacyText(String s) {
    String normalized =
        new vn.edu.hcmute.kiemtragk.entity.VietnameseTextConverter_24162019()
            .convertToDatabaseColumn(s);
    if (normalized != null
        && !java.nio.charset.Charset.forName("windows-1258").newEncoder().canEncode(normalized))
      throw new IllegalArgumentException(
          "Cột text của đề hỗ trợ tiếng Việt và ký tự Latin; vui lòng bỏ emoji/ký tự không hỗ"
              + " trợ.");
    return s;
  }

  public static String required(String s, int max, String label) {
    if (s == null || s.isBlank() || s.trim().length() > max)
      throw new IllegalArgumentException(label + " phải có từ 1 đến " + max + " ký tự.");
    return s.trim();
  }

  public static String optional(String s, int max, String label) {
    if (s != null && s.length() > max)
      throw new IllegalArgumentException(label + " tối đa " + max + " ký tự.");
    return s == null ? "" : s.trim();
  }

  public static int positive(String s) {
    try {
      int v = Integer.parseInt(s);
      if (v > 0) return v;
    } catch (Exception ignored) {
    }
    throw new IllegalArgumentException("Mã không hợp lệ.");
  }

  public static int page(String p, int total) {
    try {
      return Math.min(Math.max(1, Integer.parseInt(p)), Math.max(1, total));
    } catch (Exception e) {
      return 1;
    }
  }

  public static int pages(long count, int size) {
    return (int) Math.max(1, (count + size - 1) / size);
  }
}

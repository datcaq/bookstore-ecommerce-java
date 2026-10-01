package vn.edu.hcmute.kiemtragk.util;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public final class OrderStatusUtil_24162019 {
  public static final String NEW = "NEW";
  public static final String CONFIRMED = "CONFIRMED";
  public static final String PREPARING = "PREPARING";
  public static final String SHIPPING = "SHIPPING";
  public static final String DELIVERING = "DELIVERING";
  public static final String DELIVERED = "DELIVERED";
  public static final String CANCELLED = "CANCELLED";
  public static final String RETURNED = "RETURNED";

  private static final Map<String, String> STATUS_MAP;

  static {
    Map<String, String> map = new LinkedHashMap<>();
    map.put(NEW, "Đơn hàng mới");
    map.put(CONFIRMED, "Đã xác nhận");
    map.put(PREPARING, "Chuẩn bị hàng");
    map.put(SHIPPING, "Vận chuyển");
    map.put(DELIVERING, "Giao hàng");
    map.put(DELIVERED, "Đã giao");
    map.put(CANCELLED, "Đơn hàng hủy");
    map.put(RETURNED, "Đơn hàng hoàn");
    STATUS_MAP = Collections.unmodifiableMap(map);
  }

  private OrderStatusUtil_24162019() {}

  public static Map<String, String> getStatusMap() {
    return STATUS_MAP;
  }

  public static boolean isValidStatus(String status) {
    if (status == null || status.isBlank()) return false;
    return STATUS_MAP.containsKey(status.trim().toUpperCase());
  }

  public static String getLabel(String status) {
    if (status == null || status.isBlank()) return "Không xác định";
    String label = STATUS_MAP.get(status.trim().toUpperCase());
    return label != null ? label : status;
  }
}

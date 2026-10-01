package vn.edu.hcmute.kiemtragk.service;

import java.io.Serializable;
import java.time.Instant;
import vn.edu.hcmute.kiemtragk.entity.User_24162019;

public final class PendingRegistration_24162019 implements Serializable {
  private static final long serialVersionUID = 1L;

  private final User_24162019 user;
  private final String otp;
  private final Instant expires;
  private int attempts;

  public PendingRegistration_24162019(User_24162019 u, String o, Instant e) {
    user = u;
    otp = o;
    expires = e;
  }

  public User_24162019 getUser() {
    return user;
  }

  public Instant getExpires() {
    return expires;
  }

  public void verify(String code) {
    if (!Instant.now().isBefore(expires))
      throw new IllegalArgumentException("OTP đã hết hạn. Vui lòng đăng ký lại.");
    if (attempts >= 5)
      throw new IllegalArgumentException("Đã nhập sai 5 lần. Vui lòng đăng ký lại.");
    attempts++;
    if (!otp.equals(code)) throw new IllegalArgumentException("Mã OTP không đúng.");
  }
}

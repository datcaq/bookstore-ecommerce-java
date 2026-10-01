package vn.edu.hcmute.kiemtragk.service.impl;

import vn.edu.hcmute.kiemtragk.dao.*;
import vn.edu.hcmute.kiemtragk.dao.impl.*;
import vn.edu.hcmute.kiemtragk.entity.*;
import vn.edu.hcmute.kiemtragk.service.*;
import vn.edu.hcmute.kiemtragk.util.*;

public class UserServiceImpl_24162019 implements UserService_24162019 {
  private final UserDAO_24162019 dao;
  private final EmailUtil_24162019 mail;

  public UserServiceImpl_24162019() {
    this(new UserDAOImpl_24162019(), new EmailUtil_24162019());
  }

  public UserServiceImpl_24162019(UserDAO_24162019 d, EmailUtil_24162019 m) {
    dao = d;
    mail = m;
  }

  private String email(String s) {
    s = Validation_24162019.required(s, 50, "Email").toLowerCase(java.util.Locale.ROOT);
    Validation_24162019.utf8(s, 50, "Email");
    if (!s.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$"))
      throw new IllegalArgumentException("Email không đúng định dạng.");
    return s;
  }

  public PendingRegistration_24162019 register(
      String email, String name, String phone, String password, String confirm) {
    email = email(email);
    if (dao.findByEmail(email) != null)
      throw new IllegalArgumentException("Email đã được đăng ký.");
    name = Validation_24162019.required(name, 50, "Họ tên");
    if (password == null || password.length() < 6 || password.length() > 128)
      throw new IllegalArgumentException("Mật khẩu phải có từ 6 đến 128 ký tự.");
    if (!password.equals(confirm))
      throw new IllegalArgumentException("Xác nhận mật khẩu không trùng khớp.");
    Integer number = null;
    if (phone != null && !phone.isBlank()) {
      try {
        if (!phone.matches("[0-9]{1,10}")) throw new NumberFormatException();
        number = Integer.valueOf(phone);
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException(
            "Phone phải là số nguyên từ 0 đến 2147483647 theo cấu trúc đề.");
      }
    }
    User_24162019 u = new User_24162019();
    u.setEmail(email);
    u.setFullname(name);
    u.setPhone(number);
    u.setPasswd(PasswordUtil_24162019.hash(password));
    u.setAdmin(false);
    String otp =
        String.format(
            java.util.Locale.ROOT, "%06d", new java.security.SecureRandom().nextInt(1000000));
    mail.sendOtp(email, otp);
    return new PendingRegistration_24162019(u, otp, java.time.Instant.now().plusSeconds(300));
  }

  public User_24162019 verify(PendingRegistration_24162019 pending, String otp) {
    if (pending == null)
      throw new IllegalArgumentException("Không có đăng ký chờ xác thực. Vui lòng đăng ký lại.");
    pending.verify(otp);
    User_24162019 u = pending.getUser();
    if (dao.findByEmail(u.getEmail()) != null)
      throw new IllegalArgumentException("Email đã được đăng ký.");
    u.setSignupDate(java.time.LocalDateTime.now());
    u.setAdmin(false);
    return dao.create(u);
  }

  public User_24162019 login(String email, String password) {
    if (email == null || password == null || password.length() > 128) return null;
    User_24162019 u = dao.findByEmail(email.trim().toLowerCase(java.util.Locale.ROOT));
    if (u == null || !PasswordUtil_24162019.matches(password, u.getPasswd())) return null;
    dao.updateLastLogin(u.getId());
    u.setLastLogin(java.time.LocalDateTime.now());
    u.setPasswd(null);
    return u;
  }
}

package vn.edu.hcmute.kiemtragk.service;

import vn.edu.hcmute.kiemtragk.entity.*;

public interface UserService_24162019 {
  PendingRegistration_24162019 register(
      String email, String fullname, String phone, String password, String confirm);

  User_24162019 verify(PendingRegistration_24162019 pending, String otp);

  User_24162019 login(String email, String password);
}

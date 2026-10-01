package vn.edu.hcmute.kiemtragk.dao;

import vn.edu.hcmute.kiemtragk.entity.*;

public interface UserDAO_24162019 {
  User_24162019 findByEmail(String email);

  User_24162019 create(User_24162019 user);

  void updateLastLogin(int id);
}

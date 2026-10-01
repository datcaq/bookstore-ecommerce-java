package vn.edu.hcmute.kiemtragk.service.impl;

import java.util.*;
import vn.edu.hcmute.kiemtragk.dao.*;
import vn.edu.hcmute.kiemtragk.dao.impl.*;
import vn.edu.hcmute.kiemtragk.entity.*;
import vn.edu.hcmute.kiemtragk.service.*;
import vn.edu.hcmute.kiemtragk.util.*;

public class AuthorServiceImpl_24162019 implements AuthorService_24162019 {
  private final AuthorDAO_24162019 dao;

  public AuthorServiceImpl_24162019() {
    this(new AuthorDAOImpl_24162019());
  }

  public AuthorServiceImpl_24162019(AuthorDAO_24162019 d) {
    dao = d;
  }

  public List<Author_24162019> page(int p) {
    return dao.page(Math.max(1, p), 6);
  }

  public List<Author_24162019> all() {
    return dao.all();
  }

  public long count() {
    return dao.count();
  }

  public Author_24162019 find(int id) {
    return dao.find(id);
  }

  public void delete(int id) {
    dao.delete(id);
  }

  public void save(Author_24162019 a) {
    a.setName(Validation_24162019.required(a.getName(), 100, "Tên tác giả"));
    Validation_24162019.utf8(a.getName(), 100, "Tên tác giả");
    if (a.getDateOfBirth() != null && a.getDateOfBirth().isAfter(java.time.LocalDate.now()))
      throw new IllegalArgumentException("Ngày sinh không được ở tương lai.");
    dao.save(a);
  }
}

package vn.edu.hcmute.kiemtragk.service;

import java.util.*;
import vn.edu.hcmute.kiemtragk.entity.*;

public interface AuthorService_24162019 {
  List<Author_24162019> page(int page);

  List<Author_24162019> all();

  long count();

  Author_24162019 find(int id);

  void save(Author_24162019 author);

  void delete(int id);
}

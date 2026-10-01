package vn.edu.hcmute.kiemtragk.dao;

import java.util.*;
import vn.edu.hcmute.kiemtragk.entity.*;

public interface AuthorDAO_24162019 {
  List<Author_24162019> page(int page, int size);

  List<Author_24162019> all();

  long count();

  Author_24162019 find(int id);

  void save(Author_24162019 author);

  void delete(int id);
}

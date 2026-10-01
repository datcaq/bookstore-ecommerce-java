package vn.edu.hcmute.kiemtragk.dao;

import java.util.*;
import vn.edu.hcmute.kiemtragk.entity.*;

public interface BookDAO_24162019 {
  List<Book_24162019> page(int page, int size);

  long count();

  Book_24162019 find(int id);

  void save(Book_24162019 book, Set<Integer> authors);

  void delete(int id);
}

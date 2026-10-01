package vn.edu.hcmute.kiemtragk.service;

import java.util.*;
import vn.edu.hcmute.kiemtragk.entity.*;

public interface BookService_24162019 {
  List<Book_24162019> page(int page);

  long count();

  Book_24162019 find(int id);

  void save(Book_24162019 book, Set<Integer> authors);

  void delete(int id);
}

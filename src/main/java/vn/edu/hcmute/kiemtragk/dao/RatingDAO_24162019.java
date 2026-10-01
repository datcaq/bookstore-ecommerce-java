package vn.edu.hcmute.kiemtragk.dao;

import java.util.*;
import vn.edu.hcmute.kiemtragk.entity.*;

public interface RatingDAO_24162019 {
  List<Rating_24162019> forBook(int id);

  void save(int userId, int bookId, short rating, String review);
}

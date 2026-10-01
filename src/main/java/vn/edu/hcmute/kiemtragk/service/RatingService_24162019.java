package vn.edu.hcmute.kiemtragk.service;

import java.util.*;
import vn.edu.hcmute.kiemtragk.entity.*;

public interface RatingService_24162019 {
  List<Rating_24162019> forBook(int id);

  void save(int userId, int bookId, int rating, String review);
}

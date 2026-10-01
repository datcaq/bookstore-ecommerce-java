package vn.edu.hcmute.kiemtragk.service.impl;

import java.util.*;
import vn.edu.hcmute.kiemtragk.dao.*;
import vn.edu.hcmute.kiemtragk.dao.impl.*;
import vn.edu.hcmute.kiemtragk.entity.*;
import vn.edu.hcmute.kiemtragk.service.*;
import vn.edu.hcmute.kiemtragk.util.*;

public class RatingServiceImpl_24162019 implements RatingService_24162019 {
  private final RatingDAO_24162019 dao;

  public RatingServiceImpl_24162019() {
    this(new RatingDAOImpl_24162019());
  }

  public RatingServiceImpl_24162019(RatingDAO_24162019 d) {
    dao = d;
  }

  public List<Rating_24162019> forBook(int id) {
    return dao.forBook(id);
  }

  public void save(int uid, int bid, int stars, String review) {
    if (stars < 1 || stars > 5) throw new IllegalArgumentException("Rating phải từ 1 đến 5.");
    review = Validation_24162019.required(review, 2000, "Nội dung đánh giá");
    Validation_24162019.legacyText(review);
    dao.save(uid, bid, (short) stars, review);
  }
}

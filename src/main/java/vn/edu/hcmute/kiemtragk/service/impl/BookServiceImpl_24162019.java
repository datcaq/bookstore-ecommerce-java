package vn.edu.hcmute.kiemtragk.service.impl;

import java.util.*;
import vn.edu.hcmute.kiemtragk.dao.*;
import vn.edu.hcmute.kiemtragk.dao.impl.*;
import vn.edu.hcmute.kiemtragk.entity.*;
import vn.edu.hcmute.kiemtragk.service.*;
import vn.edu.hcmute.kiemtragk.util.*;

public class BookServiceImpl_24162019 implements BookService_24162019 {
  private final BookDAO_24162019 dao;

  public BookServiceImpl_24162019() {
    this(new BookDAOImpl_24162019());
  }

  public BookServiceImpl_24162019(BookDAO_24162019 d) {
    dao = d;
  }

  public List<Book_24162019> page(int p) {
    return dao.page(Math.max(1, p), 6);
  }

  public long count() {
    return dao.count();
  }

  public Book_24162019 find(int id) {
    return dao.find(id);
  }

  public void delete(int id) {
    dao.delete(id);
  }

  public void save(Book_24162019 b, Set<Integer> authors) {
    b.setTitle(Validation_24162019.required(b.getTitle(), 200, "Tiêu đề"));
    b.setPublisher(Validation_24162019.optional(b.getPublisher(), 100, "Nhà xuất bản"));
    b.setDescription(Validation_24162019.optional(b.getDescription(), 10000, "Mô tả"));
    b.setCoverImage(Validation_24162019.optional(b.getCoverImage(), 100, "Ảnh bìa"));
    if (b.getIsbn() == null || b.getIsbn() < 0)
      throw new IllegalArgumentException("ISBN phải là số nguyên không âm.");
    if (b.getQuantity() == null || b.getQuantity() < 0)
      throw new IllegalArgumentException("Số lượng phải là số nguyên không âm.");
    if (b.getPrice() == null
        || b.getPrice().signum() < 0
        || b.getPrice().compareTo(new java.math.BigDecimal("9999.99")) > 0
        || b.getPrice().stripTrailingZeros().scale() > 2)
      throw new IllegalArgumentException("Giá phải từ 0 đến 9999.99, tối đa 2 chữ số thập phân.");
    if (authors == null || authors.isEmpty())
      throw new IllegalArgumentException("Vui lòng chọn ít nhất một tác giả.");
    Validation_24162019.utf8(b.getTitle(), 200, "Tiêu đề");
    Validation_24162019.utf8(b.getPublisher(), 100, "Nhà xuất bản");
    Validation_24162019.utf8(b.getCoverImage(), 100, "Ảnh bìa");
    Validation_24162019.legacyText(b.getDescription());
    String c = b.getCoverImage();
    if (!c.isEmpty()
        && !c.matches("(?i)^https?://[^\\s]+$")
        && (!c.matches("[a-zA-Z0-9_.-]+") || c.contains("..")))
      throw new IllegalArgumentException("Ảnh phải là URL http/https hoặc tên file local hợp lệ.");
    dao.save(b, authors);
  }
}

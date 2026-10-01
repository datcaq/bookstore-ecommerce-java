package vn.edu.hcmute.kiemtragk.controller.web;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import vn.edu.hcmute.kiemtragk.controller.BaseController_24162019;
import vn.edu.hcmute.kiemtragk.entity.*;
import vn.edu.hcmute.kiemtragk.service.*;
import vn.edu.hcmute.kiemtragk.service.impl.*;

public class BookDetailController_24162019 extends BaseController_24162019 {
  private static final long serialVersionUID = 1L;

  private final BookService_24162019 books = new BookServiceImpl_24162019();
  private final RatingService_24162019 ratings = new RatingServiceImpl_24162019();

  protected void doGet(HttpServletRequest r, HttpServletResponse s)
      throws ServletException, IOException {
    try {
      Book_24162019 b = books.find(id(r));
      if (b == null) {
        s.sendError(404);
        return;
      }
      r.setAttribute("book", b);
      r.setAttribute("reviews", ratings.forBook(b.getId()));
      view(r, s, "web/book-detail");
    } catch (IllegalArgumentException e) {
      s.sendError(400);
    }
  }
}

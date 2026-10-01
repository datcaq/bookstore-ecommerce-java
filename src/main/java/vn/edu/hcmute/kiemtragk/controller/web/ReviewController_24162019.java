package vn.edu.hcmute.kiemtragk.controller.web;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import vn.edu.hcmute.kiemtragk.controller.BaseController_24162019;
import vn.edu.hcmute.kiemtragk.entity.*;
import vn.edu.hcmute.kiemtragk.service.*;
import vn.edu.hcmute.kiemtragk.service.impl.*;
import vn.edu.hcmute.kiemtragk.util.*;

public class ReviewController_24162019 extends BaseController_24162019 {
  private static final long serialVersionUID = 1L;

  private final RatingService_24162019 ratings = new RatingServiceImpl_24162019();
  private final BookService_24162019 books = new BookServiceImpl_24162019();

  protected void doPost(HttpServletRequest r, HttpServletResponse s)
      throws ServletException, IOException {
    int bid;
    try {
      bid = Validation_24162019.positive(r.getParameter("bookId"));
    } catch (IllegalArgumentException e) {
      s.sendError(400);
      return;
    }
    try {
      User_24162019 u = (User_24162019) r.getSession().getAttribute("account");
      int stars;
      try {
        stars = Integer.parseInt(r.getParameter("rating"));
      } catch (Exception e) {
        throw new IllegalArgumentException("Rating phải từ 1 đến 5.");
      }
      ratings.save(u.getId(), bid, stars, r.getParameter("review_text"));
      redirect(r, s, "/book/detail?id=" + bid + "&saved=1");
    } catch (IllegalArgumentException e) {
      Book_24162019 b = books.find(bid);
      if (b == null) {
        s.sendError(404);
        return;
      }
      r.setAttribute("book", b);
      r.setAttribute("reviews", ratings.forBook(bid));
      r.setAttribute("error", e.getMessage());
      view(r, s, "web/book-detail");
    }
  }
}

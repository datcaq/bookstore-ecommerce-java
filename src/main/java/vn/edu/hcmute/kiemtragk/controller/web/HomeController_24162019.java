package vn.edu.hcmute.kiemtragk.controller.web;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import vn.edu.hcmute.kiemtragk.controller.BaseController_24162019;
import vn.edu.hcmute.kiemtragk.service.*;
import vn.edu.hcmute.kiemtragk.service.impl.*;

public class HomeController_24162019 extends BaseController_24162019 {
  private static final long serialVersionUID = 1L;

  private final BookService_24162019 books = new BookServiceImpl_24162019();

  protected void doGet(HttpServletRequest r, HttpServletResponse s)
      throws ServletException, IOException {
    page(r, books.count(), "/home");
    r.setAttribute("books", books.page((Integer) r.getAttribute("page")));
    view(r, s, "web/home");
  }
}

package vn.edu.hcmute.kiemtragk.controller.admin;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import vn.edu.hcmute.kiemtragk.controller.BaseController_24162019;
import vn.edu.hcmute.kiemtragk.service.impl.*;

public class DashboardController_24162019 extends BaseController_24162019 {
  private static final long serialVersionUID = 1L;

  protected void doGet(HttpServletRequest r, HttpServletResponse s)
      throws ServletException, IOException {
    r.setAttribute("bookCount", new BookServiceImpl_24162019().count());
    r.setAttribute("authorCount", new AuthorServiceImpl_24162019().count());
    view(r, s, "admin/dashboard");
  }
}

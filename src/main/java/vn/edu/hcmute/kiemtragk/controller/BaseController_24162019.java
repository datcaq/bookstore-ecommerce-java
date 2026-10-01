package vn.edu.hcmute.kiemtragk.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import vn.edu.hcmute.kiemtragk.util.*;

public abstract class BaseController_24162019 extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void view(HttpServletRequest r, HttpServletResponse s, String path)
      throws ServletException, IOException {
    r.getRequestDispatcher("/WEB-INF/views/" + path + ".jsp").forward(r, s);
  }

  protected void redirect(HttpServletRequest r, HttpServletResponse s, String path)
      throws IOException {
    s.sendRedirect(r.getContextPath() + path);
  }

  protected int id(HttpServletRequest r) {
    return Validation_24162019.positive(r.getParameter("id"));
  }

  protected void page(HttpServletRequest r, long total, String url) {
    int pages = Validation_24162019.pages(total, 6);
    r.setAttribute("page", Validation_24162019.page(r.getParameter("page"), pages));
    r.setAttribute("pages", pages);
    r.setAttribute("total", total);
    r.setAttribute("pageUrl", url);
  }
}

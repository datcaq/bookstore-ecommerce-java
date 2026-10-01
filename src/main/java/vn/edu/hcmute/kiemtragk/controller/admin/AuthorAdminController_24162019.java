package vn.edu.hcmute.kiemtragk.controller.admin;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import vn.edu.hcmute.kiemtragk.controller.BaseController_24162019;
import vn.edu.hcmute.kiemtragk.entity.*;
import vn.edu.hcmute.kiemtragk.service.*;
import vn.edu.hcmute.kiemtragk.service.impl.*;

public class AuthorAdminController_24162019 extends BaseController_24162019 {
  private static final long serialVersionUID = 1L;

  private final AuthorService_24162019 authors = new AuthorServiceImpl_24162019();

  private void form(HttpServletRequest r, HttpServletResponse s, Author_24162019 a)
      throws ServletException, IOException {
    r.setAttribute("author", a);
    view(r, s, "admin/authors/" + (a.getId() == null ? "add" : "edit"));
  }

  protected void doGet(HttpServletRequest r, HttpServletResponse s)
      throws ServletException, IOException {
    String path = r.getPathInfo();
    try {
      if (path == null || path.equals("/")) {
        page(r, authors.count(), "/admin/authors");
        r.setAttribute("authors", authors.page((Integer) r.getAttribute("page")));
        view(r, s, "admin/authors/list");
      } else if (path.equals("/add")) {
        form(r, s, new Author_24162019());
      } else if (path.equals("/edit")) {
        Author_24162019 a = authors.find(id(r));
        if (a == null) {
          s.sendError(404);
          return;
        }
        form(r, s, a);
      } else s.sendError(404);
    } catch (IllegalArgumentException e) {
      s.sendError(400);
    }
  }

  protected void doPost(HttpServletRequest r, HttpServletResponse s)
      throws ServletException, IOException {
    String path = r.getPathInfo();
    if ("/delete".equals(path)) {
      try {
        authors.delete(id(r));
        redirect(r, s, "/admin/authors?deleted=1");
      } catch (IllegalArgumentException e) {
        s.sendError(400);
      }
      return;
    }
    if (!"/add".equals(path) && !"/edit".equals(path)) {
      s.sendError(404);
      return;
    }
    Author_24162019 a = new Author_24162019();
    if ("/edit".equals(path)) {
      try {
        a.setId(id(r));
        if (authors.find(a.getId()) == null) {
          s.sendError(404);
          return;
        }
      } catch (IllegalArgumentException e) {
        s.sendError(400);
        return;
      }
    }
    a.setName(r.getParameter("author_name"));
    try {
      String d = r.getParameter("date_of_birth");
      a.setDateOfBirth(d == null || d.isBlank() ? null : java.time.LocalDate.parse(d));
      authors.save(a);
      redirect(r, s, "/admin/authors?saved=1");
    } catch (java.time.format.DateTimeParseException e) {
      r.setAttribute("error", "Ngày sinh không hợp lệ.");
      form(r, s, a);
    } catch (IllegalArgumentException e) {
      r.setAttribute("error", e.getMessage());
      form(r, s, a);
    }
  }
}

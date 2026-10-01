package vn.edu.hcmute.kiemtragk.controller.admin;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.*;
import vn.edu.hcmute.kiemtragk.controller.BaseController_24162019;
import vn.edu.hcmute.kiemtragk.entity.*;
import vn.edu.hcmute.kiemtragk.service.*;
import vn.edu.hcmute.kiemtragk.service.impl.*;
import vn.edu.hcmute.kiemtragk.util.*;

public class BookAdminController_24162019 extends BaseController_24162019 {
  private static final long serialVersionUID = 1L;

  private final BookService_24162019 books = new BookServiceImpl_24162019();
  private final AuthorService_24162019 authors = new AuthorServiceImpl_24162019();

  private void form(
      HttpServletRequest r, HttpServletResponse s, Book_24162019 b, Set<Integer> selected)
      throws ServletException, IOException {
    r.setAttribute("book", b);
    r.setAttribute("authors", authors.all());
    r.setAttribute("selectedAuthors", selected);
    view(r, s, "admin/books/" + (b.getId() == null ? "add" : "edit"));
  }

  protected void doGet(HttpServletRequest r, HttpServletResponse s)
      throws ServletException, IOException {
    String path = r.getPathInfo();
    try {
      if (path == null || path.equals("/")) {
        page(r, books.count(), "/admin/books");
        r.setAttribute("books", books.page((Integer) r.getAttribute("page")));
        view(r, s, "admin/books/list");
      } else if (path.equals("/add")) {
        form(r, s, new Book_24162019(), new HashSet<>());
      } else if (path.equals("/edit")) {
        Book_24162019 b = books.find(id(r));
        if (b == null) {
          s.sendError(404);
          return;
        }
        Set<Integer> selected = new HashSet<>();
        b.getAuthors().forEach(a -> selected.add(a.getId()));
        form(r, s, b, selected);
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
        books.delete(id(r));
        redirect(r, s, "/admin/books?deleted=1");
      } catch (IllegalArgumentException e) {
        s.sendError(400);
      }
      return;
    }
    if (!"/add".equals(path) && !"/edit".equals(path)) {
      s.sendError(404);
      return;
    }
    Book_24162019 b = new Book_24162019();
    Set<Integer> selected = new HashSet<>();
    if ("/edit".equals(path)) {
      try {
        b.setId(id(r));
        if (books.find(b.getId()) == null) {
          s.sendError(404);
          return;
        }
      } catch (IllegalArgumentException e) {
        s.sendError(400);
        return;
      }
    }
    b.setTitle(r.getParameter("title"));
    b.setPublisher(r.getParameter("publisher"));
    b.setDescription(r.getParameter("description"));
    b.setCoverImage(r.getParameter("cover_image"));
    try {
      b.setIsbn(Integer.valueOf(r.getParameter("isbn")));
      b.setPrice(new java.math.BigDecimal(r.getParameter("price")));
      b.setQuantity(Integer.valueOf(r.getParameter("quantity")));
      String date = r.getParameter("publish_date");
      b.setPublishDate(date == null || date.isBlank() ? null : java.time.LocalDate.parse(date));
      String[] ids = r.getParameterValues("authors");
      if (ids != null) for (String v : ids) selected.add(Validation_24162019.positive(v));
      books.save(b, selected);
      redirect(r, s, "/admin/books?saved=1");
    } catch (java.time.format.DateTimeParseException | NumberFormatException e) {
      r.setAttribute("error", "ISBN, giá, số lượng hoặc ngày xuất bản không hợp lệ.");
      form(r, s, b, selected);
    } catch (IllegalArgumentException e) {
      r.setAttribute("error", e.getMessage());
      form(r, s, b, selected);
    }
  }
}

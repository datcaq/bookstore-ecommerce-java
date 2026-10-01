package vn.edu.hcmute.kiemtragk.controller.web;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import vn.edu.hcmute.kiemtragk.controller.BaseController_24162019;
import vn.edu.hcmute.kiemtragk.entity.User_24162019;
import vn.edu.hcmute.kiemtragk.service.CartService_24162019;
import vn.edu.hcmute.kiemtragk.service.impl.CartServiceImpl_24162019;
import vn.edu.hcmute.kiemtragk.util.Validation_24162019;

public class CartAddController_24162019 extends BaseController_24162019 {
  private static final long serialVersionUID = 1L;

  private final CartService_24162019 cartService = new CartServiceImpl_24162019();

  @Override
  protected void doPost(HttpServletRequest r, HttpServletResponse s)
      throws ServletException, IOException {
    User_24162019 user = (User_24162019) r.getSession().getAttribute("account");
    if (user == null) {
      redirect(r, s, "/login");
      return;
    }

    int bookId;
    try {
      bookId = Validation_24162019.positive(r.getParameter("bookId"));
    } catch (IllegalArgumentException e) {
      s.sendError(400);
      return;
    }

    int quantity = 1;
    String qParam = r.getParameter("quantity");
    if (qParam != null && !qParam.isBlank()) {
      try {
        quantity = Integer.parseInt(qParam.trim());
      } catch (NumberFormatException e) {
        quantity = 1;
      }
    }

    try {
      cartService.addToCart(user.getId(), bookId, quantity);
      redirect(r, s, "/cart?saved=1");
    } catch (IllegalArgumentException e) {
      String redirectUrl = r.getParameter("fromDetail") != null
          ? "/book/detail?id=" + bookId + "&error=" + URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8)
          : "/cart?error=" + URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
      redirect(r, s, redirectUrl);
    }
  }
}

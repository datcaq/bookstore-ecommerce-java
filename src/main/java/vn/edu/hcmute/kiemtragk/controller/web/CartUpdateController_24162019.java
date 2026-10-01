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

public class CartUpdateController_24162019 extends BaseController_24162019 {
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

    int quantity;
    try {
      quantity = Integer.parseInt(r.getParameter("quantity"));
    } catch (Exception e) {
      quantity = 1;
    }

    try {
      cartService.updateQuantity(user.getId(), bookId, quantity);
      redirect(r, s, "/cart");
    } catch (IllegalArgumentException e) {
      redirect(r, s, "/cart?error=" + URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8));
    }
  }
}

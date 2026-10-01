package vn.edu.hcmute.kiemtragk.controller.web;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import vn.edu.hcmute.kiemtragk.controller.BaseController_24162019;
import vn.edu.hcmute.kiemtragk.entity.Cart_24162019;
import vn.edu.hcmute.kiemtragk.entity.User_24162019;
import vn.edu.hcmute.kiemtragk.service.CartService_24162019;
import vn.edu.hcmute.kiemtragk.service.impl.CartServiceImpl_24162019;

public class CartController_24162019 extends BaseController_24162019 {
  private static final long serialVersionUID = 1L;

  private final CartService_24162019 cartService = new CartServiceImpl_24162019();

  @Override
  protected void doGet(HttpServletRequest r, HttpServletResponse s)
      throws ServletException, IOException {
    User_24162019 user = (User_24162019) r.getSession().getAttribute("account");
    if (user == null) {
      redirect(r, s, "/login");
      return;
    }

    Cart_24162019 cart = cartService.getCartByUser(user.getId());
    BigDecimal total = cartService.calculateTotal(cart);

    r.setAttribute("cart", cart);
    r.setAttribute("total", total);
    if (r.getParameter("error") != null) {
      r.setAttribute("error", r.getParameter("error"));
    }
    if (r.getParameter("saved") != null) {
      r.setAttribute("saved", "1");
    }

    view(r, s, "web/cart");
  }
}

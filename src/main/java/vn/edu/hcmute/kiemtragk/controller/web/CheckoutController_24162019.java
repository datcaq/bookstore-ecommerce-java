package vn.edu.hcmute.kiemtragk.controller.web;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import vn.edu.hcmute.kiemtragk.controller.BaseController_24162019;
import vn.edu.hcmute.kiemtragk.entity.Cart_24162019;
import vn.edu.hcmute.kiemtragk.entity.Order_24162019;
import vn.edu.hcmute.kiemtragk.entity.User_24162019;
import vn.edu.hcmute.kiemtragk.service.CartService_24162019;
import vn.edu.hcmute.kiemtragk.service.OrderService_24162019;
import vn.edu.hcmute.kiemtragk.service.impl.CartServiceImpl_24162019;
import vn.edu.hcmute.kiemtragk.service.impl.OrderServiceImpl_24162019;

public class CheckoutController_24162019 extends BaseController_24162019 {
  private static final long serialVersionUID = 1L;

  private final CartService_24162019 cartService = new CartServiceImpl_24162019();
  private final OrderService_24162019 orderService = new OrderServiceImpl_24162019();

  @Override
  protected void doGet(HttpServletRequest r, HttpServletResponse s)
      throws ServletException, IOException {
    User_24162019 user = (User_24162019) r.getSession().getAttribute("account");
    if (user == null) {
      redirect(r, s, "/login");
      return;
    }

    Cart_24162019 cart = cartService.getCartByUser(user.getId());
    if (cart == null || cart.getItems() == null || cart.getItems().isEmpty()) {
      redirect(r, s, "/cart?error=" + URLEncoder.encode("Giỏ hàng đang trống.", StandardCharsets.UTF_8));
      return;
    }

    BigDecimal total = cartService.calculateTotal(cart);
    r.setAttribute("cart", cart);
    r.setAttribute("total", total);
    view(r, s, "web/checkout");
  }

  @Override
  protected void doPost(HttpServletRequest r, HttpServletResponse s)
      throws ServletException, IOException {
    User_24162019 user = (User_24162019) r.getSession().getAttribute("account");
    if (user == null) {
      redirect(r, s, "/login");
      return;
    }

    String receiverName = r.getParameter("receiverName");
    String receiverPhone = r.getParameter("receiverPhone");
    String shippingAddress = r.getParameter("shippingAddress");

    try {
      Order_24162019 order =
          orderService.checkout(user.getId(), receiverName, receiverPhone, shippingAddress);
      redirect(r, s, "/checkout/success?id=" + order.getId());
    } catch (IllegalArgumentException e) {
      Cart_24162019 cart = cartService.getCartByUser(user.getId());
      BigDecimal total = cartService.calculateTotal(cart);
      r.setAttribute("cart", cart);
      r.setAttribute("total", total);
      r.setAttribute("error", e.getMessage());
      r.setAttribute("receiverName", receiverName);
      r.setAttribute("receiverPhone", receiverPhone);
      r.setAttribute("shippingAddress", shippingAddress);
      view(r, s, "web/checkout");
    }
  }
}

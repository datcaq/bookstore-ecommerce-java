package vn.edu.hcmute.kiemtragk.controller.web;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import vn.edu.hcmute.kiemtragk.controller.BaseController_24162019;
import vn.edu.hcmute.kiemtragk.entity.Order_24162019;
import vn.edu.hcmute.kiemtragk.entity.User_24162019;
import vn.edu.hcmute.kiemtragk.service.OrderService_24162019;
import vn.edu.hcmute.kiemtragk.service.impl.OrderServiceImpl_24162019;
import vn.edu.hcmute.kiemtragk.util.Validation_24162019;

public class OrderDetailController_24162019 extends BaseController_24162019 {
  private static final long serialVersionUID = 1L;

  private final OrderService_24162019 orderService = new OrderServiceImpl_24162019();

  @Override
  protected void doGet(HttpServletRequest r, HttpServletResponse s)
      throws ServletException, IOException {
    User_24162019 user = (User_24162019) r.getSession().getAttribute("account");
    if (user == null) {
      redirect(r, s, "/login");
      return;
    }

    int orderId;
    try {
      orderId = Validation_24162019.positive(r.getParameter("id"));
    } catch (IllegalArgumentException e) {
      s.sendError(400);
      return;
    }

    Order_24162019 order = orderService.getOrderDetail(orderId, user.getId());
    if (order == null) {
      // Check if order exists but belongs to another user
      Order_24162019 anyOrder = orderService.findById(orderId);
      if (anyOrder != null && !anyOrder.getUser().getId().equals(user.getId())) {
        s.sendError(403);
        return;
      }
      s.sendError(404);
      return;
    }

    r.setAttribute("order", order);
    view(r, s, "web/order-detail");
  }
}

package vn.edu.hcmute.kiemtragk.controller.web;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import vn.edu.hcmute.kiemtragk.controller.BaseController_24162019;
import vn.edu.hcmute.kiemtragk.entity.Order_24162019;
import vn.edu.hcmute.kiemtragk.entity.User_24162019;
import vn.edu.hcmute.kiemtragk.service.OrderService_24162019;
import vn.edu.hcmute.kiemtragk.service.impl.OrderServiceImpl_24162019;
import vn.edu.hcmute.kiemtragk.util.OrderStatusUtil_24162019;

public class OrderHistoryController_24162019 extends BaseController_24162019 {
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

    String status = r.getParameter("status");
    String selectedStatus = (status != null && !status.isBlank()) ? status.trim().toUpperCase() : "";

    List<Order_24162019> orders = orderService.getOrderHistory(user.getId(), selectedStatus);

    r.setAttribute("orders", orders);
    r.setAttribute("selectedStatus", selectedStatus);
    r.setAttribute("statusMap", OrderStatusUtil_24162019.getStatusMap());

    view(r, s, "web/orders");
  }
}

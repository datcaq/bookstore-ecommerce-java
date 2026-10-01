package vn.edu.hcmute.kiemtragk.controller.auth;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import vn.edu.hcmute.kiemtragk.controller.BaseController_24162019;
import vn.edu.hcmute.kiemtragk.service.*;
import vn.edu.hcmute.kiemtragk.service.impl.*;

public class RegisterController_24162019 extends BaseController_24162019 {
  private static final long serialVersionUID = 1L;

  private final UserService_24162019 users = new UserServiceImpl_24162019();

  protected void doGet(HttpServletRequest r, HttpServletResponse s)
      throws ServletException, IOException {
    view(r, s, "auth/register");
  }

  protected void doPost(HttpServletRequest r, HttpServletResponse s)
      throws ServletException, IOException {
    try {
      HttpSession session = r.getSession();
      synchronized (session) {
        Long last = (Long) session.getAttribute("lastOtpSent");
        if (last != null && System.currentTimeMillis() - last < 60000)
          throw new IllegalArgumentException("Vui lòng đợi 60 giây trước khi gửi lại đăng ký.");
        PendingRegistration_24162019 pending =
            users.register(
                r.getParameter("email"),
                r.getParameter("fullname"),
                r.getParameter("phone"),
                r.getParameter("password"),
                r.getParameter("confirm"));
        session.setAttribute("pendingRegistration", pending);
        session.setAttribute("lastOtpSent", System.currentTimeMillis());
      }
      redirect(r, s, "/verify-otp");
    } catch (IllegalArgumentException e) {
      r.setAttribute("error", e.getMessage());
      view(r, s, "auth/register");
    }
  }
}

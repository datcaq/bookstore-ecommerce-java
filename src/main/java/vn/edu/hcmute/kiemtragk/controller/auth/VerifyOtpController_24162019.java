package vn.edu.hcmute.kiemtragk.controller.auth;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import vn.edu.hcmute.kiemtragk.controller.BaseController_24162019;
import vn.edu.hcmute.kiemtragk.service.*;
import vn.edu.hcmute.kiemtragk.service.impl.*;

public class VerifyOtpController_24162019 extends BaseController_24162019 {
  private static final long serialVersionUID = 1L;

  private final UserService_24162019 users = new UserServiceImpl_24162019();

  protected void doGet(HttpServletRequest r, HttpServletResponse s)
      throws ServletException, IOException {
    view(r, s, "auth/verify-otp");
  }

  protected void doPost(HttpServletRequest r, HttpServletResponse s)
      throws ServletException, IOException {
    try {
      HttpSession session = r.getSession();
      synchronized (session) {
        users.verify(
            (PendingRegistration_24162019) session.getAttribute("pendingRegistration"),
            r.getParameter("otp"));
        session.removeAttribute("pendingRegistration");
      }
      redirect(r, s, "/login?registered=1");
    } catch (IllegalArgumentException e) {
      r.setAttribute("error", e.getMessage());
      view(r, s, "auth/verify-otp");
    }
  }
}

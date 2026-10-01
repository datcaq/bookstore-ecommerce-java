package vn.edu.hcmute.kiemtragk.controller.auth;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.*;
import vn.edu.hcmute.kiemtragk.controller.BaseController_24162019;
import vn.edu.hcmute.kiemtragk.entity.*;
import vn.edu.hcmute.kiemtragk.service.*;
import vn.edu.hcmute.kiemtragk.service.impl.*;

public class LoginController_24162019 extends BaseController_24162019 {
  private static final long serialVersionUID = 1L;

  private final UserService_24162019 users = new UserServiceImpl_24162019();

  protected void doGet(HttpServletRequest r, HttpServletResponse s)
      throws ServletException, IOException {
    view(r, s, "auth/login");
  }

  protected void doPost(HttpServletRequest r, HttpServletResponse s)
      throws ServletException, IOException {
    User_24162019 u = users.login(r.getParameter("email"), r.getParameter("password"));
    if (u == null) {
      r.setAttribute("error", "Email hoặc mật khẩu không đúng.");
      view(r, s, "auth/login");
      return;
    }
    r.changeSessionId();
    r.getSession().setAttribute("account", u);
    r.getSession().setAttribute("csrf", UUID.randomUUID().toString());
    r.getSession().removeAttribute("pendingRegistration");
    redirect(r, s, Boolean.TRUE.equals(u.getAdmin()) ? "/admin" : "/home");
  }
}

package vn.edu.hcmute.kiemtragk.controller.auth;

import jakarta.servlet.http.*;
import java.io.*;
import vn.edu.hcmute.kiemtragk.controller.BaseController_24162019;

public class LogoutController_24162019 extends BaseController_24162019 {
  private static final long serialVersionUID = 1L;

  protected void doPost(HttpServletRequest r, HttpServletResponse s) throws IOException {
    HttpSession session = r.getSession(false);
    if (session != null) session.invalidate();
    redirect(r, s, "/login");
  }
}

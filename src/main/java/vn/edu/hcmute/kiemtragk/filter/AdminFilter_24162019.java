package vn.edu.hcmute.kiemtragk.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import vn.edu.hcmute.kiemtragk.entity.*;

public class AdminFilter_24162019 implements Filter {
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest r = (HttpServletRequest) req;
    HttpServletResponse s = (HttpServletResponse) res;
    HttpSession session = r.getSession(false);
    User_24162019 u = session == null ? null : (User_24162019) session.getAttribute("account");
    if (u == null) {
      s.sendRedirect(r.getContextPath() + "/login");
      return;
    }
    if (!Boolean.TRUE.equals(u.getAdmin())) {
      s.sendError(403);
      return;
    }
    chain.doFilter(req, res);
  }
}

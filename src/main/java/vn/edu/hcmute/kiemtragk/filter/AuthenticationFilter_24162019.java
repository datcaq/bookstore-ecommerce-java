package vn.edu.hcmute.kiemtragk.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;

public class AuthenticationFilter_24162019 implements Filter {
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest r = (HttpServletRequest) req;
    HttpServletResponse s = (HttpServletResponse) res;
    HttpSession session = r.getSession(false);
    if (session == null || session.getAttribute("account") == null) {
      s.sendRedirect(r.getContextPath() + "/login");
      return;
    }
    chain.doFilter(req, res);
  }
}

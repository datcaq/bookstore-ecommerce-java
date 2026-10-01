package vn.edu.hcmute.kiemtragk.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.*;

public class CsrfFilter_24162019 implements Filter {
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest r = (HttpServletRequest) req;
    HttpServletResponse s = (HttpServletResponse) res;
    HttpSession session = r.getSession();
    String token = (String) session.getAttribute("csrf");
    if (token == null) {
      token = UUID.randomUUID().toString();
      session.setAttribute("csrf", token);
    }
    if ("POST".equals(r.getMethod()) && !token.equals(r.getParameter("csrf"))) {
      s.sendError(403);
      return;
    }
    chain.doFilter(req, res);
  }
}

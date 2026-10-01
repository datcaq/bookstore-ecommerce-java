package vn.edu.hcmute.kiemtragk.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;

public class EncodingFilter_24162019 implements Filter {
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    req.setCharacterEncoding("UTF-8");
    res.setCharacterEncoding("UTF-8");
    HttpServletResponse response = (HttpServletResponse) res;
    response.setHeader("X-Content-Type-Options", "nosniff");
    response.setHeader("X-Frame-Options", "SAMEORIGIN");
    response.setHeader("Cache-Control", "no-store");
    try {
      chain.doFilter(req, res);
    } catch (Exception e) {
      req.getServletContext().log("Không xử lý được yêu cầu BookStore", e);
      if (response.isCommitted()) throw new ServletException(e);
      response.reset();
      response.setStatus(503);
      response.setContentType("text/html;charset=UTF-8");
      req.setAttribute(
          "error",
          "Không thể xử lý yêu cầu. Kiểm tra SQL Server, file cấu hình local và nhật ký Tomcat.");
      req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, res);
    }
  }
}

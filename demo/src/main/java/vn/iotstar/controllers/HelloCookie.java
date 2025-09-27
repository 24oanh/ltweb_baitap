package vn.iotstar.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns= {"/hello1"})
public class HelloCookie extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");

        String user = null;

       
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("username".equals(c.getName())) {
                    user = c.getValue();
                }
            }
        }

      
        if (user != null) {
            resp.getWriter().println("<h2>Xin chào, " + user + "!</h2>");
        } else {
            resp.getWriter().println("<h2>Bạn chưa đăng nhập!</h2>");
            resp.getWriter().println("<a href='" + req.getContextPath() + "/login.html'>Đăng nhập</a>");
        
        }
    }
}

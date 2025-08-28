package week2.Controllers;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

// Filter này áp dụng cho tất cả URL bắt đầu bằng /views/*
@WebFilter("/views/*")
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Không cần init gì thêm
    }

    @Override
    public void doFilter(jakarta.servlet.ServletRequest request,
                         jakarta.servlet.ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        // Lấy URL người dùng đang truy cập
        String path = req.getRequestURI();

        // Nếu chưa login mà lại vào home.jsp → chặn lại
        if (path.endsWith("home.jsp")) {
            if (session == null || session.getAttribute("account") == null) {
                // Quay về login.jsp kèm theo thông báo
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }
        }

        // Cho phép đi tiếp
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Không cần destroy gì thêm
    }
}

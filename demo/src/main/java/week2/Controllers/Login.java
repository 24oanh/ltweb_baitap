package week2.Controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Nếu gõ trực tiếp /login → chuyển về trang login.jsp
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
    }

    // Xử lý login khi bấm nút "Đăng nhập"
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // Kiểm tra cứng (sau này thay bằng DB)
        if (("admin".equals(username) && "123".equals(password)) ||
            ("oanh".equals(username) && "123".equals(password))) {
            
            // Tạo session
            HttpSession session = req.getSession();
            session.setAttribute("account", username);

            // Chuyển sang home.jsp
            resp.sendRedirect(req.getContextPath() + "/views/home.jsp");
        } else {
            // Sai → báo lỗi và quay lại login.jsp
            req.setAttribute("alert", "Sai username hoặc password!");
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
        }
    }
}

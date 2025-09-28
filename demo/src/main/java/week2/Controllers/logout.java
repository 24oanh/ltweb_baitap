package week2.Controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class logout extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Hủy session
        HttpSession session = req.getSession(false); // false = không tạo mới
        if (session != null) {
            session.invalidate();
        }

        // Chuyển về login.jsp
        resp.sendRedirect(req.getContextPath() + "/views/login.jsp");
    }
}

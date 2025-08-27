package week2.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

import week2.model.User;  

@WebServlet(urlPatterns="/waiting")
public class WaitingController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public WaitingController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false); 
        if (session != null && session.getAttribute("account") != null) {
            User u = (User) session.getAttribute("account");
            req.setAttribute("username", u.getUserName());

            if (u.getId() == 1) {
                resp.sendRedirect(req.getContextPath() + "/admin/home");
            } else if (u.getId() == 2) {
                resp.sendRedirect(req.getContextPath() + "/manager/home");
            } else {
                resp.sendRedirect(req.getContextPath() + "/home");
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}

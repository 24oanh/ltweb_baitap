package vn.iotstar.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import jakarta.servlet.http.Cookie;

@WebServlet(urlPatterns= {"/login"})

public class LoginCookie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginCookie() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
    IOException {
	    resp.setContentType("text/html");
	    String user = req.getParameter("username");
	    String pass = req.getParameter("password");
	    if(user.equals("oanh") && pass.equals("1812"))
	    {
		    Cookie cookie = new Cookie("username", user);
		    cookie.setMaxAge(30);
		    resp.addCookie(cookie);
		    resp.sendRedirect(req.getContextPath() + "/hello1");
	    }else {
	    	resp.sendRedirect(req.getContextPath() + "/xin-chao");
	    }
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().println("<form method='post' action='login'>"
                + "User: <input type='text' name='username'/><br/>"
                + "Pass: <input type='password' name='password'/><br/>"
                + "<input type='submit' value='Login'/>"
                + "</form>");
    }

}
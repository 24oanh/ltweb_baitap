package test;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/simple")
public class SimpleServlet extends HttpServlet {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init() {
        System.out.println("=== SIMPLE SERVLET INIT ===");
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        System.out.println("=== SIMPLE SERVLET GET ===");
        response.getWriter().println("WORKING!");
    }
}
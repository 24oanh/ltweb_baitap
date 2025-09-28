package week2.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import week2.Dao.impl.CategoryDaoImpl;
import week2.model.Category;

@WebServlet("/test-insert")
public class TestInsertServlet extends HttpServlet {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<html><head><title>Test Category Insert</title></head><body>");
        out.println("<h2>Testing Category Insert</h2>");
        
        CategoryDaoImpl dao = new CategoryDaoImpl();
        
        try {
            // Tạo category test
            Category category = new Category();
            category.setName("Test from Servlet " + System.currentTimeMillis());
            category.setImage("servlet_test.jpg");
            
            out.println("<p><strong>Inserting category:</strong></p>");
            out.println("<ul>");
            out.println("<li>Name: " + category.getName() + "</li>");
            out.println("<li>Image: " + category.getImage() + "</li>");
            out.println("</ul>");
            
            // Insert
            dao.insert(category);
            out.println("<p style='color: green;'>✅ Insert completed!</p>");
            
            // Kiểm tra kết quả
            out.println("<p><strong>Current categories:</strong></p>");
            out.println("<ul>");
            dao.getAll().forEach(c -> {
                out.println("<li>ID: " + c.getId() + " - " + c.getName() + " - " + c.getImage() + "</li>");
            });
            out.println("</ul>");
            
        } catch (Exception e) {
            out.println("<p style='color: red;'>❌ Error: " + e.getMessage() + "</p>");
            e.printStackTrace();
        }
        
        out.println("</body></html>");
    }
}
package week2.Controller;

import week2.Service.CategoryService;
import week2.Service.impl.CategoryServiceImpl;
import week2.model.Category;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/category/delete")
public class CategoryDeleteController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        handleDeleteRequest(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        handleDeleteRequest(req, resp);
    }
    
    private void handleDeleteRequest(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        // Set encoding
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        
        String idParam = req.getParameter("id");
        
        try {
            // Validate input
            if (idParam == null || idParam.trim().isEmpty()) {
                req.getSession().setAttribute("error", "ID danh mục không được để trống!");
                resp.sendRedirect(req.getContextPath() + "/admin/category/list");
                return;
            }
            
            int categoryId;
            try {
                categoryId = Integer.parseInt(idParam.trim());
            } catch (NumberFormatException e) {
                req.getSession().setAttribute("error", "ID danh mục không hợp lệ!");
                resp.sendRedirect(req.getContextPath() + "/admin/category/list");
                return;
            }
            
            // Kiểm tra ID có hợp lệ không (> 0)
            if (categoryId <= 0) {
                req.getSession().setAttribute("error", "ID danh mục phải lớn hơn 0!");
                resp.sendRedirect(req.getContextPath() + "/admin/category/list");
                return;
            }
            
            // Kiểm tra danh mục có tồn tại không
            Category existingCategory = categoryService.findById(categoryId);
            if (existingCategory == null) {
                req.getSession().setAttribute("error", "Không tìm thấy danh mục với ID: " + categoryId);
                resp.sendRedirect(req.getContextPath() + "/admin/category/list");
                return;
            }
            
            System.out.println("Attempting to delete category: ID=" + categoryId + 
                             ", Name=" + existingCategory.getName());
            
            // Thực hiện xóa
            boolean deleteResult = categoryService.delete(categoryId);
            
            if (deleteResult) {
                req.getSession().setAttribute("success", 
                    "Đã xóa thành công danh mục: " + existingCategory.getName());
                System.out.println("Successfully deleted category ID: " + categoryId);
            } else {
                req.getSession().setAttribute("error", 
                    "Không thể xóa danh mục. Có thể danh mục này đang được sử dụng.");
                System.out.println("Failed to delete category ID: " + categoryId);
            }
            
        } catch (Exception e) {
            System.err.println("Error deleting category: " + e.getMessage());
            e.printStackTrace();
            req.getSession().setAttribute("error", 
                "Có lỗi xảy ra khi xóa danh mục: " + e.getMessage());
        }
        
        // Redirect về trang danh sách
        resp.sendRedirect(req.getContextPath() + "/admin/category/list");
    }
}
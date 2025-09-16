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

@WebServlet("/admin/category/edit")
public class CategoryEditController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        // Set encoding
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        
        String idParam = req.getParameter("id");
        
        try {
            // Validate input
            if (idParam == null || idParam.trim().isEmpty()) {
                req.setAttribute("error", "ID danh mục không được để trống!");
                req.getRequestDispatcher("/views/edit-category.jsp").forward(req, resp);
                return;
            }
            
            int categoryId;
            try {
                categoryId = Integer.parseInt(idParam.trim());
            } catch (NumberFormatException e) {
                req.setAttribute("error", "ID danh mục không hợp lệ!");
                req.getRequestDispatcher("/views/edit-category.jsp").forward(req, resp);
                return;
            }
            
            // Kiểm tra ID có hợp lệ không (> 0)
            if (categoryId <= 0) {
                req.setAttribute("error", "ID danh mục phải lớn hơn 0!");
                req.getRequestDispatcher("/views/edit-category.jsp").forward(req, resp);
                return;
            }
            
            // Lấy thông tin danh mục
            Category category = categoryService.findById(categoryId);
            
            if (category == null) {
                req.setAttribute("error", "Không tìm thấy danh mục với ID: " + categoryId);
                req.getRequestDispatcher("/views/edit-category.jsp").forward(req, resp);
                return;
            }
            
            System.out.println("Loading category for edit: ID=" + categoryId + 
                             ", Name=" + category.getName());
            
            req.setAttribute("category", category);
            req.getRequestDispatcher("/views/edit-category.jsp").forward(req, resp);
            
        } catch (Exception e) {
            System.err.println("Error loading category for edit: " + e.getMessage());
            e.printStackTrace();
            req.setAttribute("error", "Có lỗi xảy ra khi tải thông tin danh mục: " + e.getMessage());
            req.getRequestDispatcher("/views/edit-category.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        // Set encoding
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        
        String idParam = req.getParameter("id");
        String name = req.getParameter("name");
        String image = req.getParameter("image");
        
        try {
            // Validate ID
            if (idParam == null || idParam.trim().isEmpty()) {
                req.setAttribute("error", "ID danh mục không được để trống!");
                req.getRequestDispatcher("/views/admin/edit-category.jsp").forward(req, resp);
                return;
            }
            
            int categoryId;
            try {
                categoryId = Integer.parseInt(idParam.trim());
            } catch (NumberFormatException e) {
                req.setAttribute("error", "ID danh mục không hợp lệ!");
                req.getRequestDispatcher("/views/admin/edit-category.jsp").forward(req, resp);
                return;
            }
            
            if (categoryId <= 0) {
                req.setAttribute("error", "ID danh mục phải lớn hơn 0!");
                req.getRequestDispatcher("/views/admin/edit-category.jsp").forward(req, resp);
                return;
            }
            
            // Validate name
            if (name == null || name.trim().isEmpty()) {
                Category category = categoryService.findById(categoryId);
                req.setAttribute("category", category);
                req.setAttribute("error", "Tên danh mục không được để trống!");
                req.getRequestDispatcher("/views/admin/edit-category.jsp").forward(req, resp);
                return;
            }
            
            name = name.trim();
            
            // Validate name length
            if (name.length() > 100) {
                Category category = categoryService.findById(categoryId);
                req.setAttribute("category", category);
                req.setAttribute("error", "Tên danh mục không được quá 100 ký tự!");
                req.getRequestDispatcher("/views/admin/edit-category.jsp").forward(req, resp);
                return;
            }
            
            // Kiểm tra danh mục có tồn tại không
            Category existingCategory = categoryService.findById(categoryId);
            if (existingCategory == null) {
                req.setAttribute("error", "Không tìm thấy danh mục với ID: " + categoryId);
                req.getRequestDispatcher("/views/admin/edit-category.jsp").forward(req, resp);
                return;
            }
            
            // Kiểm tra trùng tên (nếu tên khác với tên hiện tại)
            if (!name.equals(existingCategory.getName())) {
                if (categoryService.isNameExists(name, categoryId)) {
                    req.setAttribute("category", existingCategory);
                    req.setAttribute("error", "Tên danh mục đã tồn tại!");
                    req.getRequestDispatcher("/views/admin/edit-category.jsp").forward(req, resp);
                    return;
                }
            }
            
            // Chuẩn hóa image path
            if (image != null) {
                image = image.trim();
                if (image.isEmpty()) {
                    image = null;
                }
            }
            
            System.out.println("Updating category: ID=" + categoryId + 
                             ", Name=" + name + ", Image=" + image);
            
            // Tạo object category để update
            Category category = new Category();
            category.setId(categoryId);
            category.setName(name);
            category.setImage(image);
            
            // Thực hiện update
            boolean updateResult = categoryService.update(category);
            
            if (updateResult) {
                req.getSession().setAttribute("success", 
                    "Cập nhật thành công danh mục: " + name);
                System.out.println("Successfully updated category ID: " + categoryId);
            } else {
                req.setAttribute("category", category);
                req.setAttribute("error", "Không thể cập nhật danh mục. Vui lòng thử lại!");
                req.getRequestDispatcher("/views/admin/edit-category.jsp").forward(req, resp);
                return;
            }
            
        } catch (Exception e) {
            System.err.println("Error updating category: " + e.getMessage());
            e.printStackTrace();
            
            // Load lại category để hiển thị form
            try {
                int categoryId = Integer.parseInt(idParam);
                Category category = categoryService.findById(categoryId);
                req.setAttribute("category", category);
            } catch (Exception ex) {
                // Ignore
            }
            
            req.setAttribute("error", "Có lỗi xảy ra khi cập nhật danh mục: " + e.getMessage());
            req.getRequestDispatcher("/views/admin/edit-category.jsp").forward(req, resp);
            return;
        }
        
        // Redirect về trang danh sách
        resp.sendRedirect(req.getContextPath() + "/admin/category/list");
    }
}
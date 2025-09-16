package week2.Controller;

import week2.Service.CategoryService;
import week2.Service.impl.CategoryServiceImpl;
import week2.model.Category;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;

@WebServlet("/admin/category/add")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
    maxFileSize = 1024 * 1024 * 10,      // 10 MB
    maxRequestSize = 1024 * 1024 * 15    // 15 MB
)
public class CategoryAddController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CategoryService categoryService = new CategoryServiceImpl();
    
    // Đường dẫn lưu file upload
    private static final String UPLOAD_DIR = "uploads";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        req.getRequestDispatcher("/views/add-category.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        // Set encoding
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        
        try {
            String name = req.getParameter("name");
            String imageUrl = "";
            
            // Validate input
            if (name == null || name.trim().isEmpty()) {
                req.setAttribute("error", "Tên danh mục không được để trống!");
                req.getRequestDispatcher("/views/add-category.jsp").forward(req, resp);
                return;
            }
            
            // Xử lý upload file
            Part filePart = req.getPart("image");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = extractFileName(filePart);
                if (fileName != null && !fileName.isEmpty()) {
                    // Tạo tên file unique để tránh trùng lặp
                    String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
                    
                    // Đường dẫn thực tế để lưu file
                    String applicationPath = req.getServletContext().getRealPath("");
                    String uploadPath = applicationPath + File.separator + UPLOAD_DIR;
                    
                    // Tạo thư mục nếu chưa tồn tại
                    File uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                    }
                    
                    // Lưu file
                    String filePath = uploadPath + File.separator + uniqueFileName;
                    filePart.write(filePath);
                    
                    // Lưu đường dẫn relative để hiển thị trên web
                    imageUrl = UPLOAD_DIR + "/" + uniqueFileName;
                }
            }
            
            // Tạo category object
            Category category = new Category();
            category.setName(name.trim());
            category.setImage(imageUrl);
            
            // Lưu vào database
            boolean success = categoryService.insert(category);
            
            if (success) {
                resp.sendRedirect(req.getContextPath() + "/admin/category/list");
            } else {
                req.setAttribute("error", "Có lỗi xảy ra khi thêm danh mục!");
                req.getRequestDispatcher("/views/add-category.jsp").forward(req, resp);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Lỗi hệ thống: " + e.getMessage());
            req.getRequestDispatcher("/views/add-category.jsp").forward(req, resp);
        }
    }
    
    // Helper method để extract tên file từ Part
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "";
    }
}
package baitap4.Controller.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import baitap4.Configs.JPA_Config;
import baitap4.Dao.Impl.UserDaoImpl;
import baitap4.Entity.AppUser;
import baitap4.Service.UserService;
import baitap4.Service.Impl.UserServiceImpl;
import baitap4.Util.Constant;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/updateProfile")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024* 10	,
    maxFileSize = 1024 * 1024 * 20,	
    maxRequestSize = 1024 * 1024 * 100
)
public class ProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        EntityManager em = JPA_Config.getEntityManager();
        UserDaoImpl userDao = new UserDaoImpl(em);
        userService = new UserServiceImpl(userDao);
        System.out.println("=== ProfileServlet INITIALIZED ===");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        System.out.println("=== ProfileServlet doGet called ===");
        response.setContentType("text/html");
        response.getWriter().println("<h1>ProfileServlet is working!</h1>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("=== ProfileServlet doPost called ===");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // Lấy dữ liệu từ form
        String fullname = request.getParameter("fullname");
        String phone = request.getParameter("phone");
        Part imagePart = request.getPart("image");

        // Lấy thông tin của người dùng từ session (nếu có)
        HttpSession session = request.getSession();
        AppUser currentUser = (AppUser) session.getAttribute("user"); // Chỉ khi đã đăng nhập

        // Nếu không có session (tức là người dùng không đăng nhập), tạo đối tượng mới
        if (currentUser == null) {
            currentUser = new AppUser(); // Tạo đối tượng mới nếu không có session
        }

        // Cập nhật thông tin người dùng
        currentUser.setFullname(fullname);
        currentUser.setPhone(phone);

        // Xử lý ảnh (nếu có)
        String imagePath = currentUser.getImage();
        if (imagePart != null && imagePart.getSize() > 0) {
            String fileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString()
                    .replaceAll("[^a-zA-Z0-9.\\-_]", "_");
            File uploadDir = new File(Constant.DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            String filePath = Constant.DIR + File.separator + fileName;
            try {
                imagePart.write(filePath);
                imagePath = "/profile/" + fileName;
            } catch (IOException e) {
                System.err.println("Không thể ghi file ảnh: " + e.getMessage());
                e.printStackTrace();
            }
        }

        currentUser.setImage(imagePath);  // Cập nhật đường dẫn ảnh

        // Lưu thông tin người dùng vào DB
        userService.updateUser(currentUser);  // Cập nhật thông tin vào cơ sở dữ liệu

        // Cập nhật lại session với thông tin người dùng mới
        session.setAttribute("user", currentUser);

        // Chuyển hướng về trang home.jsp
        response.sendRedirect(request.getContextPath() + "/views/web/home.jsp");

    }
}
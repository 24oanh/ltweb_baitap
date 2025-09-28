package week2.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

// Import for email sending
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

@WebServlet("/forgot")
public class ForgotController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int TOKEN_LENGTH = 32;
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$"
    );

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        req.getRequestDispatcher("/views/forgot.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        String email = req.getParameter("email");
        
        // Validate email input
        if (email == null || email.trim().isEmpty()) {
            req.setAttribute("alertType", "error");
            req.setAttribute("alert", "Vui lòng nhập địa chỉ email!");
            req.getRequestDispatcher("/views/forgot.jsp").forward(req, resp);
            return;
        }
        
        email = email.trim().toLowerCase();
        
        // Validate email format
        if (!isValidEmail(email)) {
            req.setAttribute("alertType", "error");
            req.setAttribute("alert", "Định dạng email không hợp lệ!");
            req.getRequestDispatcher("/views/forgot.jsp").forward(req, resp);
            return;
        }
        
        try {
            // Kiểm tra email có tồn tại trong database không
            if (isEmailExists(email)) {
                // Tạo reset token
                String resetToken = generateResetToken();
                
                // Lưu token vào database với thời gian hết hạn
                if (saveResetToken(email, resetToken)) {
                    // Gửi email reset password
                    if (sendResetEmail(email, resetToken)) {
                        req.setAttribute("alertType", "success");
                        req.setAttribute("alert", 
                            "Hướng dẫn đặt lại mật khẩu đã được gửi đến email của bạn. " +
                            "Vui lòng kiểm tra hộp thư và làm theo hướng dẫn.");
                    } else {
                        req.setAttribute("alertType", "error");
                        req.setAttribute("alert", 
                            "Có lỗi xảy ra khi gửi email. Vui lòng thử lại sau.");
                    }
                } else {
                    req.setAttribute("alertType", "error");
                    req.setAttribute("alert", 
                        "Có lỗi xảy ra khi lưu token. Vui lòng thử lại sau.");
                }
            } else {
                // Không tìm thấy email, nhưng vẫn hiển thị thông báo chung để bảo mật
                req.setAttribute("alertType", "info");
                req.setAttribute("alert", 
                    "Nếu email này tồn tại trong hệ thống, " +
                    "hướng dẫn đặt lại mật khẩu sẽ được gửi đến email của bạn.");
            }
            
        } catch (Exception e) {
            System.err.println("Error in forgot password process: " + e.getMessage());
            e.printStackTrace();
            
            req.setAttribute("alertType", "error");
            req.setAttribute("alert", "Có lỗi xảy ra. Vui lòng thử lại sau.");
        }
        
        req.getRequestDispatcher("/views/forgot.jsp").forward(req, resp);
    }
    
    /**
     * Validate email format
     */
    private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
    
    /**
     * Kiểm tra email có tồn tại trong database không
     */
    private boolean isEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Database error in isEmailExists: " + e.getMessage());
            e.printStackTrace();
            return false; // Return false khi có lỗi để báo không tồn tại email
        } catch (Exception e) {
            System.err.println("Error in isEmailExists: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        
        return false;
    }
    
    /**
     * Tạo reset token ngẫu nhiên
     */
    private String generateResetToken() {
        SecureRandom random = new SecureRandom();
        StringBuilder token = new StringBuilder();
        
        for (int i = 0; i < TOKEN_LENGTH; i++) {
            token.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        
        return token.toString();
    }
    
    /**
     * Lưu reset token vào database
     */
    private boolean saveResetToken(String email, String token) {
        // Xóa các token cũ của email này
        String deleteSql = "DELETE FROM password_reset_tokens WHERE email = ?";
        
        // Thêm token mới với thời gian hết hạn (24 giờ)
        String insertSql = "INSERT INTO password_reset_tokens (email, token, created_at, expires_at) " +
                          "VALUES (?, ?, NOW(), DATE_ADD(NOW(), INTERVAL 24 HOUR))";
        
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // Bắt đầu transaction
            
            try {
                // Xóa token cũ
                try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                    deleteStmt.setString(1, email);
                    deleteStmt.executeUpdate();
                    System.out.println("Deleted old tokens for email: " + email);
                }
                
                // Thêm token mới
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    insertStmt.setString(1, email);
                    insertStmt.setString(2, token);
                    int result = insertStmt.executeUpdate();
                    
                    if (result > 0) {
                        conn.commit(); // Commit transaction
                        System.out.println("Successfully saved reset token for email: " + email);
                        return true;
                    } else {
                        conn.rollback();
                        System.err.println("Failed to insert reset token");
                        return false;
                    }
                }
            } catch (SQLException e) {
                conn.rollback(); // Rollback nếu có lỗi
                throw e;
            }
        } catch (SQLException e) {
            System.err.println("Database error in saveResetToken: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.err.println("Error in saveResetToken: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Gửi email reset password
     */
    private boolean sendResetEmail(String email, String token) {
        try {
            String resetUrl = getResetUrl(token);
            String subject = "Đặt lại mật khẩu";
            String content = buildEmailContent(resetUrl);
            
            // Thực tế gửi email
            return sendEmailUsingGmail(email, subject, content);
            
        } catch (Exception e) {
            System.err.println("Error sending reset email: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Gửi email thực tế sử dụng Gmail SMTP
     */
    private boolean sendEmailUsingGmail(String toEmail, String subject, String content) {
        // Cấu hình email - bạn cần thay đổi thông tin này
        String fromEmail = "oanhoanh2004.vn@gmail.com"; // Email của bạn
        String password = "your-app-password"; // App password của Gmail
        
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        
        // Tạo Authenticator
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        
        try {
            Session session = Session.getInstance(props, auth);
            
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setContent(content, "text/html; charset=utf-8");
            
            Transport.send(message);
            System.out.println("Email sent successfully to: " + toEmail);
            return true;
            
        } catch (MessagingException e) {
            System.err.println("Failed to send email: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Tạo URL reset password
     */
    private String getResetUrl(String token) {
        String baseUrl = "http://localhost:8080"; // Hoặc từ config
        return baseUrl + "/reset-password?token=" + token;
    }
    
    /**
     * Tạo nội dung email
     */
    private String buildEmailContent(String resetUrl) {
        return String.format(
            "<html><body style='font-family: Arial, sans-serif;'>" +
            "<div style='max-width: 600px; margin: 0 auto; padding: 20px;'>" +
            "<h2 style='color: #333;'>Đặt lại mật khẩu</h2>" +
            "<p>Chào bạn,</p>" +
            "<p>Chúng tôi nhận được yêu cầu đặt lại mật khẩu cho tài khoản của bạn.</p>" +
            "<p>Vui lòng nhấp vào nút bên dưới để đặt lại mật khẩu:</p>" +
            "<div style='text-align: center; margin: 30px 0;'>" +
            "<a href='%s' style='background-color: #007bff; color: white; padding: 12px 30px; " +
            "text-decoration: none; border-radius: 5px; display: inline-block;'>Đặt lại mật khẩu</a>" +
            "</div>" +
            "<p>Hoặc copy và paste liên kết sau vào trình duyệt:</p>" +
            "<p style='word-break: break-all; background-color: #f8f9fa; padding: 10px; border-radius: 3px;'>%s</p>" +
            "<p><strong>Lưu ý:</strong> Liên kết này sẽ hết hạn sau 24 giờ.</p>" +
            "<p>Nếu bạn không yêu cầu đặt lại mật khẩu, vui lòng bỏ qua email này.</p>" +
            "<br><p>Trân trọng,<br><strong>Đội ngũ hỗ trợ</strong></p>" +
            "</div></body></html>",
            resetUrl, resetUrl
        );
    }
}
package vn.iotstar.service;

import vn.iotstar.entity.User;
import vn.iotstar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // Salt cố định để tăng độ bảo mật (trong thực tế nên dùng salt riêng cho mỗi user)
    private static final String SALT = "mySecretSalt123";

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User save(User user) {
        if (user.getId() == null) {
            // Mã hóa password bằng SHA-256 + salt
            user.setPassword(encodePassword(user.getPassword()));
        }
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Mã hóa password bằng SHA-256 với salt
     */
    private String encodePassword(String rawPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String saltedPassword = rawPassword + SALT;
            byte[] encodedHash = digest.digest(saltedPassword.getBytes(StandardCharsets.UTF_8));
            
            // Chuyển byte array thành hex string
            StringBuilder hexString = new StringBuilder(2 * encodedHash.length);
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Không thể mã hóa password", e);
        }
    }

    /**
     * Kiểm tra password có khớp không
     */
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return encodePassword(rawPassword).equals(encodedPassword);
    }

    /**
     * Xác thực user login
     */
    public User authenticate(String username, String password) {
        Optional<User> userOpt = findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (checkPassword(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    /**
     * Đổi password cho user
     */
    public boolean changePassword(Long userId, String oldPassword, String newPassword) {
        Optional<User> userOpt = findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (checkPassword(oldPassword, user.getPassword())) {
                user.setPassword(encodePassword(newPassword));
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }
}
package week2.Dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import week2.Dao.UserDao;
import week2.model.User;
import week2.Controller.DBConnection;

public class UserDaoImpl implements UserDao {
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    // Lấy user theo username (dùng cho login)
    @Override
    public User findByUsername(String username) {
        String sql = "SELECT * FROM [User] WHERE username = ?";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                return mapUser(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return null;
    }

    // Lấy user theo email
    @Override
    public User findByEmail(String email) {
        String sql = "SELECT * FROM [User] WHERE email = ?";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                return mapUser(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return null;
    }

    // Lấy user theo số điện thoại
    @Override
    public User findByPhone(String phone) {
        String sql = "SELECT * FROM [User] WHERE phone = ?";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, phone);
            rs = ps.executeQuery();
            if (rs.next()) {
                return mapUser(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return null;
    }

    // Thêm user mới (đăng ký)
    @Override
    public void insert(User user) {
        String sql = "INSERT INTO [User] (email, username, fullname, password, avatar, roleid, phone, createdDate) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, GETDATE())";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getUserName());
            ps.setString(3, user.getFullName());
            ps.setString(4, user.getPassWord());
            ps.setString(5, user.getAvatar()); // có thể null
            ps.setInt(6, user.getRoleid());    // 0 = user, 1 = admin
            ps.setString(7, user.getPhone());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    // Cập nhật user (ví dụ đổi mật khẩu, đổi avatar, đổi thông tin)
    @Override
    public void update(User user) {
        String sql = "UPDATE [User] SET email=?, fullname=?, password=?, avatar=?, roleid=?, phone=? WHERE username=?";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getFullName());
            ps.setString(3, user.getPassWord());
            ps.setString(4, user.getAvatar());
            ps.setInt(5, user.getRoleid());
            ps.setString(6, user.getPhone());
            ps.setString(7, user.getUserName());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    // Hàm map dữ liệu từ ResultSet -> User object
    private User mapUser(ResultSet rs) throws Exception {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setEmail(rs.getString("email"));
        user.setUserName(rs.getString("username"));
        user.setFullName(rs.getString("fullname"));
        user.setPassWord(rs.getString("password"));
        user.setAvatar(rs.getString("avatar"));
        user.setRoleid(rs.getInt("roleid"));
        user.setPhone(rs.getString("phone"));
        user.setCreatedDate(rs.getDate("createdDate"));
        return user;
    }

    // Đóng kết nối
    private void closeAll() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Nếu interface có get(String username) thì dùng lại findByUsername
    @Override
    public User get(String username) {
        return findByUsername(username);
    }
}

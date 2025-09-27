package week2baitap2.Dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import week2baitap2.Connection.DBConnect;
import week2baitap2.Dao.UserDao;
import week2baitap2.Model.User;

public class UserDaoimpl implements UserDao {

    @Override
    public void insert(User user) {
        String sql = "INSERT INTO [User](email, username, fullname, password, avatar, phone, createddate) " +
                     "VALUES (?,?,?,?,?,?,GETDATE())";
        try (
            Connection conn = new DBConnect().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getUserName());
            ps.setString(3, user.getFullName());
            ps.setString(4, user.getPassWord());
            ps.setString(5, user.getAvatar());
            ps.setString(6, user.getPhone());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkExistEmail(String email) {
        String query = "SELECT * FROM [User] WHERE email = ?";
        try (
            Connection conn = new DBConnect().getConnection();
            PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean checkExistUsername(String username) {
        String query = "SELECT * FROM [User] WHERE username = ?";
        try (
            Connection conn = new DBConnect().getConnection();
            PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean checkExistPhone(String phone) {
        String query = "SELECT * FROM [User] WHERE phone = ?";
        try (
            Connection conn = new DBConnect().getConnection();
            PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setString(1, phone);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean register(User user) {
        try {
            insert(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

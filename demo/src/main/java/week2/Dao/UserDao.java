package week2.Dao;

import week2.model.User;

public interface UserDao {
    User get(String username);           // lấy user theo username
    void insert(User user);              // thêm user mới
    User findByEmail(String email);      // tìm user theo email
    User findByUsername(String username);// tìm user theo username
    User findByPhone(String phone);      // tìm user theo phone
    void update(User user);              // cập nhật user
}

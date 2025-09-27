package week2baitap2.Service.Impl;

import week2baitap2.Dao.UserDao;
import week2baitap2.Dao.impl.UserDaoimpl;
import week2baitap2.Model.User;
import week2baitap2.Service.UserService;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoimpl();

    @Override
    public boolean Register(String email, String password, String username, String fullname, String phone) {
        // Kiểm tra username và email tồn tại
        if (userDao.checkExistUsername(username) || userDao.checkExistEmail(email)) {
            return false;
        }

        // Tạo user mới
        User user = new User(0, email, username, fullname, password, null);
        return userDao.register(user);
    }

    @Override
    public boolean checkExistEmail(String email) {
        return userDao.checkExistEmail(email);
    }

    @Override
    public boolean checkExistUsername(String username) {
        return userDao.checkExistUsername(username);
    }

    @Override
    public void insert(User user) {
        userDao.insert(user);
    }
    @Override
    public boolean checkExistPhone(String phone) {
        return userDao.checkExistPhone(phone);
    }

}

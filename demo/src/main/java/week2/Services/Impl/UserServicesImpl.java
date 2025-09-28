package week2.Services.Impl;

import week2.Dao.UserDao;
import week2.Dao.Impl.UserDaoImpl;
import week2.Model.User;
import week2.Services.UserServices;

public class UserServicesImpl implements UserServices {
    private UserDaoImpl userDao = new UserDaoImpl();

    @Override
    public User login(String username, String password) {
        User user = this.get(username);  // lấy user theo username
        if (user != null && password.equals(user.getPassWord())) {
            return user; // đúng mật khẩu
        }
        return null; // sai username hoặc password
    }

    @Override
    public User get(String username) {
        return ((UserDao) userDao).get(username);
    }
}

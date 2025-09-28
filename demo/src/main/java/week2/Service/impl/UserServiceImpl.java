package week2.Service.impl;

import week2.Dao.UserDao;
import week2.Dao.impl.UserDaoImpl;
import week2.model.User;
import week2.Service.UserService;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    @Override
    public void insert(User user) {
        userDao.insert(user);
    }

    @Override
    public boolean register(String email, String password, String username, String fullname, String phone) {
        if (userDao.findByEmail(email) != null || userDao.findByUsername(username) != null || userDao.findByPhone(phone) != null) {
            return false; // Email, username hoặc phone đã tồn tại
        }
        User user = new User();
        user.setEmail(email);
        user.setPassWord(password);
        user.setUserName(username);
        user.setFullName(fullname);
        user.setPhone(phone);
        userDao.insert(user);
        return true;
    }

    @Override
    public boolean checkExistEmail(String email) {
        return userDao.findByEmail(email) != null;
    }

    @Override
    public boolean checkExistUsername(String username) {
        return userDao.findByUsername(username) != null;
    }

    @Override
    public boolean checkExistPhone(String phone) {
        return userDao.findByPhone(phone) != null;
    }

    @Override
    public User login(String username, String password) {
        User user = userDao.findByUsername(username);
        if (user != null && user.getPassWord().equals(password)) {
            return user;
        }
        return null;
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public boolean updatePassword(String email, String newPassword) {
        User user = userDao.findByEmail(email);
        if (user != null) {
            user.setPassWord(newPassword);
            userDao.update(user);
            return true;
        }
        return false;
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

	@Override
	public User get(String username) {
		// TODO Auto-generated method stub
		return null;
	}
}

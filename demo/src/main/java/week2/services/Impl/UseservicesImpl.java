package week2.services.Impl;

import week2.Dao.UseDao;
import week2.Dao.Impl.UseDaoImpl;
import week2.model.User;
import week2.services.Useservices;

public class UseservicesImpl implements Useservices {
    private UseDao userDao;

    public UseservicesImpl() {
        userDao = (UseDao) new UseDaoImpl(); 
    }

    @Override
    public User login(String username, String password) {
        User user = userDao.get(username);
        if (user != null && password.equals(user.getPassWord())) {
            return user;
        }
        return null;
    }

    @Override
    public User get(String username) {
        return userDao.get(username);
    }
}

package week2.Dao;

import week2.Model.User;

public interface UserDao {
    User login(String username, String password);
    User get(String username);
}

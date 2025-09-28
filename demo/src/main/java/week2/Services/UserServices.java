package week2.Services;

import week2.Model.User;

public interface UserServices {
    User login(String username, String password);
    User get(String username);
}

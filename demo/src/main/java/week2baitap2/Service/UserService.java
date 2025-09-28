package week2baitap2.Service;

import week2baitap2.Model.User;

public interface UserService {
    void insert(User user);

    boolean checkExistEmail(String email);

    boolean checkExistUsername(String username);

    boolean checkExistPhone(String phone);

    boolean Register(String email, String password, String username, String fullname, String phone);
}

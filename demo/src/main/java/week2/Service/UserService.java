package week2.Service;

import week2.model.User;

public interface UserService {
    User login(String username, String password);
    User get(String username);

    void insert(User user);
    boolean register(String email, String password, String username, String fullname, String phone);

    boolean checkExistEmail(String email);
    boolean checkExistUsername(String username);
    boolean checkExistPhone(String phone);

    User findByEmail(String email);
    User findByUsername(String username);

    boolean updatePassword(String email, String newPassword);
    void update(User user);
}

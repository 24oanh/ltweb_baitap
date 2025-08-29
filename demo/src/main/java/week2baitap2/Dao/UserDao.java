package week2baitap2.Dao;

import week2baitap2.Model.User;

public interface UserDao {
    boolean checkExistEmail(String email);
    boolean checkExistUsername(String username);
    boolean register(User user);
	void insert(User user);
	boolean checkExistPhone(String phone);
}

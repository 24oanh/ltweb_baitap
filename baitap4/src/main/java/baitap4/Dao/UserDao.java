package baitap4.Dao;

import baitap4.Entity.AppUser;

public interface UserDao {

	void deleteUser(int id);

	void updateUser(AppUser user);

	AppUser getUserById(int id);

	void saveUser(AppUser user);
	
	}

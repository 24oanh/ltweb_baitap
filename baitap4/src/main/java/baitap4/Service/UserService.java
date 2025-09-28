package baitap4.Service;

import baitap4.Entity.AppUser;

public interface UserService {

	void deleteUser(int id);

	void updateUser(AppUser user);

	AppUser getUser(int id);

	void addUser(AppUser user);


   
}

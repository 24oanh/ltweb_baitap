package week2.services;

import week2.model.User;

public interface Useservices {
    User login(String username, String password);
    User get(String username);
}

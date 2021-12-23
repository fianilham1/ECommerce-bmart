package com.tes.buana.service;

import com.tes.buana.entity.Users;

public interface UserService {

    Users create(Users users);

    Users update(Users users);

    Users login(Users users);

    String forgotPassword(String username);

    void resetPassword(Users users);

    Users getUser(Users users);

    Users getUser(String username);

}

package com.cth.crm.settings.service;

import com.cth.crm.exception.LoginException;
import com.cth.crm.settings.domain.User;

import java.util.List;

public interface UserService {
    User login(String name, String pwd, String ip) throws LoginException;

    List<User> lookfor();

    List<User> getUserList();
}

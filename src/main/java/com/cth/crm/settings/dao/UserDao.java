package com.cth.crm.settings.dao;

import com.cth.crm.settings.domain.User;

import java.util.List;

public interface UserDao {
    User login(User user);

    List<User> lookfor();

}

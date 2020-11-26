package com.cth.crm.settings.dao;

import com.cth.crm.settings.domain.User;

public interface UserDao {
    User login(User user);
}

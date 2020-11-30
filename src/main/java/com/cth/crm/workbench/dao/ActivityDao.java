package com.cth.crm.workbench.dao;

import com.cth.crm.settings.domain.User;
import com.cth.crm.workbench.domain.Activity;

import java.util.List;

public interface ActivityDao {
    List<User> lookfor();

    int saveActivity(Activity at);
}

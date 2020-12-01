package com.cth.crm.workbench.dao;

import com.cth.crm.settings.domain.User;
import com.cth.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityDao {
    List<User> lookfor();

    int saveActivity(Activity at);

    List<Activity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    int deleteById(String[] param);
}

package com.cth.crm.workbench.service;

import com.cth.crm.settings.domain.User;
import com.cth.crm.workbench.domain.Activity;

import java.util.List;

public interface ActivityService {
    List<User> lookfor();

    int saveActivity(Activity at);
}

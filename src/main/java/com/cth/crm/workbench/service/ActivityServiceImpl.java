package com.cth.crm.workbench.service;

import com.cth.crm.settings.domain.User;
import com.cth.crm.utils.ServiceFactory;
import com.cth.crm.utils.SqlSessionUtil;
import com.cth.crm.workbench.dao.ActivityDao;

import java.util.List;

public class ActivityServiceImpl implements ActivityService {
    private ActivityDao ad = (ActivityDao) SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);

    @Override
    public List<User> lookfor() {
        List<User> ulist = ad.lookfor();
        return ulist;
    }
}
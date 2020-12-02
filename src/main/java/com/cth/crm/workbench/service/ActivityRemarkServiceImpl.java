package com.cth.crm.workbench.service;

import com.cth.crm.utils.SqlSessionUtil;
import com.cth.crm.workbench.dao.AcitvityRemarkDao;
import com.cth.crm.workbench.dao.ActivityDao;
import com.cth.crm.workbench.domain.ActivityRemark;

import java.util.List;

public class ActivityRemarkServiceImpl implements ActivityRemarkService {
    private AcitvityRemarkDao ard = SqlSessionUtil.getSqlSession().getMapper(AcitvityRemarkDao.class);
    @Override
    public List<ActivityRemark> getActivityRemarkByActivity(String activityId) {
        List<ActivityRemark> arlist = (List<ActivityRemark>) ard.getActivityRemarkByActivityId(activityId);
        return arlist;
    }
}

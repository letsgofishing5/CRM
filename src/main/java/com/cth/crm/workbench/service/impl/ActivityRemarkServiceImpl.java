package com.cth.crm.workbench.service.impl;

import com.cth.crm.utils.SqlSessionUtil;
import com.cth.crm.workbench.dao.AcitvityRemarkDao;
import com.cth.crm.workbench.domain.ActivityRemark;
import com.cth.crm.workbench.service.ActivityRemarkService;

import java.util.List;

public class ActivityRemarkServiceImpl implements ActivityRemarkService {
    private AcitvityRemarkDao ard = SqlSessionUtil.getSqlSession().getMapper(AcitvityRemarkDao.class);

    @Override
    public boolean saveNoteContent(ActivityRemark ars) {
        boolean flag = true;
        int count = ard.saveNoteContent(ars);
        if (count==0)
        {
            flag=false;
        }

        return flag;
    }

    @Override
    public boolean editRemarkById(ActivityRemark activityRemark) {
        boolean flag=true;
        int count = ard.editRemarkById(activityRemark);
        if (count==0)
        {
            flag=false;
        }
        return flag;
    }

    @Override
    public Boolean deleteRemarkById(String id) {
        boolean flag=true;
        int count = ard.deleteRemarkById(id);
        if (count==0)
        {
            flag=false;
        }
        return flag;
    }

    @Override
    public List<ActivityRemark> getActivityRemarkByActivity(String activityId) {
        List<ActivityRemark> arlist = (List<ActivityRemark>) ard.getActivityRemarkByActivityId(activityId);
        return arlist;
    }
}

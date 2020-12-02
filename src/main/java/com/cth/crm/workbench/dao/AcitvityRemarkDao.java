package com.cth.crm.workbench.dao;

import com.cth.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface AcitvityRemarkDao {
    int deletetByAid(String[] param);

    int selectByAid(String[] param);

    List<ActivityRemark> getActivityRemarkByActivityId(String activityId);
}

package com.cth.crm.workbench.dao;

import com.cth.crm.workbench.domain.ActivityRemark;
import com.cth.crm.workbench.service.ActivityRemarkService;

import java.util.List;
import java.util.Map;

public interface AcitvityRemarkDao {
    int deletetByAid(String[] param);

    int selectByAid(String[] param);

    List<ActivityRemark> getActivityRemarkByActivityId(String activityId);

    int deleteRemarkById(String id);

    int editRemarkById(ActivityRemark activityRemark);

    int saveNoteContent(ActivityRemark ars);
}

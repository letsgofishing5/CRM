package com.cth.crm.workbench.service;

import com.cth.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkService {
    List<ActivityRemark> getActivityRemarkByActivity(String activityId);
}

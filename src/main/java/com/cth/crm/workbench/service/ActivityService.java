package com.cth.crm.workbench.service;

import com.cth.crm.settings.domain.User;
import com.cth.crm.vo.PaginativeVO;
import com.cth.crm.workbench.domain.Activity;
import com.cth.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    List<User> lookfor();
    int saveActivity(Activity at);
    PaginativeVO<Activity> pageQuery(Map<String, Object> map);
    boolean deleteById(String[] param);
    Map<String, Object> selectById(String id);
    boolean editById(Map<String, Object> map);
    Activity detailById(String id);
    List<Activity> getActivityById(String clueId);
    List<Activity> getActivityAndNotClueId(Map<String, Object> map);

    List<Activity> getActivityByName(String aname);
}

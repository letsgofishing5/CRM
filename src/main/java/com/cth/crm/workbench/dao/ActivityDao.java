package com.cth.crm.workbench.dao;

import com.cth.crm.settings.domain.User;
import com.cth.crm.workbench.domain.Activity;
import com.cth.crm.workbench.domain.ActivityRemark;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ActivityDao {
    List<User> lookfor();

    int saveActivity(Activity at);

    List<Activity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    int deleteById(String[] param);

    Activity selectById(@Param("id") String id);

    int editById(Map<String, Object> map);

    Activity detailById(String id);

    List<Activity> getActivityById(String clueId);

    List<Activity> getActivityAndNotClueId(Map<String, Object> map);

    List<Activity> getActivityByName(String aname);
}

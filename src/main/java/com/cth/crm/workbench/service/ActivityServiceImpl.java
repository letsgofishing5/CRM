package com.cth.crm.workbench.service;

import com.cth.crm.settings.domain.User;
import com.cth.crm.utils.SqlSessionUtil;
import com.cth.crm.vo.PaginativeVO;
import com.cth.crm.workbench.dao.AcitvityRemarkDao;
import com.cth.crm.workbench.dao.ActivityDao;
import com.cth.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {
    private ActivityDao ad = (ActivityDao) SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private AcitvityRemarkDao ar = SqlSessionUtil.getSqlSession().getMapper(AcitvityRemarkDao.class);
    @Override
    public int saveActivity(Activity at) {
        return ad.saveActivity(at);
    }

    @Override
    public boolean deleteById(String[] param) {
        boolean flag=true;
        int count1 = ar.selectByAid(param);
        int count2 = ar.deletetByAid(param);
        if (count1!=count2)
        {
            flag=false;
        }

        int count3 = ad.deleteById(param);
        if (count3!=param.length) {
            flag=false;
        }
        return true;
    }

    @Override
    public PaginativeVO<Activity> pageQuery(Map<String, Object> map) {
        int total = ad.queryTotal(map);
        List<Activity> alist = ad.queryList(map);
        PaginativeVO pn = new PaginativeVO();
        pn.setDataList(alist);
        pn.setPageTotal(String.valueOf(total));

        return pn;
    }


    @Override
    public List<User> lookfor() {
        List<User> ulist = ad.lookfor();
        return ulist;
    }
}

package com.cth.crm.workbench.service.impl;

import com.cth.crm.utils.SqlSessionUtil;
import com.cth.crm.workbench.dao.ClueDao;
import com.cth.crm.workbench.service.ClueService;

public class ClueServiceImpl implements ClueService {
    private ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
}

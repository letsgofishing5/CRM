package com.cth.crm.settings.service.impl;

import com.cth.crm.settings.dao.DicTypeDao;
import com.cth.crm.settings.dao.DicValueDao;
import com.cth.crm.settings.domain.DicType;
import com.cth.crm.settings.domain.DicValue;
import com.cth.crm.settings.service.DicService;
import com.cth.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DicServiceImpl implements DicService {
    private DicTypeDao dicTypeDao = SqlSessionUtil.getSqlSession().getMapper(DicTypeDao.class);
    private DicValueDao dicValueDao = SqlSessionUtil.getSqlSession().getMapper(DicValueDao.class);

    @Override
    public Map<String, List<DicValue>> getAll() {
        List<DicType> dicTypeList = dicTypeDao.getAll();
        Map<String, List<DicValue>> map = new HashMap<>();
        for (DicType dicType : dicTypeList) {
            List<DicValue> dicValueList = dicValueDao.getDicValues(dicType);
            map.put(dicType.getCode(), dicValueList);
        }
        return map;
    }
}

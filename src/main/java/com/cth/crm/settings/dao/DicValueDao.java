package com.cth.crm.settings.dao;

import com.cth.crm.settings.domain.DicType;
import com.cth.crm.settings.domain.DicValue;

import java.util.List;
import java.util.Map;

public interface DicValueDao {

    List<DicValue> getDicValues(DicType dicType);
}

package com.cth.crm.workbench.dao;

import com.cth.crm.workbench.domain.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationDao {


    int unbound(String id);

    int bound(ClueActivityRelation car);

    List<ClueActivityRelation> getActivityById(String clueId);

    int deleteById(String id);
}

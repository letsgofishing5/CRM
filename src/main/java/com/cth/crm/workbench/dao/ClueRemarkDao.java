package com.cth.crm.workbench.dao;

import com.cth.crm.workbench.domain.ClueRemark;

import java.util.List;

public interface ClueRemarkDao {

    List<ClueRemark> getListClueRemark(String clueId);

    int deleteById(String id);
}

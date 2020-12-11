package com.cth.crm.workbench.dao;

import com.cth.crm.workbench.domain.TranHistory;

import java.util.List;

public interface TranHistoryDao {

    int createTranHistory(TranHistory tranHistory);

    List<TranHistory> historyList(String tranId);
}

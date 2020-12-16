package com.cth.crm.workbench.service;

import com.cth.crm.vo.PaginativeVO;
import com.cth.crm.workbench.domain.Tran;
import com.cth.crm.workbench.domain.TranHistory;

import java.util.List;
import java.util.Map;

public interface TranService {
    boolean saveTran(Tran t, String customerName);

    PaginativeVO getTranPageList(Map<String, Object> tran);

    Tran detail(String id);

    List<TranHistory> historyList(String tranId);

    boolean changeStage(Tran t);

    Map<String, Object> getECharts();
}

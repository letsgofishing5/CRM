package com.cth.crm.workbench.dao;

import com.cth.crm.workbench.domain.Tran;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TranDao {

    int save(Tran t);

    int pageTotal(Map<String, Object> tran);

    List<Tran> dataList(Map<String, Object> tran);

    Tran detail(String id);

    int changeStage(Tran t);

    int getTotal();

    List<Map<String, Object>> getECharts();
}

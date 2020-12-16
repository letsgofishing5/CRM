package com.cth.crm.workbench.service.impl;

import com.cth.crm.utils.DateTimeUtil;
import com.cth.crm.utils.SqlSessionUtil;
import com.cth.crm.utils.UUIDUtil;
import com.cth.crm.vo.PaginativeVO;
import com.cth.crm.workbench.dao.CustomerDao;
import com.cth.crm.workbench.dao.TranDao;
import com.cth.crm.workbench.dao.TranHistoryDao;
import com.cth.crm.workbench.domain.Customer;
import com.cth.crm.workbench.domain.Tran;
import com.cth.crm.workbench.domain.TranHistory;
import com.cth.crm.workbench.service.TranService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TranServiceImpl implements TranService {
    private TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    private TranHistoryDao tranHistoryDao = SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);
    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);

    @Override
    public List<TranHistory> historyList(String tranId) {
        List<TranHistory> tranHistories = tranHistoryDao.historyList(tranId);
        return tranHistories;
    }

    @Override
    public boolean changeStage(Tran t) {
        boolean flag = true;

        int count = tranDao.changeStage(t);
        if (count==0)
        {
            flag = false;

        }


        TranHistory th = new TranHistory();
        th.setId(UUIDUtil.getUUID());
        th.setCreateBy(t.getEditBy());
        th.setCreateTime(DateTimeUtil.getSysTime());
        th.setExpectedDate(t.getExpectedDate());
        th.setMoney(t.getMoney());
        th.setStage(t.getStage());
        th.setTranId(t.getId());
        int count2 = tranHistoryDao.createTranHistory(th);
        if (count2!=1)
        {
            flag = false;
        }
        return flag;


    }

    @Override
    public Map<String, Object> getECharts() {
        //取得dataTotal
        int count = tranDao.getTotal();
        //取得dataList
        List<Map<String,Object>> dataList = tranDao.getECharts();
        Map<String, Object> map = new HashMap<>();
        map.put("total", count);
        map.put("dataList", dataList);
        return map;

    }

    @Override
    public Tran detail(String id) {
        Tran tran = tranDao.detail(id);
        return tran;
    }

    @Override
    public PaginativeVO getTranPageList(Map<String, Object> map) {
        int count = tranDao.pageTotal(map);
        List<Tran> tList = tranDao.dataList(map);
        PaginativeVO p = new PaginativeVO();
        p.setDataList(tList);
        p.setPageTotal(String.valueOf(count));
        return p;
    }

    @Override
    public boolean saveTran(Tran t, String customerName) {
        boolean flag = true;
        Customer customer = customerDao.getByName(customerName);
        if (customer==null)
        {
            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setName(customerName);
            customer.setCreateBy(t.getCreateBy());
            customer.setCreateTime(DateTimeUtil.getSysTime());
            customer.setContactSummary(t.getContactSummary());
            customer.setNextContactTime(t.getNextContactTime());
            customer.setOwner(t.getOwner());
            int count = customerDao.save(customer);
            if (count!=1)
            {
                flag=false;
            }
        }
        t.setCustomerId(customer.getId());
        int count2 = tranDao.save(t);
        if (count2!=1)
        {
            flag = false;
        }
//        添加交易历史
        TranHistory tranHistory = new TranHistory();
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setTranId(t.getId());
        tranHistory.setStage(t.getStage());
        tranHistory.setMoney(t.getMoney());
        tranHistory.setExpectedDate(t.getExpectedDate());
        tranHistory.setCreateTime(DateTimeUtil.getSysTime());
        tranHistory.setCreateBy(t.getCreateBy());
        int count3  = tranHistoryDao.createTranHistory(tranHistory);
        if (count3!=1)
        {
            flag=false;
        }
        return flag;
    }
}

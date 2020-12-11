package com.cth.crm.workbench.web.controller;

import com.cth.crm.settings.domain.User;
import com.cth.crm.settings.service.UserService;
import com.cth.crm.settings.service.impl.UserServiceImpl;
import com.cth.crm.utils.DateTimeUtil;
import com.cth.crm.utils.PrintJson;
import com.cth.crm.utils.ServiceFactory;
import com.cth.crm.utils.UUIDUtil;
import com.cth.crm.vo.PaginativeVO;
import com.cth.crm.workbench.dao.TranDao;
import com.cth.crm.workbench.domain.Customer;
import com.cth.crm.workbench.domain.Tran;
import com.cth.crm.workbench.domain.TranHistory;
import com.cth.crm.workbench.service.CustomerService;
import com.cth.crm.workbench.service.TranService;
import com.cth.crm.workbench.service.impl.CustomerServiceImpl;
import com.cth.crm.workbench.service.impl.TranServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TranController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        if ("/workbench/transaction/add.do".equals(path))
        {
            add(request,response);
        }else if ("/workbench/transaction/getCustomerName.do".equals(path))
        {
            getCustomerName(request, response);
        }else if ("/workbench/transaction/saveTran.do".equals(path))
        {
            saveTran(request,response);
        }else if ("/workbench/transaction/getTranListPage.do".equals(path))
        {
            getTranPageList(request, response);
        }else if ("/workbench/transaction/detail.do".equals(path))
        {
            detail(request, response);
        }else if ("/workbench/transaction/historyList.do".equals(path))
        {
            historyList(request, response);
        }
    }

    private void historyList(HttpServletRequest request, HttpServletResponse response) {
        String tranId = request.getParameter("tranId");
        System.out.println("tranId="+tranId);
        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());
        List<TranHistory> tranHistoryList = ts.historyList(tranId);
        for (TranHistory tranHistory : tranHistoryList) {
            Map<String, String> map = (Map<String, String>) this.getServletContext().getAttribute("stageMap");
            tranHistory.setPossibility(map.get(tranHistory.getStage()));
        }
        PrintJson.printJsonObj(response,tranHistoryList);

    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String id = request.getParameter("id");
        System.out.println("==========================id="+id);
        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());
        Tran tran = ts.detail(id);
        Map<String, String> map = (Map<String, String>) this.getServletContext().getAttribute("stageMap");
        tran.setPossibility(map.get(tran.getStage()));
        request.setAttribute("tran",tran);
        request.getRequestDispatcher("/workbench/transaction/detail.jsp").forward(request,response);

    }

    private void getTranPageList(HttpServletRequest request, HttpServletResponse response) {
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String customerName = request.getParameter("customerName");
        String stage = request.getParameter("stage");
        String type = request.getParameter("type");
        String source = request.getParameter("source");
        String contactName = request.getParameter("contactName");
        String pageNoStr = request.getParameter("pageNo");
        Integer pageNo = Integer.valueOf(pageNoStr);
        String pageSizeStr = request.getParameter("pageSize");
        Integer pageSize = Integer.valueOf(pageSizeStr);
        int pageSkip = (pageNo - 1) * pageSize;
        Map<String, Object> map = new HashMap<>();
        map.put("owner",owner);
        map.put("name",name);
        map.put("customerId",customerName);
        map.put("stage",stage);
        map.put("type",type);
        map.put("source",source);
        map.put("contactId",contactName);
        map.put("pageSkip",pageSkip);
        map.put("pageSize",pageSize);
        TranService tranService = (TranService) ServiceFactory.getService(new TranServiceImpl());
        PaginativeVO p = tranService.getTranPageList(map);
        PrintJson.printJsonObj(response,p);

    }

    private void saveTran(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = UUIDUtil.getUUID();
        String owner = request.getParameter("owner");
        String money = request.getParameter("money");
        String name = request.getParameter("name");
        String expectedDate = request.getParameter("expectedDate");
        String customerName = request.getParameter("customerName");
        String stage = request.getParameter("stage");
        String type = request.getParameter("type");
        String source = request.getParameter("source");
        String activityId = request.getParameter("activityId");
        String contactsId = request.getParameter("contactsId");
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        String createTime = DateTimeUtil.getSysTime();
        String description = request.getParameter("description");
        String contactSummary = request.getParameter("contactSummary");
        String nextContactTime = request.getParameter("nextContactTime");

        Tran t = new Tran();
        t.setId(id);
        t.setOwner(owner);
        t.setMoney(money);
        t.setName(name);
        t.setExpectedDate(expectedDate);
        t.setStage(stage);
        t.setType(type);
        t.setSource(source);
        t.setActivityId(activityId);
        t.setContactsId(contactsId);
        t.setCreateBy(createBy);
        t.setCreateTime(createTime);
        t.setDescription(description);
        t.setContactSummary(contactSummary);
        t.setNextContactTime(nextContactTime);
        TranService tranService = (TranService) ServiceFactory.getService(new TranServiceImpl());
        boolean flag = tranService.saveTran(t,customerName);
        if (flag)
        {
            response.sendRedirect(request.getContextPath()+"/workbench/transaction/index.jsp");
        }

    }

    private void getCustomerName(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        CustomerService cs= (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());
        List<String> CusName = cs.getCustomerName(name);
        PrintJson.printJsonObj(response,CusName);

    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> uList = us.getUserList();
        request.setAttribute("uList",uList);
        request.getRequestDispatcher("/workbench/transaction/save.jsp").forward(request,response);
    }
}

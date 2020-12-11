package com.cth.crm.workbench.web.controller;

import com.cth.crm.settings.domain.User;
import com.cth.crm.settings.service.UserService;
import com.cth.crm.settings.service.impl.UserServiceImpl;
import com.cth.crm.utils.DateTimeUtil;
import com.cth.crm.utils.PrintJson;
import com.cth.crm.utils.ServiceFactory;
import com.cth.crm.utils.UUIDUtil;
import com.cth.crm.vo.PaginativeVO;
import com.cth.crm.workbench.dao.ActivityDao;
import com.cth.crm.workbench.domain.Activity;
import com.cth.crm.workbench.domain.Clue;
import com.cth.crm.workbench.domain.ClueActivityRelation;
import com.cth.crm.workbench.domain.Tran;
import com.cth.crm.workbench.service.ActivityService;
import com.cth.crm.workbench.service.ClueService;
import com.cth.crm.workbench.service.impl.ActivityServiceImpl;
import com.cth.crm.workbench.service.impl.ClueServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClueController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("-------------------进入线索控制器------------------");
        String path = request.getServletPath();
        if ("/workbench/clue/getUserList.do".equals(path))
        {
            getUserList(request, response);
        }else if ("/workbench/clue/saveClue.do".equals(path))
        {
            saveClue(request,response);
        }else if ("/workbench/clue/detail.do".equals(path))
        {
            getUserInfo(request,response);
        }else if ("/workbench/clue/getActivityById.do".equals(path))
        {
            getActivityById(request, response);
        }else if ("/workbench/clue/unbound.do".equals(path)){
            unbound(request, response);
        } else if ("/workbench/clue/getActivityAndNotClueId.do".equals(path)) {
            getActivityAndNotClueId(request, response);
        }else if ("/workbench/clue/bound.do".equals(path))
        {
            bound(request, response);
        }else if ("/workbench/clue/getActivityByName.do".equals(path))
        {
            getActivityByName(request, response);
        }else if("/workbench/clue/convert.do".equals(path))
        {
            convert(request, response);
        }else if ("/workbench/clue/getClueInfo.do".equals(path))
        {
            getClueInfo(request, response);
        }
    }

    private void getClueInfo(HttpServletRequest request, HttpServletResponse response) {
        String pageNoStr = request.getParameter("pageNo");
        Integer pageNo = Integer.valueOf(pageNoStr);
        String pageSiezStr = request.getParameter("pageSize");
        Integer pageSize = Integer.valueOf(pageSiezStr);
        Integer pageSkip=(pageNo-1)*pageSize;
        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        PaginativeVO<Clue> p = cs.getClueInfo(pageSkip, pageSize);
         PrintJson.printJsonObj(response,p);

    }

    private void convert(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String clueId = request.getParameter("clueId");
//        标记，判断是否创建了交易
        String flag = request.getParameter("flag");
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        Tran t = null;
        if ("a".equals(flag)) {
            t = new Tran();
            String money = request.getParameter("money");
            String name = request.getParameter("name");
            String exceptedDate = request.getParameter("exceptedDate");
            String stage = request.getParameter("stage");
            String activityId = request.getParameter("activityId");
            String uuid = UUIDUtil.getUUID();
            String createTime = DateTimeUtil.getSysTime();
            t.setId(uuid);
            t.setActivityId(activityId);
            t.setMoney(money);
            t.setName(name);
            t.setExpectedDate(exceptedDate);
            t.setCreateBy(createBy);
            t.setCreateTime(createTime);
            t.setStage(stage);
        }
        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean flag1 = cs.convert(clueId,t,createBy);
        response.sendRedirect(request.getContextPath() + "/workbench/clue/index.jsp");
    }

    private void getActivityByName(HttpServletRequest request, HttpServletResponse response) {
        String aname = request.getParameter("aname");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<Activity> a = as.getActivityByName(aname);

        PrintJson.printJsonObj(response,a);

    }

    private void bound(HttpServletRequest request, HttpServletResponse response) {
        String cid = request.getParameter("cid");
        String[] ids = request.getParameterValues("aid");

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean flag = cs.bound(cid,ids);
        PrintJson.printJsonFlag(response,flag);
    }

    private void getActivityAndNotClueId(HttpServletRequest request, HttpServletResponse response) {
        String clueId = request.getParameter("clueId");
        String search = request.getParameter("search");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Map<String, Object> map = new HashMap<>();
        map.put("clueId", clueId);
        map.put("search", search);
        List<Activity> alist = as.getActivityAndNotClueId(map);
        map.clear();
        map.put("alist", alist);
        map.put("success", true);
        PrintJson.printJsonObj(response,map);

    }

    private void unbound(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean flag = clueService.unbound(id);
        PrintJson.printJsonFlag(response,flag);

    }

    private void getActivityById(HttpServletRequest request, HttpServletResponse response) {
        String clueId = request.getParameter("clueId");
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<Activity> alist = activityService.getActivityById(clueId);
        PrintJson.printJsonObj(response,alist);
    }

    private void getUserInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        Clue clue = clueService.getUserInfo(id);
        request.setAttribute("clue",clue);
        request.getRequestDispatcher("/workbench/clue/detail.jsp").forward(request,response);

    }

    private void saveClue(HttpServletRequest request, HttpServletResponse response) {
        String id = UUIDUtil.getUUID();
        String fullname = request.getParameter("fullname");
        String appellation = request.getParameter("appellation");
        String owner = request.getParameter("owner");
        String company = request.getParameter("company");
        String job = request.getParameter("job");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String website = request.getParameter("website");
        String mphone = request.getParameter("mphone");
        String state = request.getParameter("state");
        String source = request.getParameter("source");
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        String createTime = DateTimeUtil.getSysTime();
        String description = request.getParameter("description");
        String contactSummary = request.getParameter("contactSummary");
        String nextContactTime = request.getParameter("nextContactTime");
        String address = request.getParameter("address");

        Clue clue = new Clue();
        clue.setId(id);
        clue.setFullname(fullname);
        clue.setAppellation(appellation);
        clue.setOwner(owner);
        clue.setCompany(company);
        clue.setJob(job);
        clue.setEmail(email);
        clue.setPhone(phone);
        clue.setWebsite(website);
        clue.setPhone(mphone);
        clue.setMphone(mphone);
        clue.setState(state);
        clue.setSource(source);
        clue.setCreateBy(createBy);
        clue.setCreateTime(createTime);
        clue.setDescription(description);
        clue.setContactSummary(contactSummary);
        clue.setNextContactTime(nextContactTime);
        clue.setAddress(address);

        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean ok = clueService.saveClue(clue);
        PrintJson.printJsonFlag(response,ok);

    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> ulist = userService.getUserList();
        PrintJson.printJsonObj(response,ulist);

    }
}

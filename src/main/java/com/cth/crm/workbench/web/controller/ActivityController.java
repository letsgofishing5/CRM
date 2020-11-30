package com.cth.crm.workbench.web.controller;

import com.cth.crm.exception.LoginException;
import com.cth.crm.settings.dao.UserDao;
import com.cth.crm.settings.domain.User;
import com.cth.crm.settings.service.UserService;
import com.cth.crm.settings.service.UserServiceImpl;
import com.cth.crm.utils.*;
import com.cth.crm.workbench.domain.Activity;
import com.cth.crm.workbench.service.ActivityService;
import com.cth.crm.workbench.service.ActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        String path = request.getServletPath();
        if ("/workbench/user/lookfor.do".equals(path))
        {
            lookfor(request, response);
        }else if("/workbench/user/save.do".equals(path)){
            saveActivvity(request,response);
        }
    }

    private void saveActivvity(HttpServletRequest request, HttpServletResponse response) {
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        String owner=request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description= request.getParameter("description");
        String uuid = UUIDUtil.getUUID();
        String sysTime = DateTimeUtil.getSysTime();
        User user = (User) request.getSession().getAttribute("user");

        Activity at = new Activity();

        at.setId(uuid);
        at.setOwner(owner);
        at.setName(name);
        at.setStartDate(startDate);
        at.setEndDate(endDate);
        at.setCost(cost);
        at.setDescription(description);
        at.setCreateBy(user.getName());
        at.setCreateTime(sysTime);
        int i = as.saveActivity(at);
        if (i==0)
        {
            PrintJson.printJsonFlag(response,false);
        }else {
            PrintJson.printJsonFlag(response,true);
        }
    }

    private void lookfor(HttpServletRequest request, HttpServletResponse response) {
        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> ulist = us.lookfor();
        PrintJson.printJsonObj(response,ulist);
    }


}

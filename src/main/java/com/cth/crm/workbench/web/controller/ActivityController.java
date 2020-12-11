package com.cth.crm.workbench.web.controller;

import com.cth.crm.settings.domain.User;
import com.cth.crm.settings.service.UserService;
import com.cth.crm.settings.service.impl.UserServiceImpl;
import com.cth.crm.utils.*;
import com.cth.crm.vo.PaginativeVO;
import com.cth.crm.workbench.domain.Activity;
import com.cth.crm.workbench.domain.ActivityRemark;
import com.cth.crm.workbench.service.ActivityRemarkService;
import com.cth.crm.workbench.service.impl.ActivityRemarkServiceImpl;
import com.cth.crm.workbench.service.ActivityService;
import com.cth.crm.workbench.service.impl.ActivityServiceImpl;

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
        if ("/workbench/Activity/lookfor.do".equals(path))
        {
            lookfor(request, response);
        }else if("/workbench/Activity/save.do".equals(path))
        {
            saveActivvity(request,response);
        }else if ("/workbench/Activity/queryList.do".equals(path))
        {
            queryList(request,response);
        }else if ("/workbench/Activity/deleteById.do".equals(path))
        {
            deleteById(request,response);
        }else if("/workbench/Activity/selectById.do".equals(path))
        {
            selectById(request,response);
        }else if("/workbench/Activity/updateById.do".equals(path))
        {
            editById(request,response);
        }else if("/workbench/activity/detail.do".equals(path))
        {
            getActivity(request,response);
        }else if("/workbench/activity/activityRemark.do".equals(path))
        {
            getActivityRemark(request,response);
        }else if ("/workbench/activity/deleteRemark.do".equals(path))
        {
            deleteRemarkById(request,response);
        }else if ("/workbench/activity/editRemark.do".equals(path))
        {
            editRemarkById(request,response);
        }else if ("/workbench/activity/saveNoteContent.do".equals(path))
        {
            saveNoteContent(request,response);
        }
    }

    private void saveNoteContent(HttpServletRequest request, HttpServletResponse response) {
        String activityId = request.getParameter("activityId");
        String remark = request.getParameter("remark");
        String uuid = UUIDUtil.getUUID();
        String createTime = DateTimeUtil.getSysTime();
        User user = (User) request.getSession(false).getAttribute("user");
        String editFlag="1";
        ActivityRemark activityRemark = new ActivityRemark();
        activityRemark.setId(uuid);
        activityRemark.setNoteContent(remark);
        activityRemark.setCreateTime(createTime);
        activityRemark.setCreateBy(user.getName());
        activityRemark.setEditFlag(editFlag);
        activityRemark.setActivityId(activityId);
        ActivityRemarkService ars = (ActivityRemarkService) ServiceFactory.getService(new ActivityRemarkServiceImpl());
        boolean flag = ars.saveNoteContent(activityRemark);
        Map<String, Object> map = new HashMap<>();
        map.put("success", flag);
        map.put("ar", activityRemark);
        PrintJson.printJsonObj(response,map);


    }

    private void editRemarkById(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String noteContent = request.getParameter("noteContent");
        String editTime = DateTimeUtil.getSysTime();
        System.out.println("信息修改时间是：=========="+editTime);
        User user = (User) request.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<>();
        ActivityRemark ar = new ActivityRemark();
        ar.setId(id);
        ar.setNoteContent(noteContent);
        ar.setEditTime(editTime);
        ar.setEditBy(user.getName());
        ActivityRemarkService ars = (ActivityRemarkService) ServiceFactory.getService(new ActivityRemarkServiceImpl());
        boolean flag = ars.editRemarkById(ar);
        map.put("a", ar);
        map.put("success", flag);
        PrintJson.printJsonObj(response,map);
    }

    private void deleteRemarkById(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        ActivityRemarkService ars = (ActivityRemarkService) ServiceFactory.getService(new ActivityRemarkServiceImpl());
        Boolean flag = ars.deleteRemarkById(id);
        PrintJson.printJsonFlag(response,flag);

    }

    private void getActivityRemark(HttpServletRequest request, HttpServletResponse response) {
        String activityId = request.getParameter("activityId");
        ActivityRemarkService ars = (ActivityRemarkService) ServiceFactory.getService(new ActivityRemarkServiceImpl());
        List<ActivityRemark> arlist = (List<ActivityRemark>) ars.getActivityRemarkByActivity(activityId);
        System.out.println(arlist);
        PrintJson.printJsonObj(response,arlist);
    }

    private void getActivity(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Activity a = as.detailById(id);
        request.setAttribute("a",a);
        try {
            request.getRequestDispatcher("/workbench/activity/detail.jsp").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void editById(HttpServletRequest request, HttpServletResponse response) {
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String owner = request.getParameter("owner");
        String endDate = request.getParameter("endDate");
        String startDate = request.getParameter("startDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");
        String sysTime = DateTimeUtil.getSysTime();
        User user = (User) request.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<>();
        map.put("sysTime", sysTime);
        map.put("user", user.getName());
        map.put("id", id);
        map.put("name", name);
        map.put("owner", owner);
        map.put("endDate", endDate);
        map.put("startDate", startDate);
        map.put("cost", cost);
        map.put("description", description);
        boolean flag = as.editById(map);
        PrintJson.printJsonFlag(response,flag);

    }

    private void selectById(HttpServletRequest request, HttpServletResponse response) {
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        String id = request.getParameter("id");
        Map<String,Object> map = as.selectById(id);
        PrintJson.printJsonObj(response,map);
    }

    private void deleteById(HttpServletRequest request, HttpServletResponse response) {
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        String[] split = request.getParameterValues("id");

        boolean flag = as.deleteById(split);
        PrintJson.printJsonFlag(response,flag);
    }

    private void queryList(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String owner = request.getParameter("owner");
        String endDate = request.getParameter("endDate");
        String startDate = request.getParameter("startDate");
        String pageNoStr = request.getParameter("pageNo");
        Integer pageNo = Integer.valueOf(pageNoStr);
        String pageSizeStr = request.getParameter("pageSize");
        Integer pageSize = Integer.valueOf(pageSizeStr);
        Integer skipCount = (pageNo-1) * pageSize;
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("owner", owner);
        map.put("endDate", endDate);
        map.put("startDate", startDate);
        map.put("skipCount", skipCount);
        map.put("pageSize", pageSize);
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        PaginativeVO<Activity> vo = as.pageQuery(map);
        PrintJson.printJsonObj(response,vo);

    }

    private void saveActivvity(HttpServletRequest request, HttpServletResponse response) {
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        String owner=request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description= request.getParameter("description");
        if ("".equals(owner) || owner==null || "".equals(name) || name==null
                || "".equals(startDate) || startDate==null || "".equals(endDate) || endDate==null
                || "".equals(cost) || cost==null|| "".equals(description) || description==null
        ){
            PrintJson.printJsonFlag(response,false);


        }else{
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

    }

    private void lookfor(HttpServletRequest request, HttpServletResponse response) {
        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> ulist = us.lookfor();
        PrintJson.printJsonObj(response,ulist);
    }


}

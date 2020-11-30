package com.cth.crm.workbench.web.controller;

import com.cth.crm.exception.LoginException;
import com.cth.crm.settings.domain.User;
import com.cth.crm.settings.service.UserService;
import com.cth.crm.settings.service.UserServiceImpl;
import com.cth.crm.utils.MD5Util;
import com.cth.crm.utils.PrintJson;
import com.cth.crm.utils.ServiceFactory;
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
    private ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        String path = request.getServletPath();
        if ("/workbench/user/lookfor.do".equals(path))
        {
            lookfor(request, response);
        }else if("/settings/user/xxx.do".equals(path)){

        }
    }

    private void lookfor(HttpServletRequest request, HttpServletResponse response) {
        List<User> ulist = as.lookfor();
        PrintJson.printJsonObj(response,ulist);
    }


}

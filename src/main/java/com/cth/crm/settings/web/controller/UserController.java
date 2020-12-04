package com.cth.crm.settings.web.controller;

import com.cth.crm.exception.LoginException;
import com.cth.crm.settings.domain.User;
import com.cth.crm.settings.service.UserService;
import com.cth.crm.settings.service.impl.UserServiceImpl;
import com.cth.crm.utils.MD5Util;
import com.cth.crm.utils.PrintJson;
import com.cth.crm.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        String path = request.getServletPath();
        if ("/settings/user/login.do".equals(path))
        {
            System.out.println("进入用户登录controller页面");
            login(request, response);
        }else if("/settings/user/xxx.do".equals(path)){

        }
    }

    private void login(HttpServletRequest request,HttpServletResponse response) {
        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());
        String name = request.getParameter("name");
        String pwd = request.getParameter("pwd");
        pwd = MD5Util.getMD5(pwd);
        String ip=request.getRemoteAddr();
        try {
            User user = us.login(name, pwd, ip);
            request.getSession().setAttribute("user",user);
            PrintJson.printJsonFlag(response,true);
        } catch (LoginException e) {
            e.printStackTrace();
            String msg = e.getMessage();
            Map<String, Object> map = new HashMap<>();
            map.put("msg",msg);
            map.put("success",false);
            PrintJson.printJsonObj(response,map);
        }
    }
}

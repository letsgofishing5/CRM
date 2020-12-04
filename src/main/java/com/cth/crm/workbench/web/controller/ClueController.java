package com.cth.crm.workbench.web.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ClueController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("-------------------进入线索控制器------------------");
        String path = request.getServletPath();
        if ("".equals(path))
        {

        }else if ("".equals(path))
        {
            
        }
    }
}

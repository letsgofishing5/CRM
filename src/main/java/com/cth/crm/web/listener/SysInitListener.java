package com.cth.crm.web.listener;

import com.cth.crm.settings.domain.DicType;
import com.cth.crm.settings.domain.DicValue;
import com.cth.crm.settings.service.DicService;
import com.cth.crm.settings.service.impl.DicServiceImpl;
import com.cth.crm.utils.ServiceFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;

public class SysInitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("上下文对象执行了");
        ServletContext application = servletContextEvent.getServletContext();
        DicService dicService = (DicService) ServiceFactory.getService(new DicServiceImpl());
        Map<String, List<DicValue>> dicValueList = dicService.getAll();
        Set<String> strings = dicValueList.keySet();
        for (String string : strings) {
            application.setAttribute(string,dicValueList.get(string));
        }

//        加载交易阶段和可能性关系，
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Stage2Possibility");
        Enumeration<String> keys = resourceBundle.getKeys();
        Map<String, String> stageMap = new HashMap<>();

        while (keys.hasMoreElements())
        {
            String s = keys.nextElement();
            String string = resourceBundle.getString(s);
            stageMap.put(s, string);
        }
        application.setAttribute("stageMap",stageMap);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}

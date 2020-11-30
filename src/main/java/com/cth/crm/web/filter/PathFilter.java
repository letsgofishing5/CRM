package com.cth.crm.web.filter;

import com.cth.crm.settings.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class PathFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        System.out.println("-----------进入到登录拦截器---------");
        HttpServletRequest request= (HttpServletRequest) req;
        HttpServletResponse response= (HttpServletResponse) resp;
        String path = request.getServletPath();
        if ("/settings/user/login.do".equals(path) || "/login.jsp".equals(path))
        {
            chain.doFilter(req,resp);
        }else{
            HttpSession session =  request.getSession(false);
            if (session != null) {
                User user = (User) session.getAttribute("user");
                if (user!=null)
                {
                    chain.doFilter(req,resp);
                }else{
                    response.sendRedirect(request.getContextPath()+"/login.jsp");
                }

            }else{
                response.sendRedirect(request.getContextPath()+"/login.jsp");
            }
        }


    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }


}

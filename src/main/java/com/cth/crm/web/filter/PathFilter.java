package com.cth.crm.web.filter;

import com.cth.crm.settings.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class PathFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String servletPath = request.getServletPath();
        System.out.println(servletPath+"-------------------------");

        if("/login.jsp".equals(servletPath) || "/settings/user/login.do".equals(servletPath)){
            chain.doFilter(req, resp);
        }else{
            HttpSession session = request.getSession(false);
            System.out.println("session:"+session);
            if (session!=null)
            {
                chain.doFilter(req, resp);
            }else{
                response.sendRedirect(request.getContextPath()+"/login.jsp");
            }
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}

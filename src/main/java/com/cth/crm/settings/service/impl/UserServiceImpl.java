package com.cth.crm.settings.service.impl;

import com.cth.crm.exception.LoginException;
import com.cth.crm.settings.dao.UserDao;
import com.cth.crm.settings.domain.User;
import com.cth.crm.settings.service.UserService;
import com.cth.crm.utils.DateTimeUtil;
import com.cth.crm.utils.SqlSessionUtil;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDao = (UserDao) SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public User login(String name, String pwd, String ip) throws LoginException {
        System.out.println("-----------进入dao层------------");
        User user = new User();
        user.setLoginAct(name);
        user.setLoginPwd(pwd);
        User u = userDao.login(user);
        String split = u.getAllowIps();
        if (u==null)
        {
            throw new LoginException("用户不存在");
        }
        else if (u.getLockState().equals("0"))
        {
            throw new LoginException("该用户被锁定");
        }else if(u.getExpireTime().compareTo(DateTimeUtil.getSysTime())<0)
        {
            throw new LoginException("账户已失效");
        }else if (!split.contains(ip))
        {
            throw new LoginException("IP地址不正确");
        }
        return u;
    }

    @Override
    public List<User> lookfor() {
        List<User> ulist = userDao.lookfor();
        return ulist;
    }

    @Override
    public List<User> getUserList() {
        List<User> ulist = userDao.lookfor();
        return ulist;
    }
}

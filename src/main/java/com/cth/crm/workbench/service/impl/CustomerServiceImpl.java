package com.cth.crm.workbench.service.impl;

import com.cth.crm.utils.SqlSessionUtil;
import com.cth.crm.workbench.dao.CustomerDao;
import com.cth.crm.workbench.domain.Customer;
import com.cth.crm.workbench.service.CustomerService;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);

    @Override
    public List<String> getCustomerName(String name) {
        List<String> customerList = customerDao.getCustomerName(name);
        return customerList;
    }
}

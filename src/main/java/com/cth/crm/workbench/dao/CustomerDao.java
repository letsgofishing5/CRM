package com.cth.crm.workbench.dao;

import com.cth.crm.workbench.domain.Customer;

public interface CustomerDao {


    Customer getByName(String company);

    int save(Customer customer);
}

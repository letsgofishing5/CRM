package com.cth.crm.workbench.service.impl;

import com.cth.crm.utils.DateTimeUtil;
import com.cth.crm.utils.SqlSessionUtil;
import com.cth.crm.utils.UUIDUtil;
import com.cth.crm.vo.PaginativeVO;
import com.cth.crm.workbench.dao.*;
import com.cth.crm.workbench.domain.*;
import com.cth.crm.workbench.service.ClueService;

import java.util.List;

public class ClueServiceImpl implements ClueService {
//   线索表
    private ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
    private ClueActivityRelationDao clueActivityRelationDao = SqlSessionUtil.getSqlSession().getMapper(ClueActivityRelationDao.class);
    private ClueRemarkDao clueRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ClueRemarkDao.class);
//   客户表
    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);
    private CustomerRemarkDao customerRemarkDao = SqlSessionUtil.getSqlSession().getMapper(CustomerRemarkDao.class);

    private ContactsDao contactsDao = SqlSessionUtil.getSqlSession().getMapper(ContactsDao.class);
    private ContactsRemarkDao contactsRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ContactsRemarkDao.class);
    private ContactsActivityRelationDao contactsActivityRelationDao = SqlSessionUtil.getSqlSession().getMapper(ContactsActivityRelationDao.class);

    private TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    private TranHistoryDao tranHistoryDao = SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);

    @Override
    public PaginativeVO getClueInfo(int pageSkip, int pageSize) {
        int pageTotal = clueDao.getClueTotal();
        List<Clue> clueList = clueDao.getClueDataList(pageSkip, pageSize);
        PaginativeVO p = new PaginativeVO();
        p.setPageTotal(String.valueOf(pageTotal));
        p.setDataList(clueList);
        return p;
    }

    @Override
    public boolean convert(String clueId, Tran t, String createBy) {
        String createTime = DateTimeUtil.getSysTime();
        boolean flag = true;
//        通过线索id获取线索对象
        Clue clue = clueDao.getClueById(clueId);
//        通过线索对象获取客户信息，当客户不存在时，创建新客户
        String company = clue.getCompany();
        Customer customer = customerDao.getByName(company);
        if (customer==null) {
            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setAddress(clue.getAddress());
            customer.setWebsite(clue.getWebsite());
            customer.setName(clue.getCompany());
            customer.setContactSummary(clue.getContactSummary());
            customer.setOwner(clue.getOwner());
            customer.setPhone(clue.getPhone());
            customer.setDescription(clue.getDescription());
            customer.setCreateTime(clue.getCreateTime());
            customer.setCreateBy(clue.getCreateBy());
            //添加客户
            int count = customerDao.save(customer);
            if (count == 0)
            {
                flag=false;
            }
        }

//        通过线索对象提取联系人信息
        Contacts contacts = new Contacts();
        contacts.setId(UUIDUtil.getUUID());
        contacts.setSource(clue.getSource());
        contacts.setOwner(clue.getOwner());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setMphone(clue.getMphone());
        contacts.setJob(clue.getJob());
        contacts.setFullname(clue.getFullname());
        contacts.setDescription(clue.getDescription());
        contacts.setEmail(clue.getEmail());
        contacts.setAddress(clue.getAddress());
        contacts.setAppellation(clue.getAppellation());
        contacts.setCreateBy(createBy);
        contacts.setCreateTime(createTime);
        contacts.setCustomerId(customer.getId());

//        添加联系人
        int count2 = contactsDao.save(contacts);
        if (count2==0)
        {
            flag = false;
        }
//        线索备注转换到客户备注以及联系人备注
        List<ClueRemark> clueRemarkList = clueRemarkDao.getListClueRemark(clueId);
        for (ClueRemark clueRemark : clueRemarkList) {
//            取出备注信息,添加到客户备注信息表中
            String noteContent = clueRemark.getNoteContent();
            CustomerRemark customerRemark = new CustomerRemark();
            customerRemark.setCreateBy(createBy);
            customerRemark.setCreateTime(createTime);
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setCustomerId(customer.getId());
            customerRemark.setEditFlag("0");
            customerRemark.setNoteContent(noteContent);

            int count3 = customerRemarkDao.save(customerRemark);
            if (count3==0)
            {
                flag=false;
            }
            //取出备注信息,添加到联系人备注信息表中
            ContactsRemark contactsRemark = new ContactsRemark();
            contactsRemark.setCreateBy(createBy);
            contactsRemark.setCreateTime(createTime);
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setContactsId(customer.getId());
            contactsRemark.setEditFlag("0");
            contactsRemark.setNoteContent(noteContent);
            int count4 = contactsRemarkDao.save(contactsRemark);
            if (count4==0)
            {
                flag=false;
            }
        }

//        线索和市场活动的关系转换到联系人和和市场活动
//        查询出该条线索关联的市场活动，查询与市场活动的关联关系列表
        List<ClueActivityRelation> clueActivityRelations = clueActivityRelationDao.getActivityById(clueId);
        for (ClueActivityRelation clueActivityRelation : clueActivityRelations) {
            String activityId = clueActivityRelation.getActivityId();
            ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setContactsId(contacts.getId());
            contactsActivityRelation.setActivityId(activityId);

            int count5 = contactsActivityRelationDao.saveContactsActivity(contactsActivityRelation);
            if (count5==0)
            {
                flag = false;
            }
        }

//        如果有创建交易需求，创建一条交易
        if (t!=null)
        {
            t.setSource(clue.getSource());
            t.setOwner(clue.getOwner());
            t.setDescription(clue.getDescription());
            t.setNextContactTime(clue.getNextContactTime());
            t.setCustomerId(customer.getId());
            t.setContactSummary(clue.getContactSummary());
            t.setContactsId(contacts.getId());
//            添加交易
            int count6 = tranDao.save(t);
            if (count6 != 1) {
                flag = false;
            }
//            创建交易历史
            TranHistory tranHistory = new TranHistory();
            tranHistory.setId(UUIDUtil.getUUID());
            tranHistory.setCreateBy(createBy);
            tranHistory.setCreateTime(createTime);
            tranHistory.setExpectedDate(t.getExpectedDate());
            tranHistory.setMoney(t.getMoney());
            tranHistory.setStage(t.getStage());
            tranHistory.setTranId(t.getId());
            int count7 = tranHistoryDao.createTranHistory(tranHistory);
            if (count7!=1)
            {
                flag = false;
            }

        }
        //删除线索备注
        for (ClueRemark clueRemark : clueRemarkList) {
            int count8 = clueRemarkDao.deleteById(clueRemark.getId());
            if (count8!=1)
            {
                flag = false;
            }
        }
//        删除线索和市场活动关系
        for (ClueActivityRelation clueActivityRelation : clueActivityRelations) {
            int count9 = clueActivityRelationDao.deleteById(clueActivityRelation.getId());
            if (count9!=1)
            {
                flag = false;
            }
        }
//        删除线索
        int count10 = clueDao.delete(clueId);
        if (count10!=1)
        {
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean bound(String cid, String[] ids) {
        boolean flag = true;
        int count=0;
        for (String id : ids) {
            String uuid = UUIDUtil.getUUID();
            ClueActivityRelation car = new ClueActivityRelation();
            car.setId(uuid);
            car.setClueId(cid);
            car.setActivityId(id);
            count += clueActivityRelationDao.bound(car);

        }
        if (count!=ids.length)
        {
            flag=false;
        }
        return flag;
    }

    @Override
    public boolean unbound(String id) {
        boolean flag = true;
        int count = clueActivityRelationDao.unbound(id);
        if (count==0)
        {
            flag=false;
        }
        return flag;
    }

    @Override
    public Clue getUserInfo(String id) {
        Clue clue = clueDao.getUserInfo(id);
        return clue;
    }

    @Override
    public boolean saveClue(Clue clue) {
        boolean flag =true;
        int count = clueDao.saveClue(clue);
        if (count==0)
        {
            flag=false;
        }
        return flag;
    }
}

package com.cth.crm.vo;

import java.util.List;

public class PaginativeVO<T>{
    private String pageTotal;
    private List<T> dataList;

    public String getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(String pageTotal) {
        this.pageTotal = pageTotal;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    @Override
    public String toString() {
        return "PaginativeVO{" +
                "pageTotal='" + pageTotal + '\'' +
                ", dataList=" + dataList +
                '}';
    }
}

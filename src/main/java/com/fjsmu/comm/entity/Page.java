package com.fjsmu.comm.entity;

import com.fjsmu.tools.StringUtils;
import org.jboss.resteasy.spi.HttpRequest;
import org.json.JSONObject;

import javax.ws.rs.core.MultivaluedMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 分页信息
 *
 * Created by zzh on 16/10/21.
 */
public class Page<T> {

    private int pageNo = 1; // 当前页码
    private int pageSize = 20; // 页面大小

    private long totalCount; // 总记录数
    private long totalPage; // 总页数

    private transient List<T> list = new ArrayList<T>();

    private transient String orderBy = ""; // 标准查询有效， 实例： updatedate desc, name
                                           // asc

    public Page() {
    }

    public Page(HttpRequest req) {
        MultivaluedMap<String, String> queryParameters = req.getUri().getQueryParameters();
        if (queryParameters != null) {
            List<String> pageNoList = (List<String>) queryParameters.get("pageNo");
            if (pageNoList != null) {
                setPageNo(Integer.valueOf(pageNoList.get(0)));
            }
            List<String> pageSizeList = (List<String>) queryParameters.get("pageSize");
            if (pageSizeList != null) {
                setPageSize(Integer.valueOf(pageSizeList.get(0)));
            }
            List<String> orderByList = (List<String>) queryParameters.get("orderBy");
            if (orderByList != null) {
                setOrderBy(orderByList.get(0));
            }
        }
    }

    public Page(Map<String, String> mapParams) {
        if (mapParams != null) {
            String pageNoString = mapParams.get("pageNo");
            if (StringUtils.isNotBlank(pageNoString)) {
                setPageNo(Integer.valueOf(pageNoString));
            }
            String pageSizeString = mapParams.get("pageSize");
            if (StringUtils.isNotBlank(pageSizeString)) {
                setPageSize(Integer.valueOf(pageSizeString));
            }
            String orderByString = mapParams.get("orderBy");
            if (StringUtils.isNotBlank(orderByString)) {
                setOrderBy(orderByString);
            }
        }
    }

    /**
     * Page构造函数, 兼容两种传参方式,用哪种传参都可以
     *
     * @param json
     *            json传参
     * @param req
     *            request传参
     */
    public Page(JSONObject json, HttpRequest req) {
        try {
            MultivaluedMap<String, String> queryParameters = req.getUri().getQueryParameters();
            if (queryParameters != null) {
                List<String> pageNoList = (List<String>) queryParameters.get("pageNo");
                if (pageNoList != null) {
                    setPageNo(Integer.valueOf(pageNoList.get(0)));
                }
                List<String> pageSizeList = (List<String>) queryParameters.get("pageSize");
                if (pageSizeList != null) {
                    setPageSize(Integer.valueOf(pageSizeList.get(0)));
                }
                List<String> orderByList = (List<String>) queryParameters.get("orderBy");
                if (orderByList != null) {
                    setOrderBy(orderByList.get(0));
                }
            }
            if (json != null) {
                int pageNo = json.getInt("pageNo");
                if (pageNo > 0) {
                    setPageNo(pageNo);
                }
                int pageSize = json.getInt("pageSize");
                if (pageSize > 0) {
                    setPageSize(pageSize);
                }
                String orderBy = json.getString("orderBy");
                if (StringUtils.isNotEmpty(orderBy)) {
                    setOrderBy(orderBy);
                }
            }
        } catch (Exception e) {
        }

    }

    /**
     * 获取设置总数
     * 
     * @return
     */
    public long getCount() {
        return totalCount;
    }

    /**
     * 设置数据总数
     * 
     * @param count
     */
    public void setCount(long count) {
        this.totalCount = count;
        if (pageSize >= count) {
            pageNo = 1;
        }
    }

    /**
     * 获取当前页码
     * 
     * @return
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * 设置当前页码
     * 
     * @param pageNo
     */
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    /**
     * 获取页面大小
     * 
     * @return
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置页面大小（最大500）
     * 
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = (pageSize <= 0 ? 10 : pageSize) > 100 ? 100 : pageSize;// >
                                                                               // 100
                                                                               // ?
                                                                               // 100
                                                                               // :
                                                                               // pageSize;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    /**
     * 获取本页数据对象列表
     * 
     * @return List<T>
     */
    public List<T> getList() {
        return list;
    }

    /**
     * 设置本页数据对象列表
     * 
     * @param list
     */
    public Page<T> setList(List<T> list) {
        this.list = list;
        return this;
    }

    /**
     * 获取 Hibernate FirstResult
     */
    public int getFirstResult() {
        int firstResult = (getPageNo() - 1) * getPageSize();
        if (firstResult >= getCount()) {
            firstResult = 0;
        }
        return firstResult;
    }

    /**
     * 获取 Hibernate MaxResults
     */
    public int getMaxResults() {
        return getPageSize();
    }

    /**
     * 计算总页数
     */
    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }
}

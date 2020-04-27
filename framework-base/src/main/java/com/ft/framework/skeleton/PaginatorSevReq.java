package com.ft.framework.skeleton;

import java.util.HashMap;
import java.util.Map;

public class PaginatorSevReq extends ServiceRequest {

	private static final long serialVersionUID = 8203516039702012657L;

	/**
	 * 页码
	 */
	private Integer pageIndex = 1;
	/**
	 * 页面大小
	 */
	private Integer pageSize = 10;
	/**
	 * 排序字段
	 */
	private Integer sort;
	/**
	 * 排序方向 (see OrderEnum.java)
	 */
	private Integer order;
	
	/**
	 * 是否包含总数
	 */
	private boolean containsTotalCount = true;

	/**
	 * 查询条件
	 */
	private Map<String, Object> queryConditions = new HashMap<String, Object>();
	
	
    /**
     * 方法描述：设置查询参数
     * @param key
     * @param value
     */
    public void setCondition(String key, Object value) {
        queryConditions.put(key, value);
    }
    

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Map<String, Object> getQueryConditions() {
		return queryConditions;
	}

	public void setQueryConditions(Map<String, Object> queryConditions) {
		this.queryConditions = queryConditions;
	}


    /**
     * @return the containsTotalCount
     */
    public boolean isContainsTotalCount() {
        return containsTotalCount;
    }


    /**
     * @param containsTotalCount the containsTotalCount to set
     */
    public void setContainsTotalCount(boolean containsTotalCount) {
        this.containsTotalCount = containsTotalCount;
    }
	
	
}

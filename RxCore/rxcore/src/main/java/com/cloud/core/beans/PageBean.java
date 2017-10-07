package com.cloud.core.beans;

public class PageBean {

    /**
     * 当前页数
     */
    private int pageNumber;
    /**
     * 总记录数
     */
    private int totalCount;
    /**
     * 总页数
     */
    private int pageCount;
    /**
     * 每页记录数
     */
    private int pageSize;
    /**
     * 是否包含更多数据
     */
    private boolean hasMoreData = false;


    /**
     * @return 获取当前页数
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * 设置当前页数
     *
     * @param pageNumber
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * @return 获取总记录数
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * 设置总记录数
     *
     * @param totalCount
     */
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * @return 获取总页数
     */
    public int getPageCount() {
        return pageCount;
    }

    /**
     * 设置总页数
     *
     * @param pageCount
     */
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    /**
     * @return 获取每页记录数
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置每页记录数
     *
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 获取是否包含更多数据
     */
    public boolean getHasMoreData() {
        return hasMoreData;
    }

    /**
     * 设置是否包含更多数据
     *
     * @param hasMoreData
     */
    public void setHasMoreData(boolean hasMoreData) {
        this.hasMoreData = hasMoreData;
    }
}

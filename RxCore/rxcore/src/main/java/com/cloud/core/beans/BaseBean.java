package com.cloud.core.beans;

import java.io.Serializable;

public class BaseBean implements Serializable {

    /**
     * 返回编码 return code
     */
    private String rcd;

    /**
     * 返回信息 return message
     */
    private String rmg;

    /**
     * 返回code=200成功
     */
    private String code = "";

    /**
     * api返回消息
     */
    private String message = "";

    /**
     * 分页属性
     */
    private PageBean pageBean = null;


    /**
     * @return 获取返回编码
     * return
     * code
     */
    public String getRcd() {
        return rcd;
    }

    /**
     * 设置返回编码
     * return
     * code
     *
     * @param rcd
     */
    public void setRcd(String rcd) {
        this.rcd = rcd;
    }

    /**
     * @return 获取返回信息
     * return
     * message
     */
    public String getRmg() {
        return rmg;
    }

    /**
     * 设置返回信息
     * return
     * message
     *
     * @param rmg
     */
    public void setRmg(String rmg) {
        this.rmg = rmg;
    }

    /**
     * @return 获取分页属性
     */
    public PageBean getPageBean() {
        if (pageBean == null) {
            return new PageBean();
        } else {
            return pageBean;
        }
    }

    /**
     * 设置分页属性
     *
     * @param pageBean
     */
    public void setPageBean(PageBean pageBean) {
        this.pageBean = pageBean;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
        this.rcd = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        this.rmg = message;
    }
}

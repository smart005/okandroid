package com.cloud.basicfun.provider;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/5/6
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class ContentProviderQueryWhereItem {
    private String key = "";
    private String value = "";
    private String begin = "";
    private String end = "";
    private ContentProviderOperatorType operator = ContentProviderOperatorType.equal;
    private boolean isExtrasQuery = false;
    private String extras = "";

    public ContentProviderQueryWhereItem() {

    }

    public ContentProviderQueryWhereItem(String extras, boolean isExtrasQuery) {
        this.extras = extras;
        this.isExtrasQuery = isExtrasQuery;
    }

    public ContentProviderQueryWhereItem(String key, String value, String begin, String end, ContentProviderOperatorType operator) {
        this.key = key;
        this.value = value;
        this.begin = begin;
        this.end = end;
        this.operator = operator;
    }

    public ContentProviderQueryWhereItem(String key, String value, ContentProviderOperatorType operator) {
        this(key, value, "", "", operator);
    }

    public boolean isExtrasQuery() {
        return this.isExtrasQuery;
    }

    public void setExtrasQuery(boolean isExtrasQuery) {
        this.isExtrasQuery = isExtrasQuery;
    }

    /**
     *
     */
    public String getKey() {
        if (key == null) {
            key = "";
        }
        return key;
    }

    /**
     * @param key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     *
     */
    public String getValue() {
        if (value == null) {
            value = "";
        }
        return value;
    }

    /**
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     *
     */
    public ContentProviderOperatorType getOperator() {
        return operator;
    }

    /**
     * @param operator
     */
    public void setOperator(ContentProviderOperatorType operator) {
        this.operator = operator;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getExtras() {
        return extras;
    }

    public void setExtras(String extras) {
        this.extras = extras;
    }
}

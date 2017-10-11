package com.cloud.resources.enums;

/**
 * 
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-11-19 下午2:11:38
 * @Description: Yes、No、Confirm、Cancel四个值需要与DialogButtonEnum对应值匹配,其它值可自定义
 * @Modifier:
 * @ModifyContent:
 * 
 */
public enum MsgBoxClickButtonEnum {

	/**
	 * 是
	 */
	Yes("988836739"),
	/**
	 * 否
	 */
	No("1238183772"),
	/**
	 * 确定
	 */
	Confirm("711756514"),
	/**
	 * 取消
	 */
	Cancel("1598871971"),
	/**
	 * 重新登录
	 */
	ReLogin("1399912050"),
	/**
	 * 取消登录
	 */
	CancelLogin("118385731");

	private String value = "";

	private MsgBoxClickButtonEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}

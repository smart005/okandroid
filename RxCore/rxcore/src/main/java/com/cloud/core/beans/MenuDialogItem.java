package com.cloud.core.beans;

/**
 * 
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-6-19 下午1:46:32
 * @Description: BaseMenuDialog实体项
 * @Modifier:
 * @ModifyContent:
 * 
 */
public class MenuDialogItem {

	/**
	 * 文本颜色
	 */
	private int textColorResid = 0;

	/**
	 * 文本颜色RGB
	 */
	private int textColorRGB = 0;

	/**
	 * 文本名称
	 */
	private String textName = "";

	/**
	 * 命令项
	 */
	private CmdItem cmdItem = null;

	public int getTextColorResid() {
		return textColorResid;
	}

	public void setTextColorResid(int textColorResid) {
		this.textColorResid = textColorResid;
	}

	public int getTextColorRGB() {
		return textColorRGB;
	}

	public void setTextColorRGB(int textColorRGB) {
		this.textColorRGB = textColorRGB;
	}

	public String getTextName() {
		return textName;
	}

	public void setTextName(String textName) {
		this.textName = textName;
	}

	public CmdItem getCmdItem() {
		return cmdItem;
	}

	public void setCmdItem(CmdItem cmdItem) {
		this.cmdItem = cmdItem;
	}
}

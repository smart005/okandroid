package com.cloud.core.beans;

public class CmdItem {
	private String actionname;

	private String actionid;

	private int iconresid = 0;

	private int textcolor = 0;

	private int bgresid = 0;

	private boolean isEnable = true;

	public CmdItem() {

	}

	public CmdItem(String cmdid, String cmdname, int iconresid, int bgresid) {
		this.actionid = cmdid;
		this.actionname = cmdname;
		this.iconresid = iconresid;
		this.bgresid = bgresid;
	}

	public CmdItem(String cmdid, String cmdname, int iconresid) {
		this(cmdid, cmdname, iconresid, 0);
	}

	public CmdItem(String cmdid, String cmdname) {
		this(cmdid, cmdname, 0, 0);
	}

	/**
	 * 取唯一标识名
	 * 
	 * @return
	 */
	public String getCommandName() {
		return this.actionname;
	}

	/**
	 * 设置唯一标识名
	 * 
	 * @param actionname
	 */
	public void setCommandName(String actionname) {
		this.actionname = actionname;
	}

	public String getCommandId() {
		return this.actionid;
	}

	public void setCommandId(String actionid) {
		this.actionid = actionid;
	}

	public void setIcon(int resid) {
		this.iconresid = resid;
	}

	public int getIcon() {
		return this.iconresid;
	}

	public void setTextColor(int color) {
		this.textcolor = color;
	}

	public int getTextColor() {
		return this.textcolor;
	}

	public int getBgresid() {
		return bgresid;
	}

	public void setBgresid(int bgresid) {
		this.bgresid = bgresid;
	}

	/**
	 * @return 获取isEnable
	 */
	public boolean isEnable() {
		return isEnable;
	}

	/**
	 * @param 设置isEnable
	 */
	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}
}

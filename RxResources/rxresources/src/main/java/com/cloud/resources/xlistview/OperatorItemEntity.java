package com.cloud.resources.xlistview;

/**
 * 
 * @Author LiJingHuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-6-7 下午8:28:37
 * @Description:列表项侧滑后操作项实体
 * @Modifier:
 * @ModifyContent:
 * 
 */
public class OperatorItemEntity {

	/**
	 * 项位置
	 * 		多个按钮时用于区分
	 */
	private int id = -1;

	/**
	 * 项文本
	 */
	private String text = "";
	/**
	 * 项图标
	 */
	private int icon = 0;
	/**
	 * 文本颜色
	 */
	private int textColor = 0;
	/**
	 * 背景颜色
	 */
	private int backgroudResId = 0;

	/**
	 * 项在列表中的位置
	 * 			在列表中时用于判断当前项在列表中位置
	 */
	private int position = -1;

	public OperatorItemEntity(String text, int icon, int textColor,
			int backgroudResId, int id) {
		this.text = text;
		this.icon = icon;
		this.textColor = textColor;
		this.backgroudResId = backgroudResId;
		this.id = id;
	}

	public OperatorItemEntity(String text, int icon, int textColor) {
		this(text, icon, textColor, 0, -1);
	}

	public OperatorItemEntity(String text, int icon) {
		this(text, icon, 0, 0, -1);
	}

	public OperatorItemEntity(String text) {
		this(text, 0, 0, 0, -1);
	}

	public OperatorItemEntity() {

	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public int getTextColor() {
		return textColor;
	}

	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}

	public int getBackgroudResId() {
		return backgroudResId;
	}

	public void setBackgroudResId(int backgroudResId) {
		this.backgroudResId = backgroudResId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
}

package com.cloud.resources.beans;

/**
 * 
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-11-17 上午11:25:06
 * @Description:
 * @Modifier:
 * @ModifyContent:
 * 
 */
public class BasePinnedSectionItem {

	/**
	 * 项类型
	 */
	private int itemType;
	private int sectionPosition;
	private int listPosition;
	/**
	 * 是否分组(列表分组时用)
	 */
	private boolean isGroup = false;
	/**
	 * 分组名称(列表分组时用)
	 */
	private String groupName = "";
	/**
	 * 是否选中(列表分组时用)
	 */
	private boolean isChk = false;

	/**
	 * @return 获取sectionPosition
	 */
	public int getSectionPosition() {
		return sectionPosition;
	}

	/**
	 * @param 设置sectionPosition
	 */
	public void setSectionPosition(int sectionPosition) {
		this.sectionPosition = sectionPosition;
	}

	/**
	 * @return 获取listPosition
	 */
	public int getListPosition() {
		return listPosition;
	}

	/**
	 * @param 设置listPosition
	 */
	public void setListPosition(int listPosition) {
		this.listPosition = listPosition;
	}

	/**
	 * @return 获取项类型
	 */
	public int getItemType() {
		return itemType;
	}

	/**
	 * @param 设置项类型
	 */
	public void setItemType(int itemType) {
		this.itemType = itemType;
	}

	/**
	 * @return 获取是否分组(列表分组时用)
	 */
	public boolean getIsGroup() {
		return isGroup;
	}

	/**
	 * 设置是否分组(列表分组时用)
	 *
	 * @param isGroup
	 */
	public void setIsGroup(boolean isGroup) {
		this.isGroup = isGroup;
	}

	/**
	 * @return 获取分组名称(列表分组时用)
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * 设置分组名称(列表分组时用)
	 *
	 * @param groupName
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return 获取是否选中(列表分组时用)
	 */
	public boolean getIsChk() {
		return isChk;
	}

	/**
	 * 设置是否选中(列表分组时用)
	 *
	 * @param isChk
	 */
	public void setIsChk(boolean isChk) {
		this.isChk = isChk;
	}
}

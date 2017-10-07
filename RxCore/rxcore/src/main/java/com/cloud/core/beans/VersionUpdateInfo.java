/**
 * @Title:  VersionUpdateInfo.java
 * @Description:
 * @author:  lijinghuan
 * @data:  2015年4月30日 下午3:47:23
 */
package com.cloud.core.beans;


public class VersionUpdateInfo {
	private ApkInfo apkinfo;
	private boolean isCompulsoryUpdate;
	private int noticeMode;
	private boolean isCheckedNotAgainRemind;

	public ApkInfo getApkinfo() {
		return apkinfo;
	}

	public void setApkinfo(ApkInfo apkinfo) {
		this.apkinfo = apkinfo;
	}

	public boolean isCompulsoryUpdate() {
		return isCompulsoryUpdate;
	}

	public void setCompulsoryUpdate(boolean isCompulsoryUpdate) {
		this.isCompulsoryUpdate = isCompulsoryUpdate;
	}

	public int getNoticeMode() {
		return noticeMode;
	}

	public void setNoticeMode(int noticeMode) {
		this.noticeMode = noticeMode;
	}

	public boolean isCheckedNotAgainRemind() {
		return isCheckedNotAgainRemind;
	}

	public void setCheckedNotAgainRemind(boolean isCheckedNotAgainRemind) {
		this.isCheckedNotAgainRemind = isCheckedNotAgainRemind;
	}
}

package com.cloud.core.appupdate;

import com.cloud.core.beans.InstanceUpdateServiceInfoEntity;

public class UpdateManager {

	private OnVersionUpdateListener mvulistener = null;

	private InstanceUpdateServiceInfoEntity miusinfo = new InstanceUpdateServiceInfoEntity();

	/**
	 * @param 设置miusinfo
	 */
	public void setMiusinfo(InstanceUpdateServiceInfoEntity miusinfo) {
		this.miusinfo = miusinfo;
	}

	public void start() {
		UpdateService muservice = new UpdateService();
		muservice.setMvulistener(mvulistener);
		muservice.onCheckAppUpdate(miusinfo);
	}

	public void setOnVersionUpdate(OnVersionUpdateListener listener) {
		mvulistener = listener;
	}
}

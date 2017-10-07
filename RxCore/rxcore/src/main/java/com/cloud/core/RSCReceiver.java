package com.cloud.core;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;

/**
 * 
 * @author lijinghuan
 * 
 */
public class RSCReceiver extends BroadcastReceiver {

	private Context currcontent = null;
	private Activity curractivity = null;
	private String curraction = "";

	protected void receiveBroadResult(Context context, Intent intent) {

	}

	protected void receiveBroadResult(Activity activity, Intent intent) {

	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if (TextUtils.isEmpty(curraction)) {
			receiveBroadResult(context, intent);
			receiveBroadResult(curractivity, intent);
		} else {
			if (intent.getAction().equalsIgnoreCase(curraction)) {
				receiveBroadResult(context, intent);
				receiveBroadResult(curractivity, intent);
			}
		}
	}

	private void registerBroadcastAction(Context context, String action) {
		currcontent = context;
		curraction = action;
		IntentFilter filter = new IntentFilter();
		filter.addAction(action);
		currcontent.registerReceiver(this, filter);
	}

	public void registerAction(Context context, String action) {
		registerBroadcastAction(context, action);
	}

	public void registerAction(Activity activity, String action) {
		curractivity = activity;
		registerBroadcastAction(activity, action);
	}
}

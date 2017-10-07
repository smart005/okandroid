package com.cloud.core.exception;

import android.content.Context;

import com.cloud.core.beans.CrashFileParam;
import com.cloud.core.enums.LogLevel;
import com.cloud.core.utils.GlobalUtils;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * 
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-7-1 下午6:53:18
 * @Description: 捕捉异常处理
 * @Modifier:
 * @ModifyContent:
 * 
 */
public class CrashHandler implements UncaughtExceptionHandler {

	/** 程序的Context对象 */
	private Context mContext;
	private String apppackgename = "";
	/** 系统默认的UncaughtException处理类 */
	private UncaughtExceptionHandler mDefaultHandler;

	/**
	 * 初始化,注册Context对象, 获取系统默认的UncaughtException处理器, 设置该CrashHandler为程序的默认处理器
	 * 
	 * @param ctx
	 */
	public void init(Context ctx, String packgename) {
		mContext = ctx;
		apppackgename = packgename;
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	/**
	 * 当UncaughtException发生时会转入该函数来处理
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		try {
			if (!handleException(ex) && mDefaultHandler != null) {
				// 如果用户没有处理则让系统默认的异常处理器来处理
				mDefaultHandler.uncaughtException(thread, ex);
			} else {
				GlobalUtils.exitApp(mContext, apppackgename);
			}
		} catch (Exception e) {
			// uncaughtException
		}
	}

	/**
	 * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
	 * 
	 * @param ex
	 * @return true:如果处理了该异常信息;否则返回false
	 */
	private boolean handleException(Throwable ex) {
		if (ex == null) {
			return true;
		}
		String msg = ex.getLocalizedMessage();
		if (msg == null) {
			return false;
		}
		CrashFileParam mcfparam = new CrashFileParam();
		mcfparam.setCrashValue(ex);
		mcfparam.setLevel(LogLevel.ERROR);
		CrashFileTask.writeLog(mcfparam);
		return true;
	}
}

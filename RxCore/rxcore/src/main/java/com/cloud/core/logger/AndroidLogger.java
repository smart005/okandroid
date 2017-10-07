package com.cloud.core.logger;

import android.util.Log;

import com.cloud.core.beans.CrashFileParam;
import com.cloud.core.enums.LogLevel;
import com.cloud.core.exception.CrashFileTask;
import com.cloud.core.utils.SDCardUtils;

public class AndroidLogger extends Logger {

    private static final boolean DEBUG = BuildConfig.DEBUG;
    private String tag = "frong";

    public AndroidLogger() {

    }

    private void saveLogInfo(String messages, Object logobj, LogLevel level) {
        if (SDCardUtils.hasSdcard()) {
            CrashFileParam mcfparam = new CrashFileParam();
            mcfparam.setMessage(messages);
            mcfparam.setCrashValue(logobj);
            mcfparam.setLevel(level);
            CrashFileTask.writeLog(mcfparam);
        }
    }

    private void saveLogInfo(Object logobj, LogLevel level) {
        saveLogInfo("", logobj, level);
    }

    @Override
    public void debug(String... messages) {
        if (isDebugEnabled()) {
            saveLogInfo(convertToStr(messages), LogLevel.DEBUG);
            Log.e(tag, convertToStr(messages));
        }
    }

    @Override
    public void debug(String message) {
        if (isDebugEnabled()) {
            saveLogInfo(message, LogLevel.DEBUG);
            Log.e(tag, message);
        }
    }

    @Override
    public void debug(String message, Throwable tr) {
        if (isDebugEnabled()) {
            saveLogInfo(message, tr, LogLevel.DEBUG);
            Log.e(tag, message, tr);
        }
    }

    @Override
    public void info(String... messages) {
        if (isInfoEnabled()) {
            saveLogInfo(convertToStr(messages), LogLevel.INFO);
            Log.e(tag, convertToStr(messages));
        }
    }

    @Override
    public void info(String message) {
        if (isInfoEnabled()) {
            saveLogInfo(message, LogLevel.INFO);
            Log.e(tag, message);
        }
    }

    @Override
    public void info(String message, Throwable tr) {
        if (isInfoEnabled()) {
            saveLogInfo(message, tr, LogLevel.INFO);
            Log.e(tag, message, tr);
        }
    }

    @Override
    public void error(String message) {
        saveLogInfo(message, LogLevel.ERROR);
        Log.e(tag, message);
    }

    @Override
    public void error(String... messages) {
        saveLogInfo(convertToStr(messages), LogLevel.ERROR);
        Log.e(tag, convertToStr(messages));
    }

    @Override
    public void error(Integer tags, String... messages) {
        saveLogInfo(tags.toString(), convertToStr(messages), LogLevel.ERROR);
        Log.e(tag, tags.toString() + convertToStr(messages));
    }

    @Override
    public void error(String message, Throwable tr) {
        saveLogInfo(message, tr, LogLevel.ERROR);
        Log.e(tag, message, tr);
    }

    @Override
    public void error(Throwable tr, String... messages) {
        saveLogInfo(convertToStr(messages), tr, LogLevel.ERROR);
        Log.e(tag, convertToStr(messages), tr);
    }

    @Override
    public void error(Integer tags, Throwable tr, String... messages) {
        saveLogInfo(tag.toString() + convertToStr(messages), tr, LogLevel.ERROR);
        Log.e(tag, tags.toString() + convertToStr(messages), tr);
    }

    @Override
    public void warn(String message) {
        if (isWarnEnabled()) {
            saveLogInfo(message, LogLevel.WARNING);
            Log.e(tag, message);
        }
    }

    @Override
    public void warn(String message, Throwable tr) {
        if (isWarnEnabled()) {
            saveLogInfo(message, tr, LogLevel.WARNING);
            Log.e(tag, message, tr);
        }
    }

    @Override
    public boolean isDebugEnabled() {
        return DEBUG;
    }

    @Override
    public boolean isErrorEnabled() {
        return DEBUG;
    }

    @Override
    public boolean isInfoEnabled() {
        return DEBUG;
    }

    @Override
    public boolean isWarnEnabled() {
        return DEBUG;
    }
}

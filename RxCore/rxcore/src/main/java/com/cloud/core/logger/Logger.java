package com.cloud.core.logger;

import java.lang.reflect.Field;

public abstract class Logger {

	/**
	 * 打印DEBUG级别日志
	 * 
	 * @param messages
	 *            , 可以输入如下格式的参数 key,value,key,value
	 * @return void
	 */
	public abstract void debug(String... messages);

	/**
	 * 打印DEBUG级别日志
	 * 
	 * @param message
	 * @return void
	 */
	public abstract void debug(String message);

	/**
	 * 打印DEBUG级别日志
	 * 
	 * @param message
	 * @param tr
	 * @return void
	 */
	public abstract void debug(String message, Throwable tr);

	/**
	 * 打印INFO级别日志
	 * 
	 * @param messages
	 *            , 可以输入如下格式的参数 key,value,key,value
	 * @return void
	 */
	public abstract void info(String... messages);

	/**
	 * 打印INFO级别日志
	 * 
	 * @param message
	 * @return void
	 */
	public abstract void info(String message);

	/**
	 * 打印INFO级别日志
	 * 
	 * @param message
	 * @param tr
	 * @return void
	 */
	public abstract void info(String message, Throwable tr);

	/**
	 * 打印ERROR级别日志
	 * 
	 * @param message
	 * @return void
	 */
	public abstract void error(String message);

	/**
	 * 打印ERROR级别日志
	 * 
	 * @param message
	 * @return void
	 */
	public abstract void error(String... messages);

	/**
	 * 打印ERROR级别日志
	 * 
	 * @param message
	 * @return void
	 */
	public abstract void error(Integer tags, String... messages);

	/**
	 * 打印ERROR级别日志
	 * 
	 * @param message
	 * @param tr
	 * @return void
	 */
	public abstract void error(String message, Throwable tr);

	/**
	 * 打印ERROR级别日志
	 * 
	 * @param tr
	 * @param messages
	 * @return void
	 */
	public abstract void error(Throwable tr, String... messages);

	/**
	 * 打印ERROR级别日志
	 * 
	 * @param tags
	 * @param tr
	 * @param messages
	 */
	public abstract void error(Integer tags, Throwable tr, String... messages);

	/**
	 * 打印WARN级别日志
	 * 
	 * @param message
	 * @return void
	 */
	public abstract void warn(String message);

	/**
	 * 打印WARN级别日志
	 * 
	 * @param message
	 * @param tr
	 * @return void
	 */
	public abstract void warn(String message, Throwable tr);

	/**
	 * DEBUG级别是否有效
	 * 
	 * @return
	 * @return boolean
	 */
	public abstract boolean isDebugEnabled();

	/**
	 * ERROR级别是否有效
	 * 
	 * @return
	 * @return boolean
	 */
	public abstract boolean isErrorEnabled();

	/**
	 * INFO级别是否有效
	 * 
	 * @return
	 * @return boolean
	 */
	public abstract boolean isInfoEnabled();

	/**
	 * WARN级别是否有效
	 * 
	 * @return
	 * @return boolean
	 */
	public abstract boolean isWarnEnabled();

	private static final LoggerFactory LOGGER_FACTORY = new AndroidLoggerFactory();

	public static Logger getLogger() {
		return LOGGER_FACTORY.getLogger();
	}

	public static Logger L = getLogger();

	/**
	 * 数组转换成字符串
	 * 
	 * @param messages
	 *            数组
	 * @return
	 */
	public static String convertToStr(String... messages) {
		if (messages == null) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("****start**");
		for (String string : messages) {
			buffer.append("**");
			buffer.append(string);
		}
		buffer.append("**end****");
		return buffer.toString();
	}

	/**
	 * 对象中的属性转换成字符串. 只将基本数据类型拼接成字符串。 主要用于打印bean对象, 不用于打印数组
	 * 
	 * @param obj
	 * @return
	 */
	public static String convertToStr(Object obj) {
		if (obj == null) {
			return null;
		}
		Field[] fields = obj.getClass().getDeclaredFields();
		String className = obj.getClass().getName();
		StringBuffer buffer = new StringBuffer();
		buffer.append("****start**");
		buffer.append("classname:").append(className).append("**\r\n");
		try {
			for (Field field : fields) {
				field.setAccessible(true);
				String name = field.getName();
				Object value = field.get(obj);// 返回字符串
				buffer.append("**");
				buffer.append(name).append(":").append(value);
				buffer.append("\r\n");
			}
		} catch (Exception e) {
			buffer.append(e.getMessage());
		}
		buffer.append("**end****");
		return buffer.toString();
	}
}

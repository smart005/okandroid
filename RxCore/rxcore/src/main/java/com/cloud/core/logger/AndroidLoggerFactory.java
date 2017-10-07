package com.cloud.core.logger;

public class AndroidLoggerFactory extends LoggerFactory {

	@Override
	public Logger getLogger() {
		return new AndroidLogger();
	}
}

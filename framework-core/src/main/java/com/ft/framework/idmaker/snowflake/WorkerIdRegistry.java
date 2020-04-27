package com.ft.framework.idmaker.snowflake;

public interface WorkerIdRegistry {

	/**
	 * 获得WorkerId
	 * 
	 * @param datacenterId
	 * @param moduleName
	 * @return
	 */
	WorkerId register(int datacenterId, String moduleName);

}

package com.kltb.framework.idmaker;

import com.kltb.framework.idmaker.snowflake.SnowFlakeId;
import com.kltb.framework.idmaker.snowflake.SnowFlakeIdFactory;

import java.util.UUID;

public class IdMaker {

	/**
	 * 生成ObjectId
	 * <p>12字节，24字符长，保证顺序
	 * @return
	 */
	public static String makeObjectId() {
		ObjectId objectId = ObjectId.get();
		return objectId.toHexString();
	}
	
	/**
	 * 生成UUID
	 * @return
	 */
	public static String makeUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replace("-", "");
	}
	
	/**
	 * 生成编号
	 * @return
	 */
	public static long nextId() {
		SnowFlakeId snowFlakeId = SnowFlakeIdFactory.getSnowFlakeId(null);
		return snowFlakeId.nextId();
	}
	
	/**
	 * 生成编号
	 * @return
	 */
	public static long nextId(String moduleName) {
		SnowFlakeId snowFlakeId = SnowFlakeIdFactory.getSnowFlakeId(moduleName);
		return snowFlakeId.nextId();
	}
}

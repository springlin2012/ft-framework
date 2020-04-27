package com.ft.framework.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class NumberUtils {
	
	public static DecimalFormat df = new DecimalFormat("0.00");
	
	/**
	 * 默认double数据格式
	 * @param d
	 * @return
	 */
	public static String getDefaultFormatHalfDown(Double d){
		if(d==null){
			return "";
		}
		df.setRoundingMode(RoundingMode.DOWN);
		return df.format(d);
	}
	
	
	/**
	 * 默认double数据格式
	 * @param d
	 * @return
	 */
	public static String getFormat(Double d,String formate){
		if(d==null){
			return "";
		}
		DecimalFormat df = new DecimalFormat(formate);
		df.setRoundingMode(RoundingMode.DOWN);
		return df.format(d);
	}
	
	/**
	 * 默认double数据格式
	 * @param d
	 * @return
	 */
	public static String getFormatHalfUp(Double d,String formate){
		if(d==null){
			return "";
		}
		DecimalFormat df = new DecimalFormat(formate);
		df.setRoundingMode(RoundingMode.HALF_UP);
		return df.format(d);
	}
	
	/**
	 * 默认BigDecimal数据格式
	 * @param d
	 * @return
	 */
	public static String getFormat(BigDecimal d,String formate){
		if(d==null){
			return "";
		}
		DecimalFormat df = new DecimalFormat(formate);
		df.setRoundingMode(RoundingMode.DOWN);
		return df.format(d);
	}
	
	/**
	 * 默认BigDecimal数据格式
	 * @param d
	 * @return
	 */
	public static String getDefaultFormatHalfDown(BigDecimal d){
		if(d==null){
			return "0.00";
		}
		df.setRoundingMode(RoundingMode.DOWN);
		return df.format(d);
	}
	
	
	/**
	 * 默认BigDecimal数据格式
	 * @param d
	 * @return
	 */
	public static String getDefaultFormatHalfDown(Object d){
		if(d==null){
			return "";
		}
		df.setRoundingMode(RoundingMode.DOWN);
		return df.format(d);
	}
}

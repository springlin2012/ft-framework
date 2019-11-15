package com.kltb.framework.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RateUtils {
	public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
	
	public static final BigDecimal TWELVE= new BigDecimal(12);
	
	/**
	 * 获取用于计算的年利率
	 *
	 * @param dbMonthRate 数据库中的月利率
	 * @return
	 *
	 */
	public static BigDecimal getYearRateForCal(BigDecimal dbMonthRate){
		if(dbMonthRate==null){
			return BigDecimal.ZERO;
		}
		return dbMonthRate.multiply(BigDecimal.valueOf(12)).divide(BigDecimal.valueOf(100)).setScale(4, RoundingMode.HALF_UP);
	}
	
	
	/**
	 * 获取用于计算的月利率
	 * 
	 * @param dbMonthRate 数据库中的月利率
	 * @return
	 */
	public static BigDecimal getMonthRateForCal(BigDecimal dbMonthRate){
		if(dbMonthRate==null){
			return BigDecimal.ZERO;
		}
		return new BigDecimal(dbMonthRate.doubleValue()/100);
	}
	
	/**
	 * 获取罚息利率
	 *
	 * @param overdueRate
	 * @return
	 */
	public static BigDecimal getOverdueRateForCal(BigDecimal overdueRate){
		if(overdueRate==null){
			return BigDecimal.ZERO;
		}
		return new BigDecimal(overdueRate.doubleValue()/100);
	}
	
	/**
	 * 获取逾期罚息
	 *
	 * @param monthRate 月利率
	 * @param overdueAmountPenalty 罚息比例
	 * @param leftAmount 待还款金额 
	 *
	 */
	public static long getOverdueAmt(BigDecimal monthRate,BigDecimal overdueAmountPenalty,Long leftAmount){
		if(leftAmount==null){
			return 0;
		}
		return monthRate.multiply(overdueAmountPenalty).multiply(new BigDecimal(leftAmount))
				.divide(new BigDecimal(30),8,RoundingMode.HALF_UP).divide(ONE_HUNDRED,0,RoundingMode.HALF_UP).longValue();
	}
	
}

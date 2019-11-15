package com.kltb.framework.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public final class DecimalUtil {
	private static final DecimalFormat FORMAT = new DecimalFormat("0.00");

	public static final BigDecimal ZERO = BigDecimal.ZERO;
	private static final int SCALE = 2;

	/****
	 * 格式化
	 ****/
	public static BigDecimal format(double value) {
		return new BigDecimal(FORMAT.format(value));
	}

	/****
	 * 格式化
	 ****/
	public static BigDecimal format(long value) {
		return new BigDecimal(FORMAT.format(value));
	}

	/****
	 * 格式化
	 ****/
	public static BigDecimal formatWithoutScale(BigDecimal value) {
		return value.setScale(0, 6);
	}

	/****
	 * 格式化
	 ****/
	public static BigDecimal format(BigDecimal value) {
		return value.setScale(2, 6);
	}

	/****
	 * 加法
	 ****/
	public static BigDecimal add(BigDecimal d1, BigDecimal d2) {
		return format(d1.add(d2));
	}

	/****
	 * 加法
	 ****/
	public static BigDecimal add(BigDecimal v1, BigDecimal[] vs) {
		BigDecimal sum = BigDecimal.ZERO;
		for (BigDecimal v : vs) {
			sum = add(sum, v);
		}
		return add(v1, sum);
	}

	/****
	 * 减法
	 ****/
	public static BigDecimal subtract(BigDecimal v1, BigDecimal[] vs) {
		BigDecimal sum = add(BigDecimal.ZERO, vs);
		return subtract(v1, sum);
	}

	public static BigDecimal subtract(BigDecimal d1, BigDecimal d2) {
		return format(d1.subtract(d2));
	}

	/****
	 * 除法
	 ****/
	public static BigDecimal divide(BigDecimal v1, BigDecimal v2) {
		return format(v1.divide(v2, MathContext.DECIMAL128));
	}

	/****
	 * 除法
	 ****/
	public static BigDecimal divide(BigDecimal v1, BigDecimal v2, int roundingMode) {
		return v1.divide(v2, 2, roundingMode);
	}

	/****
	 * 乘法
	 ****/
	public static BigDecimal multiply(BigDecimal v1, BigDecimal v2) {
		return format(v1.multiply(v2));
	}

	/****
	 * 比较两个 BigDecimal 的值
	 ****/
	private static int compareBigDecimal(BigDecimal v1, BigDecimal v2) {
		return format(v1).compareTo(format(v2));
	}

	/****
	 * vi > v2
	 ****/
	public static boolean gt(BigDecimal v1, BigDecimal v2) {
		return compareBigDecimal(v1, v2) > 0;
	}

	/****
	 * vi >= v2
	 ****/
	public static boolean ge(BigDecimal v1, BigDecimal v2) {
		return compareBigDecimal(v1, v2) >= 0;
	}

	/****
	 * vi == v2
	 ****/
	public static boolean eq(BigDecimal v1, BigDecimal v2) {
		return compareBigDecimal(v1, v2) == 0;
	}

	/****
	 * vi < v2
	 ****/
	public static boolean lt(BigDecimal v1, BigDecimal v2) {
		return compareBigDecimal(v1, v2) < 0;
	}

	/****
	 * vi <= v2
	 ****/
	public static boolean le(BigDecimal v1, BigDecimal v2) {
		return compareBigDecimal(v1, v2) <= 0;
	}

	/****
	 * vi != v2
	 ****/
	public static boolean ne(BigDecimal v1, BigDecimal v2) {
		return compareBigDecimal(v1, v2) != 0;
	}

	static {
		FORMAT.setRoundingMode(RoundingMode.HALF_EVEN);
	}
}

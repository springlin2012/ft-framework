package com.ft.framework.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 功能描述: 金额计算工具类
 * 创建人: chunlin.li
 */
public class BigDecimalUtil {

    /**
     * 金额相加
     * @param a
     * @param b
     * @return
     */
    public static double add(double a, double b, int scale) {
        BigDecimal bg_a = new BigDecimal(Double.toString(a)).setScale(scale, BigDecimal.ROUND_DOWN);
        BigDecimal bg_b = new BigDecimal(Double.toString(b)).setScale(scale, BigDecimal.ROUND_DOWN);

        return bg_a.add(bg_b).setScale(scale).doubleValue();
    }

    /**
     * 相乘
     * @param a
     * @param b
     * @return
     */
    public static Double mul(double a, double b) {
        BigDecimal bdA = new BigDecimal(a);
        BigDecimal bdB = new BigDecimal(b);

        return bdA.multiply(bdB).doubleValue();
    }

    public static Double mul(double a, double b, int scale) {
        BigDecimal bdA = new BigDecimal(a);
        BigDecimal bdB = new BigDecimal(b);

        return round(bdA.multiply(bdB).doubleValue(), scale);
    }

    /**
     * 相除
     * @param a
     * @param b
     * @return
     */
    public static Double div(double a, double b, int len) {
        BigDecimal bdA = new BigDecimal(a);
        BigDecimal bdB = new BigDecimal(b);

        return bdA.divide(bdB, len, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 进行加法运算
     * @param d1
     * @param d2
     * @return
     */
    public static Double add(double d1, double d2)
    {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.add(b2).doubleValue();
    }

    /**
     * 进行减法运算
     * @param d1
     * @param d2
     * @return
     */
    public static Double sub(double d1, double d2)
    {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 取余
     * @param a
     * @param scale
     * @return
     */
    public static Double mod(Double a, int scale) {
        BigDecimal bg_a = new BigDecimal(Double.toString(a)).setScale(scale, BigDecimal.ROUND_DOWN);
        return bg_a.setScale(scale).doubleValue();
    }

    //截断位数
    public static Double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 获取产品利率
     * @param rateVal
     * @return
     */
    public static Double getRate(double rateVal) {
        return (rateVal < 0) ? rateVal:(rateVal / 100);
    }
    
    
    /**
     * 方法描述：乘法
     * @param l
     * @param d
     * @return
     */
    public static Long multiply(Long l, Double d) {
        if(l == null || d == null) {
            return null;
        }
        return BigDecimal.valueOf(l).multiply(BigDecimal.valueOf(d))
                .setScale(0, RoundingMode.HALF_UP).longValue();
    }
    
    /**
	 * 最后scale位数后 四舍五入
	 * 例如: 1249 变为 1200
	 *      1250 变为1300
	 *
	 * @param data
	 * @param scale
	 * @return
	 */
	public static long halfup(Long data,int scale){
		//return data;
		if(data==null){
			return 0;
		}
		int times =  new Double(Math.pow(10,scale)).intValue();
		return new BigDecimal(data).divide(new BigDecimal(times),0, BigDecimal.ROUND_HALF_UP).longValue()*times;
	}

	
	/**
	 * 四舍五入格式为2为小数，
	 * 100.256d - 100.26
	 * 100 -  100.00
	 *
	 * @param amount
	 * @return
	 */
	public static String formate(BigDecimal amount){
		if(amount==null){
			return "0.00";
		}
		return amount.setScale(2,BigDecimal.ROUND_HALF_UP).toString();
	}

}

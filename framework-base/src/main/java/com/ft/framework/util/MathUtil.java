package com.ft.framework.util;

import java.math.BigDecimal;

/**
 * 
 * <b>类说明：</b> 数值处理工具类
 */
public class MathUtil {

    /** 保留小数位 */
    public static final int SCALE = 2;

    /** 四舍五入模式 */
    public static final int ROUND_MOD = BigDecimal.ROUND_HALF_UP;

    /**
     * <b>说明:</b> 取两位小数，并四舍五入
     * 
     * @param srcDecimal
     * @return
     */
    public static BigDecimal roundTwoScale(BigDecimal srcDecimal) {
        if (srcDecimal == null) {
            return BigDecimal.ZERO;
        }
        return srcDecimal.setScale(SCALE, ROUND_MOD);
    }

    /**
     * 相除
     * 
     * @param dividend
     *            除数
     * @param divisor
     *            被除数
     * @return BigDecimal
     */
    public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor) {
        assert divisor != null && !eq(divisor, BigDecimal.ZERO);
        if (dividend == null) {
            return BigDecimal.ZERO;
        }
        return dividend.divide(divisor, SCALE, ROUND_MOD);
    }

    /**
     * 相除
     * 
     * @param dividend
     *            除数
     * @param divisor
     *            被除数
     * @param scale
     *            指定小数位 0 &lt; scale &le; 8
     * @return BigDecimal
     */
    public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor, int scale) {
        assert scale > 0 && scale <= 8;
        assert divisor != null && !eq(divisor, BigDecimal.ZERO);
        if (dividend == null) {
            return BigDecimal.ZERO;
        }
        return dividend.divide(divisor, scale, ROUND_MOD);
    }

    /**
     * <b>说明:</b> 判断两个小数值是否相同（比较时会四舍五入到两位小数）
     * 
     * @param decimal1
     * @param decimal2
     * @return
     */
    public static boolean eq(BigDecimal decimal1, BigDecimal decimal2) {
        return compare(decimal1, decimal2) == 0;
    }

    /**
     * <b>说明:</b> 判断第一个小数是否小于第二个小数（比较时会四舍五入到两位小数）
     * 
     * @param decimal1
     * @param decimal2
     * @return
     */
    public static boolean lt(BigDecimal decimal1, BigDecimal decimal2) {
        return !ge(decimal1, decimal2);
    }

    /**
     * <b>说明:</b> 判断第一个小数是否小于等于第二个小数（比较时会四舍五入到两位小数）
     * 
     * @param decimal1
     * @param decimal2
     * @return
     */
    public static boolean le(BigDecimal decimal1, BigDecimal decimal2) {
        return !gt(decimal1, decimal2);
    }

    /**
     * <b>说明:</b> 判断第一个小数是否小于第二个小数（比较时会四舍五入到两位小数）
     * 
     * @param decimal1
     * @param decimal2
     * @return
     */
    public static boolean gt(BigDecimal decimal1, BigDecimal decimal2) {
        return compare(decimal1, decimal2) > 0;
    }

    /**
     * <b>说明:</b> 判断第一个小数是否小于等于第二个小数（比较时会四舍五入到两位小数）
     * 
     * @param decimal1
     * @param decimal2
     * @return
     */
    public static boolean ge(BigDecimal decimal1, BigDecimal decimal2) {
        return compare(decimal1, decimal2) >= 0;
    }

    /**
     * <b>说明:</b> 判断两个数的大小关系（比较时会四舍五入到两位小数）
     * <p>
     * 如果第一个小数小于、等于、大于第二个小数，则返回-1、0、1；
     * 
     * @param first
     * @param second
     * @return
     */
    public static int compare(BigDecimal first, BigDecimal second) {
        if (first == null) {
            return second == null ? 0 : -1;
        }

        if (second == null) {
            return 1;
        }

        // 如果与0比较,则不用再相减
        if (!BigDecimal.ZERO.equals(second)) {
            return first.subtract(second).setScale(SCALE, ROUND_MOD).compareTo(BigDecimal.ZERO);
        }
        else {
            return first.setScale(SCALE, ROUND_MOD).compareTo(BigDecimal.ZERO);
        }
    }
}

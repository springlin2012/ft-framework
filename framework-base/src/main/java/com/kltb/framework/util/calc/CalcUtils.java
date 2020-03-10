package com.kltb.framework.util.calc;

import com.kltb.framework.util.BigDecimalUtil;
import com.kltb.framework.util.RateUtils;
import com.kltb.framework.util.RoundOpt;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @descript: 利息计算工具类
 * @auth: lichunlin
 */
public class CalcUtils {

    /**
     * 等额本息
     */
    public final static int ACPI = CalcHelper.ACPI;

    /**
     * 按月付息,到期还本
     */
    public final static int MPDI = CalcHelper.MPDI;

    /**
     * 到期返本付息(一次性还本付息)
     */
    public final static int IRDTM = CalcHelper.IRDTM;

    /**
     * 等额本金
     */
    public final static int ACAI = CalcHelper.ACAI;


    private static CalcHelper calcHelper = new CalcHelper(new RoundOpt() {
        @Override
        public long invoke(long params) {
            return BigDecimalUtil.halfup(params, 2);
        }
    });


    /**
     * 计算月标的本息收益 预期本金和利息（principal and interest）
     *
     * @param capital   贷款金额
     * @param cycle     贷款月数
     * @param rate      年利率
     * @param repayType 年利率
     * @return
     */
    public static long getPI(long capital, int cycle, double rate, int repayType) {
        return calcHelper.getPI(capital, cycle, rate, repayType);
    }


    /**
     * 每月还款本息 (principal and interest of month)
     *
     * @param capital    本金
     * @param currPeriod 当前是第几期
     * @param cycle      借款期数
     * @param rate
     * @return
     */
    public static long getMPI(long capital, int currPeriod, int cycle, double rate, int repayType) {
        return calcHelper.getMPI(capital, currPeriod, cycle, rate, repayType);
    }


    /**
     * 获取每个月的利息
     *
     * @param capital   本金
     * @param i         当前是第几期
     * @param month     总共多少个月
     * @param rate      年化利率
     * @param repayType 还款类型
     * @return
     */
    public static long getMI(long capital, int i, int month, double rate, int repayType) {
        return calcHelper.getMI(capital, i, month, rate, repayType);
    }

    /**
     * 获取每个月待还的本金
     *
     * @param capital   本金
     * @param i         第几个月
     * @param month     总的月数
     * @param rate      年利率
     * @param repayType 还款类型
     * @return
     */
    public static BigDecimal getMP(long capital, int i, int month, double rate, int repayType) {
        return calcHelper.getMP(capital, i, month, rate, repayType);
    }

    /**
     * 计算月标到投资期限所产生的总的利息收益 预期利息 (sum interest)
     *
     * @param capital   贷款金额
     * @param month     贷款月数
     * @param rate      年利率
     * @param repayType 年利率
     * @return
     */
    public static long getSI(long capital, int month, double rate, int repayType) {
        return calcHelper.getSI(capital, month, rate, repayType);
    }


    /**
     * 获取剩余本金
     *
     * @param capital   初始借款本金
     * @param i         已还期数
     * @param month     总共期数
     * @param rate      借款年利率
     * @param repayType 还款类型
     * @return
     */
    public static long getRP(long capital, int i, int month, double rate, int repayType) {
        return calcHelper.getRP(capital, i, month, rate, repayType);
    }

    /**
     * 获取月标的剩余利息(非提前还款计算剩余利息)
     *
     * @param capital   初始借款本金
     * @param repayed   已还期数
     * @param cycle     总共期数
     * @param rate      借款年利率
     * @param repayType 还款类型
     * @return
     */
    public static long getRI(long capital, int repayed, int cycle, double rate, int repayType) {
        return calcHelper.getRI(capital, repayed, cycle, rate, repayType);
    }


    /**
     * 获取还款日列表
     *
     * @param date      放款时间(次日计息，该日起为计息日的昨日)
     * @param cycle     借款周期(如果是月标就是第几期，如果是天标就是总的时间)
     * @param repayType 还款类型
     * @return
     */
    public static Map<Integer, Date> getNRD(Date date, int cycle, int repayType) {
        return calcHelper.getNRD(date, cycle, repayType);

    }

    /**
     * 提前还款费用
     *
     * @param amount    本金
     * @param monthRate 月利率 (单位%)
     * @return
     */
    public static long getPreRefundFee(Long amount, BigDecimal monthRate) {
        if (amount == null || monthRate == null) {
            return 0;
        }
        return RateUtils.getMonthRateForCal(monthRate).multiply(new BigDecimal(amount)).longValue();
    }


    /**
     * 服务费
     * 算法： 服务费 = 本金 * 费率
     *
     * @param capital 本金
     * @param rate    服务费率
     * @return
     */
    public BigDecimal getMFee(BigDecimal capital, BigDecimal rate) {
        BigDecimal mFee = capital.multiply(rate);
        return mFee;
    }


    /**
     * 获取日费率费用 [add by lichunlin 2018/01/05]
     *
     * @param capital  金额
     * @param loanDays 天数
     * @param rate     利率
     * @return
     */
    public static long getRateFeeByDay(long capital, int loanDays, double rate) {
        CalcHelper helper = new CalcHelper();
        BigDecimal result = BigDecimal.ZERO;
        BigDecimal capitalBd = BigDecimal.valueOf(capital);

        Double dayRate = rate / 30; // 月利率转换日利率
        result = capitalBd.multiply(new BigDecimal(dayRate)).multiply(new BigDecimal(loanDays)).divide(new BigDecimal(100))
                .setScale(CalcHelper.SCALE, BigDecimal.ROUND_HALF_UP);

        return helper.handRound(result.longValue());
    }

    /**
     * 获取月费率费用
     *
     * @param capital  金额
     * @param month 月份数
     * @param rate     月利率
     * @return
     */
    public static long getRateFeeByMonth(long capital, int month, double rate) {
        CalcHelper helper = new CalcHelper();
        BigDecimal result = BigDecimal.ZERO;
        BigDecimal capitalBd = BigDecimal.valueOf(capital);

        result = capitalBd.multiply(new BigDecimal(rate)).multiply(new BigDecimal(month)).divide(new BigDecimal(100))
                .setScale(CalcHelper.SCALE, BigDecimal.ROUND_HALF_UP);

        return helper.handRound(result.longValue());
    }

    /**
     * 根据用户实际到手金额反计算用户借款金额
     * 公式：借款金额=用户实际到手金额 / (1 - 月利率 / 30 * 借款日期)
     *
     * @param capital  用户实际到手金额,单位：毫
     * @param loanDays 天数
     * @param rate     利率
     * @return
     */
    public static long getLoanAmountByTradeAmount(long capital, int loanDays, double rate) {
        CalcHelper helper = new CalcHelper();
        BigDecimal result;
        BigDecimal capitalBd = BigDecimal.valueOf(capital);

        Double dayRate = rate / 30; // 月利率转换日利率
        //公式：借款金额=用户实际到手金额 / (1 - 月利率 / 30 * 借款日期)
        result = capitalBd.divide(
                new BigDecimal(new Integer(1)).subtract(
                        new BigDecimal(dayRate).multiply(new BigDecimal(loanDays)).divide(new BigDecimal(100))
                ), CalcHelper.SCALE, BigDecimal.ROUND_HALF_UP
        );

        return helper.handRound(result.longValue());
    }

    public static void main(String[] args) {

        int repayType = CalcUtils.ACPI;

        // 借款金额
        long capital = 5000 * 10000;

        // 借款周期
        int cycle = 12;

        // 月利率
        double mouthRate = 30;

        // 年利率
        double rate = 3.45;

        // 借款日期
        int loanDays = 14;

        // 计算月标的本息收益 预期本金和利息
        /*System.out.println(CalcUtils.getLoanAmountByTradeAmount(capital, loanDays, mouthRate));

        // 计算月标的本息收益 预期本金和利息
        System.out.println(CalcUtils.getPI(capital, cycle, rate, repayType));

        // 预期利息
        System.out.println(CalcUtils.getSI(capital, cycle, rate, repayType));*/


        System.out.println(Math.pow((1 + rate), cycle));
        /** -------------------------------------------------------------- */
        /** 模拟实际计算公式用户还款利息 (计算最后一期利息时, 算已还每期利息应当先“四舍五入”再相加, ) */
        /** -------------------------------------------------------------- */
        /*System.out.println("------ 模拟实际计算公式用户还款利息 ------");
        System.out.println("用户【A】,【B】, 分别使用两种方式计算得出最终总还利息金额");
        System.out.println("----------------------------------------");

        // 计算每月还款本息
        long countProfit = CalcUtils.getSI(capital, cycle, rate, repayType);
        System.out.println("====================== 总利息: "+ countProfit +"======================");

        long countProfit11 = 0L;
        for (int i = 1; i < cycle; i++) { // 计算十一期利息(每期四舍五入)
            long currentProfit = CalcUtils.getMI(capital, i, cycle, rate, repayType);
            countProfit11 += currentProfit;
            System.out.println("期数："+ i +"利息：" + currentProfit);
        }

        System.out.println(">>> 11期总利息【A】: "+ countProfit11);
        System.out.println(">>> 12期应还息【A】: "+ (countProfit - countProfit11));
        System.out.println(">>> 实际总还利息【A】: "+ (countProfit11 + (countProfit - countProfit11)));


        *//** -------------------------------------------------------------- *//*
        *//** 当前计算公式用户还款利息模拟 *//*
        *//** -------------------------------------------------------------- *//*
        System.out.println("------ 当前计算公式用户还款利息模拟 ------");
        CalcHelper calcHelper = new CalcHelper();
        long countProfit2_11 = 0L;
        for (int i = 1; i < cycle; i++) { // 计算十一期利息 (不进行四舍五入)
            long currentProfit = calcHelper.getMI(capital, i, cycle, rate, repayType);
            countProfit2_11 += currentProfit;

            System.out.println("期数："+ i +"利息：" + currentProfit);
        }

        System.out.println(">>>  11期总利息【B】: "+ countProfit2_11); // 已还未四舍五入利息
        long refundPrfit12 = (countProfit - countProfit2_11); // 最后一期利息 = (总利息 - 已还期利息)
        System.out.println(">>>  12期应还息【B】: "+ refundPrfit12 );
        // 总还利息 = 已还期利息(四舍五入) + 最后一期利息
        System.out.println(">>>  实际总还利息【B】: "+ (countProfit11 + BigDecimalUtil.halfup(refundPrfit12, 2) ));*/
    }
}
    

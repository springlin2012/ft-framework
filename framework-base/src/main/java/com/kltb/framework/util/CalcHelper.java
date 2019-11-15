package com.kltb.framework.util;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @descript: 利息计算工具类
 * @auth: lichunlin
 */
public class CalcHelper {
    /**
     * 等额本息
     */
    public final static int ACPI = 1;

    /**
     * 按月付息,到期还本
     */
    public final static int MPDI = 2;

    /**
     * 到期返本付息(一次性还本付息)
     */
    public final static int IRDTM = 3;

    /**
     * 等额本金
     */
    public final static int AC = 4;

    /**
     * 等额等息
     */
    public final static int ACAI = 5;

    /**
     * 小数位精度
     */
    public final static int SCALE = 2;

    private RoundOpt roundOpt;


    public CalcHelper(){

    }

    public CalcHelper(RoundOpt roundOpt){
        this.roundOpt = roundOpt;
    }

    /**
     * 尾数处理
     *
     */
    public long handRound(long params){
        if(roundOpt!=null){
            return roundOpt.invoke(params);
        }
        return params;
    }



    /**
     * 计算月标的本息收益 预期本金和利息（principal and interest）
     *
     * @param capital 贷款金额
     * @param cycle 贷款月数
     * @param rate 年利率
     * @param repayType 年利率
     * @return
     */
    public  long getPI(long capital, int cycle, double rate, int repayType) {

        BigDecimal result = BigDecimal.ZERO;

        BigDecimal capitalBd = BigDecimal.valueOf(capital);

        // 等额本息
        if (repayType == ACPI) {
            rate = rate / 12;
            result = capitalBd.multiply(new BigDecimal(cycle * rate * Math.pow((1 + rate), cycle)))
                    .divide(new BigDecimal(Math.pow((1 + rate), cycle) - 1),SCALE, BigDecimal.ROUND_HALF_UP);
        }
        // 按月付息
        else if (repayType == MPDI) {
            /**
             * 算法： 每月利息 = 本金*利率/12 本息 = 本金 + 本金*利率*月份/12
             */
            BigDecimal interest = capitalBd.multiply(new BigDecimal(rate))
                    .multiply(new BigDecimal(cycle))
                    .divide(new BigDecimal(12), SCALE, BigDecimal.ROUND_HALF_UP);
            result = capitalBd.add(interest);
        }
        // 到期返本付息
        else if (repayType == IRDTM) {
            BigDecimal interest = capitalBd.multiply(new BigDecimal(rate))
                    .multiply(new BigDecimal(cycle))
                    .divide(new BigDecimal(12), SCALE, BigDecimal.ROUND_HALF_UP);
            result = capitalBd.add(interest);
        }
        //等额等息,按月付息
        else if(repayType == ACAI){
            BigDecimal interest = capitalBd.multiply(new BigDecimal(rate))
                    .multiply(new BigDecimal(cycle))
                    .divide(new BigDecimal(12), SCALE, BigDecimal.ROUND_HALF_UP);
            result = capitalBd.add(interest);
        }
        return handRound(result.longValue());
    }





    /**
     * 每月还款本息 (principal and interest of month)
     * @param capital 本金
     * @param currPeriod 当前是第几期
     * @param cycle 借款期数
     * @param rate
     * @return
     */
    public   long getMPI(long capital, int currPeriod, int cycle, double rate, int repayType) {

        BigDecimal mpi = BigDecimal.ZERO;

        BigDecimal capitalBd = BigDecimal.valueOf(capital);

        // 等额本息
        if (repayType == ACPI) {

            // 已收益金额
            BigDecimal hpi = BigDecimal.ZERO;

            double avgrate = rate / 12;
            // 最后一个月,返还金额=总金额-前面返还金额之和
            if (currPeriod == cycle) {
                hpi = capitalBd.multiply(new BigDecimal(avgrate))
                        .multiply(new BigDecimal((Math.pow((1 + avgrate), cycle)) / (Math.pow((1 + avgrate), cycle) - 1)))
                        .setScale(SCALE, BigDecimal.ROUND_HALF_UP);
                mpi = BigDecimal.valueOf(getPI(capital, cycle, rate, repayType)).subtract(new BigDecimal(handRound(hpi.longValue())*(cycle - 1)));
            } else {
                mpi = capitalBd.multiply(new BigDecimal(avgrate))
                        .multiply(new BigDecimal((Math.pow((1 + avgrate), cycle)) / (Math.pow((1 + avgrate), cycle) - 1)))
                        .setScale(SCALE, BigDecimal.ROUND_HALF_UP);
            }
        }
        // 按月付息
        else if (repayType == MPDI) {
            mpi = BigDecimal.valueOf(getMI(capital, currPeriod, cycle, rate, repayType));
            // 最后一个月需要加上本金
            if (currPeriod == cycle) {
                mpi = capitalBd.add(mpi);
            }
        }
        // 到期返本付息
        else if (repayType == IRDTM) {
            if(currPeriod==cycle){
                mpi = BigDecimal.valueOf(getPI(capital,cycle,  rate,  repayType));
            }else{
                mpi =  BigDecimal.ZERO;
            }
        }
        //等额等息,按月付息
        else if(repayType == ACAI){
            mpi = BigDecimal.valueOf(getMI(capital, currPeriod, cycle, rate, repayType));//利息
            BigDecimal p = getMP(capital, currPeriod, cycle, rate, repayType);//本金
            mpi = mpi.add(p);
        }
        return handRound(mpi.longValue());
    }


    /**
     * 获取每个月的利息
     * @param capital 本金
     * @param i 当前是第几期
     * @param month 总共多少个月
     * @param rate 年化利率
     * @param repayType 还款类型
     * @return
     */
    public  long getMI(long capital, int i, int month, double rate, int repayType) {

        BigDecimal mi = BigDecimal.ZERO;
        BigDecimal capitalBd = BigDecimal.valueOf(capital);

        // 等额本息
        if (repayType == ACPI) {
            // 已支付本息
            BigDecimal hpi = BigDecimal.ZERO;
            double avgrate = rate / 12;
            // 最后一个月,还款利息=总利息-前面已还利息
            if (month == i) {
                for (int n = 1; n < month; n++) {
                    hpi = hpi.add(new BigDecimal(
                            handRound(capitalBd.multiply(new BigDecimal(avgrate))
                                    .multiply(new BigDecimal((Math.pow((1 + avgrate), month) - Math.pow((1 + avgrate), (n - 1))) / (Math.pow((1 + avgrate), month) - 1)))
                                    .setScale(SCALE, BigDecimal.ROUND_HALF_UP).longValue())));
                }
                mi = BigDecimal.valueOf(getSI(capital, month, rate, repayType)).subtract(hpi);
            } else {
                mi = capitalBd.multiply(new BigDecimal(avgrate))
                        .multiply(new BigDecimal((Math.pow((1 + avgrate), month) - Math.pow((1 + avgrate), (i - 1))) / (Math.pow((1 + avgrate), month) - 1)))
                        .setScale(SCALE, BigDecimal.ROUND_HALF_UP);
            }
        }

        // 按月付息
        else if (repayType == MPDI) {
            // 每个月利息
            mi = BigDecimal.valueOf(getSI(capital, 1, rate, repayType));
            // 校准最后一个月利息(总的利息-前N个月的利息)
            if (i == month) {
                BigDecimal si = BigDecimal.valueOf(getSI(capital, month, rate, repayType));
                mi = si.subtract(mi.multiply(new BigDecimal(month - 1)));
            }
        }
        // 到期返本付息
        else if (repayType == IRDTM) {
            if(i==month){
                mi = capitalBd.multiply(new BigDecimal(rate))
                        .multiply(new BigDecimal(month))
                        .divide(new BigDecimal(12), SCALE, BigDecimal.ROUND_HALF_UP);
            }else{
                mi =  BigDecimal.ZERO;
            }
        }
        //等额等息,按月付息
        else if(repayType == ACAI){
            // 每个月利息
            mi = BigDecimal.valueOf(getSI(capital, 1, rate, repayType));
            // 校准最后一个月利息(总的利息-前N个月的利息)
            if (i == month) {
                BigDecimal si = BigDecimal.valueOf(getSI(capital, month, rate, repayType));
                mi = si.subtract(mi.multiply(new BigDecimal(month - 1)));
            }
        }
        /*
         * if (mi.compareTo(BigDecimal.ZERO) == -1) { mi = BigDecimal.ZERO; }
         */
        return handRound(mi.longValue());
    }

    /**
     * 获取每个月待还的本金
     * @param capital 本金
     * @param i 第几个月
     * @param month 总的月数
     * @param rate 年利率
     * @param repayType 还款类型
     * @return
     */
    public  BigDecimal getMP(long capital, int i, int month, double rate, int repayType) {

        BigDecimal pm = BigDecimal.ZERO;
        BigDecimal capitalBd = BigDecimal.valueOf(capital);

        // 等额本息
        if (repayType == ACPI) {
            BigDecimal hpm = BigDecimal.ZERO;
            if (i == month) {
                for (int n = 1; n < month; n++) {
                    // N-1个月本金之和 = (N月本息 - N月息)+(N+1月本息-N+1月息)
                    long monthPrinc = getMPI(capital, n, month, rate, repayType) - getMI(capital, n, month, rate, repayType);
                    hpm = hpm.add(BigDecimal.valueOf(monthPrinc));
                }
                // 最后一个月本金 = 总的本金 - (N-1个月本金之和）
                pm = capitalBd.subtract(hpm);
            } else {
                // 月本金 = 月本息 - 月息
                long monthPrinc = getMPI(capital, i, month, rate, repayType) - getMI(capital, i, month, rate, repayType);
                pm = BigDecimal.valueOf(monthPrinc);
            }
        }

        // 按月付息
        else if (repayType == MPDI) {
            // 最后一个月返还本金
            if (i == month) {
                pm = capitalBd;
            }
        }
        // 到期返本付息
        else if (repayType == IRDTM) {
            if (i == month) {
                pm = capitalBd;
            }
        }
        //等额等息,按月付息
        else if(repayType == ACAI){
            pm = capitalBd.divide(new BigDecimal(month),SCALE,BigDecimal.ROUND_HALF_UP);
            pm = new BigDecimal(handRound(pm.longValue()));
            if (i == month) {
                pm = capitalBd.subtract(new BigDecimal(pm.longValue()*(month - 1)));
            }
        }
        return pm;
    }

    /**
     * 计算月标到投资期限所产生的总的利息收益 预期利息 (sum interest)
     * @param capital 贷款金额
     * @param month 贷款月数
     * @param rate 年利率
     * @param repayType 年利率
     * @return
     */
    public  long getSI(long capital, int month, double rate, int repayType) {

        BigDecimal result = BigDecimal.ZERO;
        BigDecimal capitalBd = BigDecimal.valueOf(capital);

        // 等额本息
        if (repayType == ACPI) {
            rate = rate / 12;
            result = capitalBd.multiply(new BigDecimal(month * rate * Math.pow((1 + rate), month)))
                    .divide(new BigDecimal(Math.pow((1 + rate), month) - 1), SCALE, BigDecimal.ROUND_HALF_UP);
            result = result.subtract(capitalBd);
        }

        // 按月付息
        else if (repayType == MPDI) {
            /**
             * 算法： 每月利息 = 本金*利率*时间/12
             */
            BigDecimal interest = capitalBd.multiply(new BigDecimal(rate))
                    .multiply(new BigDecimal(month))
                    .divide(new BigDecimal(12), SCALE, BigDecimal.ROUND_HALF_UP);
            result = interest;
        }
        // 到期返本付息
        else if (repayType == IRDTM) {
            result = capitalBd.multiply(new BigDecimal(rate))
                    .multiply(new BigDecimal(month))
                    .divide(new BigDecimal(12), SCALE, BigDecimal.ROUND_HALF_UP);
        }
        //等额等息,按月付息
        else if(repayType == ACAI){
            BigDecimal interest = capitalBd.multiply(new BigDecimal(rate))
                    .multiply(new BigDecimal(month))
                    .divide(new BigDecimal(12), SCALE, BigDecimal.ROUND_HALF_UP);
            result = interest;
        }
        return handRound(result.longValue());
    }





    /**
     * 获取剩余本金
     * @param capital 初始借款本金
     * @param i 已还期数
     * @param month 总共期数
     * @param rate 借款年利率
     * @param repayType 还款类型
     * @return
     */
    public  long getRP(long capital, int i, int month, double rate, int repayType) {

        BigDecimal rp = BigDecimal.ZERO;
        BigDecimal capitalBd = BigDecimal.valueOf(capital);

        // 等额本息
        if (repayType == ACPI) {
            // 已还的本金
            BigDecimal hp = BigDecimal.ZERO;
            for (int n = 1; n <= i; n++) {
                hp = hp.add(getMP(capital, n, month, rate, repayType));
            }
            rp = capitalBd.subtract(hp);
        }

        // 按月付息
        else if (repayType == MPDI) {
            // 等于本金
            if (i == month) {
                rp = BigDecimal.ZERO;
            }else{
                rp = capitalBd;
            }

        }

        // 到期返本付息
        else if (repayType == IRDTM) {
            if (i == month) {
                rp = BigDecimal.ZERO;
            }else{
                rp = capitalBd;
            }
        }
        //等额等息,按月付息
        else if(repayType == ACAI){
            // 已还的本金
            BigDecimal hp = BigDecimal.ZERO;
            for (int n = 1; n <= i; n++) {
                hp = hp.add(getMP(capital, n, month, rate, repayType));
            }
            rp = capitalBd.subtract(hp);
        }
        return handRound(rp.longValue());
    }

    /**
     * 获取月标的剩余利息(非提前还款计算剩余利息)
     * @param capital 初始借款本金
     * @param repayed 已还期数
     * @param cycle 总共期数
     * @param rate 借款年利率
     * @param repayType 还款类型
     * @return
     */
    public  long getRI(long capital, int repayed, int cycle, double rate, int repayType) {

        BigDecimal ri = BigDecimal.ZERO;

        // 等额本息 按月付息
        if (repayType == ACPI || repayType == MPDI) {
            // 已还的利息
            BigDecimal hi = BigDecimal.ZERO;
            for (int n = 1; n <= repayed; n++) {
                hi = hi.add(BigDecimal.valueOf(getMI(capital, n, cycle, rate, repayType)));
            }
            ri = BigDecimal.valueOf(getSI(capital, cycle, rate, repayType)).subtract(hi);
        }
        // 到期返本付息
        else if (repayType == IRDTM) {
            if (repayed == cycle) {
                ri = BigDecimal.ZERO;
            }else{
                ri = BigDecimal.valueOf(capital).multiply(new BigDecimal(rate))
                        .multiply(new BigDecimal(cycle))
                        .divide(new BigDecimal(12), SCALE, BigDecimal.ROUND_HALF_UP);
            }
        }
        //等额等息,按月付息
        else if(repayType == ACAI){
            // 已还的利息
            BigDecimal hi = BigDecimal.ZERO;
            for (int n = 1; n <= repayed; n++) {
                hi = hi.add(BigDecimal.valueOf(getMI(capital, n, cycle, rate, repayType)));
            }
            ri = BigDecimal.valueOf(getSI(capital, cycle, rate, repayType)).subtract(hi);
        }
        return handRound(ri.longValue());
    }



    /**
     * 获取还款日列表
     * @param date 放款时间(次日计息，该日起为计息日的昨日)
     * @param cycle 借款周期(如果是月标就是第几期，如果是天标就是总的时间)
     * @param repayType 还款类型
     * @return
     */
    public  Map<Integer, Date> getNRD(Date date, int cycle, int repayType) {

        Map<Integer, Date> repayDateMap = new HashMap<Integer, Date>();

        if (repayType == ACPI || repayType == MPDI|| repayType == IRDTM||repayType==ACAI) {

            for (int period = 1; period <= cycle; period++) {

                repayDateMap.put(period, DateUtils.addMonth(date, period));

            }

        }

        return repayDateMap;

    }
    
    
   /* public static void main(String[] args) {
        

        int repayType = CalcHelper.ACPI;
        
        // 借款金额
        long capital = 100000000;
        
        // 借款周期
        int cycle = 12;
        
        // 年利率
        double rate = 0.12;
        
        
        
        // 计算月标的本息收益 预期本金和利息
        System.out.println(CalcHelper.getPI(capital, cycle, rate, repayType));
        
        // 预期利息
        System.out.println(CalcHelper.getSI(capital, cycle, rate, repayType));
        
        
        
        // 计算每月还款本息
        for(int i=1; i<=cycle; i++) {
            
            System.out.println("期数：" + i);
            
            System.out.println( "本金：" + CalcHelper.getMP(capital, i, cycle, rate, repayType).longValue() +
                    
                                ", 剩余本金：" + CalcHelper.getRP(capital, i, cycle, rate, repayType) + 
                                
                                ", 利息：" + CalcHelper.getMI(capital, i, cycle, rate, repayType) +
            
                                ", 剩余利息：" + CalcHelper.getRI(capital, i, cycle, rate, repayType) + 
            
                                ", 本息：" + CalcHelper.getMPI(capital, i, cycle, rate, repayType));      
        }
        
    }*/

}
    

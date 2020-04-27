package com.ft.framework.util;

/**
 * 功能描述: 序列ID生成工具
 * 创建人: lichunlin
 */
public class IdWorkerUtils {
    private static IdWorker idWorker = null;

    static {
        if (null == idWorker) {
            idWorker = IdWorker.getFlowIdWorkerInstance(3);
        }
    }

    public static long generateID() {
        try {
            return idWorker.nextId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void setWorkerId(long workerId) {
        idWorker = IdWorker.getFlowIdWorkerInstance(workerId);
    }


    public static void main(String[] args) {
        setWorkerId(1);
        System.out.println(generateID());
    }

}

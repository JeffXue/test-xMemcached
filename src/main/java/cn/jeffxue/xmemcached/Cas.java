package cn.jeffxue.xmemcached;

import net.rubyeye.xmemcached.CASOperation;
import net.rubyeye.xmemcached.MemcachedClient;

import java.util.concurrent.CountDownLatch;


public class Cas extends Thread {

    private MemcachedClient mc;
    private CountDownLatch cd;
    private int maxTries;
    private String key;

    public Cas(MemcachedClient mc, CountDownLatch cdl, String key, Integer maxTries) {
        super();
        this.mc = mc;
        this.cd = cdl;
        this.key = key;
        this.maxTries = maxTries;
    }

    public void run() {
        try {
            if (mc.cas(key, 0, new CASOperation<Integer>() {
                public int getMaxTries() {

                    return maxTries;
                }

                public Integer getNewValue(long currentCAS, Integer currentValue) {
                    if (currentValue > 0) {
                        return currentValue - 1;
                    } else {
                        RuntimeException e = new RuntimeException("库存不足");
                        throw e;
                    }
                }
            }))
                System.out.println("扣除库存成功");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            this.cd.countDown();
        }
    }
}

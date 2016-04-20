package cn.jeffxue.xmemcached;

import net.rubyeye.xmemcached.MemcachedClient;

import java.util.concurrent.CountDownLatch;

public class Decr extends Thread{

    private MemcachedClient mc;
    private CountDownLatch cd;
    private String key;

    public Decr(MemcachedClient mc, CountDownLatch cdl, String key) {
        super();
        this.mc = mc;
        this.cd = cdl;
        this.key = key;
    }

    public void run() {
        try {
            if(mc.get(key).toString() != "0"){
                System.out.println("扣除库存成功:" + mc.decr(key, 1));
            }else{
                System.err.println("库存不足");
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            this.cd.countDown();
        }
    }

}

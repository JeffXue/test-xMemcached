package cn.jeffxue.test;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.utils.AddrUtil;

import java.util.concurrent.CountDownLatch;

import cn.jeffxue.xmemcached.Cas;

public class CasTest {

    public static void main(String[] args) throws Exception {

        //设置并发线程数量
        int NUM = 10000;

        //设置最多重试次数
        int maxTries = 100;

        //设置key
        String key = "myCas";

        MemcachedClient mc = new XMemcachedClient(AddrUtil.getAddresses("192.168.1.200:11211"));

        //设置库存数量
        mc.set(key, 0, 1000);

        CountDownLatch cdl = new CountDownLatch(NUM);
        long start = System.currentTimeMillis();

        for (int i = 0; i < NUM; i++)
            new Cas(mc, cdl, key, maxTries).start();

        cdl.await();
        System.out.println("test cas,timed:" + (System.currentTimeMillis() - start));
        System.out.println("inventory=" + mc.get(key));
        mc.shutdown();
    }

}

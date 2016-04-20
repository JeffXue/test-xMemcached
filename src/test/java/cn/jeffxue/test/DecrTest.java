package cn.jeffxue.test;

import cn.jeffxue.xmemcached.Decr;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.utils.AddrUtil;

import java.util.concurrent.CountDownLatch;

public class DecrTest {

    public static void main(String[] args) throws Exception {

        //设置并发线程数量
        int NUM = 10000;

        //设置key
        String key = "myDecr";

        MemcachedClient mc = new XMemcachedClient(AddrUtil.getAddresses("192.168.1.200:11211"));
        mc.setName(key);

        //设置库存数量
        mc.set(key, 0, "8000");

        CountDownLatch cdl = new CountDownLatch(NUM);
        long start = System.currentTimeMillis();

        for (int i = 0; i < NUM; i++)
            new Decr(mc, cdl, key).start();

        cdl.await();
        System.out.println("test decr,timed:" + (System.currentTimeMillis() - start));
        System.out.println("inventory=" + mc.get(key));
        mc.shutdown();
    }
}

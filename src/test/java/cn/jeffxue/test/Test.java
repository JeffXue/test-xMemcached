package cn.jeffxue.test;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.utils.AddrUtil;

public class Test {

    public static void main(String[] args) {

        XMemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses("192.168.1.200:11211"));
        try {

            MemcachedClient client = builder.build();
            System.out.println(client.set("myKey", 0, "10"));
            System.out.println(client.decr("myKey", 1));
            System.out.println(client.get("myKey"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

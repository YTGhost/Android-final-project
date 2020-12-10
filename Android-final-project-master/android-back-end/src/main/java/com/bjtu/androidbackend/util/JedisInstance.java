package com.bjtu.androidbackend.util;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisInstance {
    // 私有化构造函数
    private JedisInstance(){}


    static enum SingletonEnum{
        // 定义一个静态枚举对象，该对象天生为单例
        INSTANCE;
        private JedisPool jedisPool;
        // 私有化枚举的构造函数
        private SingletonEnum() {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(30);
            config.setMaxIdle(10);
            jedisPool = new JedisPool(config, "182.92.66.200", 6384, 10000, "yt232419");

        }
        public JedisPool getInstance() {
            return jedisPool;
        }
    }
    // 对外暴露一个获取对象的静态方法
    public static JedisPool getInstance() {
        return SingletonEnum.INSTANCE.getInstance();
    }
}

package com.bjtu.androidbackend;

import com.bjtu.androidbackend.util.JedisInstance;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

public class JedisTest {

    private Jedis jedis = JedisInstance.getInstance().getResource();

    @Test
    public void set() {
        jedis.set("test", String.valueOf(123456));
        System.out.println(jedis.get("test"));
    }
}

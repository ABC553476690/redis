package com.qfedu.jedis;

import com.alibaba.fastjson.JSON;
import com.qfedu.pojo.User;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.Map;

public class JedisTest {
    @Test
    public void test1() {
        Jedis jedis = new Jedis("192.168.240.132", 6379);

        jedis.auth("root");
        System.out.println(111111111);
        jedis.set("username","mzl");
        System.out.println(jedis.get("username"));
        jedis.close();
    }

    @Test
    public void test2() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(30);
        jedisPoolConfig.setMinIdle(10);
        jedisPoolConfig.setMaxTotal(50);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig,"192.168.240.132",6379);

        Jedis jedis = jedisPool.getResource();

        jedis.auth("root");
        HashMap<String,String> map = new HashMap<String, String>();

        map.put("user1",JSON.toJSONString(new User(14,"goulei")));
        map.put("user2",JSON.toJSONString(new User(13,"shaolei")));
        map.put("user3",JSON.toJSONString(new User(17,"djj")));
        map.put("user4",JSON.toJSONString(new User(18,"mzl")));

        jedis.set("user", JSON.toJSONString(new User(18,"mzl")));
        jedis.hset("map",map);
        System.out.println(jedis.get("user"));
        Map<String, String> map1 = jedis.hgetAll("map");
        System.out.println(map1);

        jedis.close();
        jedisPool.close();
    }
}

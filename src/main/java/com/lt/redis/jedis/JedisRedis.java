package com.lt.redis.jedis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 测试redis
 * @author luot
 * @date   2023年2月23日
 *
 *
 */
public class JedisRedis {
	public static void main(String[] args) {
		//AUTH  密码redis
		//redis连接池连接
		JedisPool jedisPool = new JedisPool(new GenericObjectPoolConfig<Object>(),"localhost",6379,5000,"redis");
		try {
			Jedis jedis = jedisPool.getResource();

			System.out.println(jedis.keys("*"));
		}finally {
			jedisPool.close();
		}
	}
}

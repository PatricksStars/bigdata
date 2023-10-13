package com.lt.redis.lock;

import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class RedissonLock {
	
	private static final String LOCK_KEY= "lock_key";
	
	public static RedissonClient redisson;
	
	public static RLock getLock() {
		// 创建Redisson配置
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
 
        // 创建Redisson客户端
        redisson = Redisson.create(config);
 
        // 获取锁对象
        RLock lock = redisson.getLock(LOCK_KEY);
        return lock;
	}
	
	public static void closeRedisson() {
		redisson.shutdown();
	}
	
	@Test
	public void testLock() {
		RLock lock = RedissonLock.getLock();
		try {
            // 尝试获取锁对象
            if (lock.tryLock()) {
                // 获取到锁，执行任务
                System.out.println("获取到锁，执行任务...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放锁对象
            lock.unlock();
        }
	}

}

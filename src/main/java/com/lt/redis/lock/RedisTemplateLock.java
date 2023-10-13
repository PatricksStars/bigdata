package com.lt.redis.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisTemplateLock {
	
	@SuppressWarnings("rawtypes")
	@Autowired
    public RedisTemplate redisTemplate;
 
    /**
     * 获取锁
     * @param key       锁key
     * @param seconds   最大锁时间
     * @return true:成功,false:失败
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean lock(String key,Long seconds){
        return (Boolean) redisTemplate.execute((RedisCallback) connection -> {
            /** 如果不存在,那么则true,则允许执行, */
            Boolean acquire = connection.setNX(key.getBytes(), String.valueOf(key).getBytes());
            /** 防止死锁,将其key设置过期时间 */
            connection.expire(key.getBytes(), seconds);
            if (acquire) {
                return true;
            }
            return false;
        });
    }
 
    /**
     * 删除锁
     * @param key
     */
    @SuppressWarnings("unchecked")
	public void unlock(String key) {
        redisTemplate.delete(key);
    }
}

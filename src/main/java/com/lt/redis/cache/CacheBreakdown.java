package com.lt.redis.cache;

import org.springframework.beans.factory.annotation.Autowired;

import com.lt.redis.utils.RedisClient;

/**
 * 缓存击穿
 * 
 * 缓存击穿，指一个key为热点key，在高并发的情况下，这个热点key突然失效，在失效的瞬间大量的请求直接穿破缓存直接访问数据库，
 * 就像在有屏障的情况下穿破了一个洞。

	解决办法：
	1）设置热点数据永不过期
	
	2）使用分布式锁
 * @author luot
 * @date   2023年8月30日
 *
 *
 */
public class CacheBreakdown {
	
	@Autowired
    public RedisClient redisClient;
	
	@SuppressWarnings("unused")
	public Object selectItemInfo(Long itemId) {
        /*****************不能修改原有的逻辑*****************/
        //1、先查redis，如果有直接return
		Object tbItem = (Object) redisClient.get("ITEM_INFO" + ":" + itemId + ":" + "BASE");
        if(tbItem != null){
            return tbItem;
        }
        /*****************解决缓存击穿**********************/
        if(redisClient.setnx("SETNX_LOCK_BASE"+":"+itemId,itemId)) {
            try {
                //2、再查数据库，并缓存到redis再rerun
                tbItem = null;//db.selectByPrimaryKey(itemId);
                //解决缓存穿透问题
                if (tbItem == null) {
                    tbItem = new Object();
                    redisClient.set("ITEM_INFO"+ ":" + itemId + ":" + "BASE", tbItem);
                    redisClient.expire("ITEM_INFO"+ ":" + itemId + ":" + "BASE", 30);
                    return tbItem;
                }
                redisClient.set("ITEM_INFO"+ ":" + itemId + ":" + "BASE", tbItem);
                redisClient.expire("ITEM_INFO"+ ":" + itemId + ":" + "BASE", 86200);
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                //釋放鎖
                redisClient.del("SETNX_LOCK_BASE" + ":" + itemId);
            }
        }else{
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
            selectItemInfo(itemId);
        }
        return tbItem;
    }
	
}

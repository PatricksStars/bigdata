package com.lt.redis.cache;

import org.springframework.beans.factory.annotation.Autowired;

import com.lt.redis.utils.RedisClient;

/**
 * 缓存穿透
 * 
 * 描述：
	缓存穿透是指在Redis缓存和数据库中都没有相应的数据，用户不断发起对应请求，而这些请求就会穿过缓存直接访问数据库。
	假如有恶意攻击，就可以利用这个漏洞，对数据库造成压力，甚至压垮数据库。

	解决办法：
	缓存空对象：当在数据库查询不到数据时，创建空对象放入缓存中，同时设置一个过期时间（避免空值占用太多的存储空间），
	之后在访问这个数据就会从缓存中取出，保护了后端数据库的安全。
 * @author luot
 * @date   2023年8月30日
 *
 *
 */
public class CachePenetration {

	@Autowired
    public RedisClient redisClient;
	
	@SuppressWarnings("unused")
	public Object selectItemInfo(Long id) {
		//查询缓存
		Object tbItem = (Object) redisClient.get("ITEM_INFO "+ ":" + id + ":"+ "BASE");
		if(tbItem!=null){
			return tbItem;
		}
		
		//查询数据库
		tbItem = null;//database.selectByPrimaryKey(id);
		/********************解决缓存穿透************************/
		if(tbItem == null){
			//把空对象保存到缓存
			redisClient.set("ITEM_INFO "+ ":" + id + ":"+ "BASE",new Object());
			//设置缓存的有效期
			redisClient.expire("ITEM_INFO "+ ":" + id + ":"+ "BASE",30);
			return tbItem;
		}
		//把数据保存到缓存
		redisClient.set("ITEM_INFO "+ ":" + id + ":"+ "BASE",tbItem);
		//设置缓存的有效期
		redisClient.expire("ITEM_INFO "+ ":" + id + ":"+ "BASE",86400);
		return tbItem;
	}
}

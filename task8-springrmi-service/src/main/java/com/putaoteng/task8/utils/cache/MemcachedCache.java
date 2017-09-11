package com.putaoteng.task8.utils.cache;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import net.rubyeye.xmemcached.GetsResponse;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

@Component (value="memcachedCache")
public class MemcachedCache {
	private static Logger logger = Logger.getLogger(MemcachedCache.class.getName());

	private MemcachedClient memcachedClient = null;

	public MemcachedCache(){
		@SuppressWarnings("resource")
		ApplicationContext context = new
				ClassPathXmlApplicationContext("xmemcached.xml");
		this.memcachedClient = (MemcachedClient) context.getBean("memcachedClient");
	}
	
	
	// 添加方法
	public void set(String key, int exp, Object value) {
		try {
			if (!memcachedClient.set(key, exp, value)) {
				System.err.println("set error, key is" + key + "value is" + value);
			}
		} catch (TimeoutException e) {
			logger.error("MemcachedClient operation timeout-----(set)" + e.getMessage());
		} catch (InterruptedException e) {
			logger.error("MemcachedClient operation interrupted-----(set)" + e.getMessage());
		} catch (MemcachedException e) {
			logger.error("MemcachedClient operation failed-----(set)" + e.getMessage());
		}
	}

	// 删除一个key
	public void delete(String key) {
		try {
			memcachedClient.delete(key);
		} catch (TimeoutException e) {
			logger.error("MemcachedClient operation timeout-----(delete)" + e.getMessage());
		} catch (InterruptedException e) {
			logger.error("MemcachedClient operation interrupted-----(delete)" + e.getMessage());
		} catch (MemcachedException e) {
			logger.error("MemcachedClient operation failed-----(delete)" + e.getMessage());
		}
	}

	//原子更新,将比较和更新绑定为一个原子的操作
	public boolean update(String key, Object value) {
		try {
			GetsResponse<Integer> result =  memcachedClient.gets(key);
			long cas = result.getCas();
			if (!memcachedClient.cas(key, 0, value, cas)){
				return false;
			}
		} catch (TimeoutException e) {
			logger.error("MemcachedClient operation timeout-----(update)" + e.getMessage());
		} catch (InterruptedException e) {
			logger.error("MemcachedClient operation interrupted-----(update)" + e.getMessage());
		} catch (MemcachedException e) {
			logger.error("MemcachedClient operation failed-----(update)" + e.getMessage());
		}
		return true;
	}
	
	//根据key,获取值
	public Object get(String key){
		try {
			return memcachedClient.get(key);
		} catch (TimeoutException e) {
			logger.error("MemcachedClient operation timeout-----(get)" + e.getMessage());
		} catch (InterruptedException e) {
			logger.error("MemcachedClient operation interrupted-----(get)" + e.getMessage());
		} catch (MemcachedException e) {
			logger.error("MemcachedClient operation failed-----(get)" + e.getMessage());
		}
		
		return null;
	}
	
	//额外添加服务
	public void addServer(String server, int port, int weight){
		try {
			memcachedClient.addServer(server, port, weight);
		} catch (IOException e) {
			logger.error("添加Memcacahed失败:" + e.getMessage());
		}
	}
	
	//移除一个服务
	public void removeServer(String hostList){
		memcachedClient.removeServer(hostList);
	}
}

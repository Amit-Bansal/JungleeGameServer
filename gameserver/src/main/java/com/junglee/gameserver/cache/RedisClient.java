package com.junglee.gameserver.cache;

import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
public class RedisClient {

	private JedisPool jedisPool;
	
	RedisClient(){
		final JedisPoolConfig poolConfig = buildPoolConfig();
		jedisPool = new JedisPool(poolConfig, "localhost");
	}
	 
	public void write(String key, String value){
		try (Jedis jedis = jedisPool.getResource()) {
			jedis.set(key, value);

		}
	}
	
	public String read(String key){
		try (Jedis jedis = jedisPool.getResource()) {
	    	String cachedResponse = jedis.get(key);
	    	return cachedResponse;
		}
		catch (Exception e) {
			return null;
        }
	}
	
	
	private JedisPoolConfig buildPoolConfig() {
	    final JedisPoolConfig poolConfig = new JedisPoolConfig();
	    poolConfig.setMaxTotal(128);
	    poolConfig.setMaxIdle(128);
	    poolConfig.setMinIdle(16);
	    poolConfig.setTestOnBorrow(true);
	    poolConfig.setTestOnReturn(true);
	    poolConfig.setTestWhileIdle(true);
	    //poolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(60).toMillis());
	    //poolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(30).toMillis());
	    poolConfig.setNumTestsPerEvictionRun(3);
	    poolConfig.setBlockWhenExhausted(true);
	    return poolConfig;
	}
}

package com.fgj.comment.config;

import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.locks.ReadWriteLock;

/**
 * 放弃使用这种方式了 ┭┮﹏┭┮，鸡肋，大缓存放service层（redis），小缓存就放java内存就行了
 */
public class RedisCache implements Cache {
    private String id;
    public static RedisTemplate redisTemplate;

    public RedisCache(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        redisTemplate.opsForValue().set(key.toString(),value);
    }

    @Override
    public Object getObject(Object key) {
        return redisTemplate.opsForValue().get(key.toString());
    }

    @Override
    public Object removeObject(Object key) {
        redisTemplate.delete(key.toString());
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }
}

package com.chat.util;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    /**
     * 设置 key-value
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 设置 key-value 并设置过期时间
     *
     * @param key
     * @param value
     * @param expireTime 过期时间，单位秒
     * @return
     */
    public boolean set(String key, Object value, long expireTime) {
        try {
            redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 获取 key 对应的值
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
    /**
     * 删除 key
     *
     * @param key
     * @return
     */
    public boolean delete(String key) {
        try {
            redisTemplate.delete(key);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 判断 key 是否存在
     *
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }
    /**
     * 获取 key 的过期时间
     *
     * @param key
     * @return 过期时间，单位秒
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }
    /**
     * 设置 key 的过期时间
     *
     * @param key
     * @param expireTime 过期时间，单位秒
     * @return
     */
    public boolean setExpire(String key, long expireTime) {
        return redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
    }
    /**
     * 自增操作
     *
     * @param key
     * @param delta 自增步长
     * @return
     */
    public Long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }
    /**
     * 自减操作
     *
     * @param key
     * @param delta 自减步长
     * @return
     */
    public Long decrement(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, -delta);
    }
    /**
     * 获取匹配的 key 列表
     *
     * @param pattern 匹配规则
     * @return
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }


    public Long leftPush(String k, Object v) {
        return redisTemplate.opsForList().leftPush(k, v);
    }

    public Long rightPush(String k, Object v) {
        return redisTemplate.opsForList().rightPush(k, v);
    }

    public Object rightPop(String k) {
        try{
            return redisTemplate.opsForList().rightPop(k);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 不存在则将键/值对插入
     * @param key
     * @return
     */
    public Boolean putIfAbsentHash(String key, Object k, Object v) {
        return redisTemplate.opsForHash().putIfAbsent(key, k, v);
    }


    public Object hget(String key, Object k) {
        return redisTemplate.opsForHash().get(key, k);
    }

    public Map<Object, Object> hgetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    public List<Object> hVals(String key) {
        return redisTemplate.opsForHash().values(key);
    }

    public List<Object> range(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    public void hset(String key, Object k, Object v) {
        redisTemplate.opsForHash().put(key, k, v);
    }

    public void hDelete(String key, Object... k) {
        redisTemplate.opsForHash().delete(key, k);
    }

    public Set hKeys(String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    public boolean tryLock(String key, String value, long expireTime) {
        Boolean result = false;
        try {
            result = redisTemplate.opsForValue().setIfAbsent(key, value, expireTime, TimeUnit.SECONDS);
            if (result == null) {
                return false;
            }
        }catch(Exception e){
            log.error("{}", e);
        }
        return result;
    }

    public boolean unlock(String key, String value) {
        String currentValue = (String) redisTemplate.opsForValue().get(key);
        if (StrUtil.isNotEmpty(currentValue) && currentValue.equals(value)) {
            redisTemplate.opsForValue().getOperations().delete(key);
            return true;
        }
        return false;
    }


}

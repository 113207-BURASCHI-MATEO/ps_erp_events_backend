package com.tup.ps.erpevents.services.impl;

import com.tup.ps.erpevents.services.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {

  private final RedisTemplate<String, Object> redisTemplate;

  @Autowired
  public RedisServiceImpl(@Qualifier("redisTemplateObject") RedisTemplate<String, Object> redisTemplateArg) {
    this.redisTemplate = redisTemplateArg;
  }

  @Override
  public void saveValue(String key, String value) {
    redisTemplate.opsForValue().set(key, value);
  }

  @Override
  public void saveValueWithExpiration(String key, String value, Double expiration, TimeUnit timeUnit) {
    redisTemplate.opsForValue().set(key, value, expiration.longValue(), timeUnit);
  }

  @Override
  public String getValue(String key) {
    return (String) redisTemplate.opsForValue().get(key);
  }

  @Override
  public void saveObject(String key, Object obj) {
    redisTemplate.opsForValue().set(key, obj);
  }

  @Override
  public void saveObjectWithExpiration(String key, Object obj, Double expiration, TimeUnit timeUnit) {
    redisTemplate.opsForValue().set(key, obj, expiration.longValue(), timeUnit);
  }

  @Override
  public Object getObject(String key) {
    return redisTemplate.opsForValue().get(key);
  }

  @Override
  public Set<String> getKeys(String pattern) {
    return redisTemplate.keys(pattern);
  }

  @Override
  public void removeObject(String key) {
    redisTemplate.delete(key);
  }
}

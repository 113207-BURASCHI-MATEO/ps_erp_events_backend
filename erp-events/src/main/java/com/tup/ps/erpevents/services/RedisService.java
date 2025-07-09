package com.tup.ps.erpevents.services;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;


@Service
public interface RedisService {

  void saveValue(String key, String value);

  void saveValueWithExpiration(String key, String value, Double expiration, TimeUnit timeUnit);

  String getValue(String key);

  Object getObject(String key);

  void saveObject(String key, Object obj);

  void saveObjectWithExpiration(String key, Object obj, Double expiration, TimeUnit timeUnit);

  Set<String> getKeys(String pattern);

  void removeObject(String key);
}

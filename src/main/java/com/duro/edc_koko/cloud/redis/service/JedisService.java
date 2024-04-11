package com.duro.edc_koko.cloud.redis.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface JedisService {
    void set (String key, String value);

    void setTimeToLive (String key, long timeoutInDays);

    void hashSet (String key, String field, Object value);

    boolean hashExists (String key, String field);

    Object get (String key);

    Map<String, Object> getField (String key);

    Object hashGet (String key, String field);

    List<Object> hashGetByFieldPrefix (String key, String fieldPrefix);

    Set<String> getFieldPrefixes (String key);

    void delete (String key);

    void delete (String key, String field);

    void delete (String key, List<String> fields);
}

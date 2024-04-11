package com.duro.edc_koko.cloud.redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPooled;

@Service
@RequiredArgsConstructor
public class JedisService {
    private final JedisPooled jedis;

    public void set (String key, String value) {
        jedis.set(key, value);
    }

}

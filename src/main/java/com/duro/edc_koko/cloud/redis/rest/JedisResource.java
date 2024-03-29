package com.duro.edc_koko.cloud.redis.rest;

import com.duro.edc_koko.cloud.redis.service.JedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jedis")
public class JedisResource {
    private final JedisService service;

    @GetMapping
    public void set() {
        service.set("jedis-test", "1911");
    }
}

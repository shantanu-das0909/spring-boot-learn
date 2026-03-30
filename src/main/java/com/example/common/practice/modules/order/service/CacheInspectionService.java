package com.example.common.practice.modules.order.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class CacheInspectionService {

    @Autowired
    private CacheManager cacheManager;

    public void printCacheContent(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);

        if(cache != null) {
            log.info("Cache details -> {}", Objects.requireNonNull(cache.getNativeCache().toString()));

        } else {
            log.info("Cache not found with name -> {}", cacheName);
        }
    }
}

package com.github.sejoung.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AudienceADLogCacheService {

    @Autowired
    private Map<String, Integer> audienceADLogCache;
    
    
    public synchronized void update(String key) {
        
        Integer i = audienceADLogCache.get(key);
        
        if(i == null) {
            i=0;
        }
        audienceADLogCache.put(key, i+1);
        
    }
}

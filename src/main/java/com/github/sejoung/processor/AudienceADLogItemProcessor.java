package com.github.sejoung.processor;

import java.util.List;
import java.util.Map;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.sejoung.model.AudienceADLog;
import com.github.sejoung.model.InctKc;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AudienceADLogItemProcessor implements ItemProcessor<AudienceADLog,AudienceADLog> {
/*
    @Autowired
    private StringRedisTemplate redisTemplate;
    
    */
    @Autowired
    private Map<String, Integer> audienceADLogCache;
    
    @Override
    public AudienceADLog process(AudienceADLog item) throws Exception {
        //log.debug(item.toString());
        List<InctKc> inctKcs = item.getInctKcs();
        /*
        inctKcs.forEach((inctKc)->{
            redisTemplate.opsForValue().increment(inctKc.getCategory(), 1);
        });
        */
        
        
        inctKcs.forEach((inctKc)->{
            String key = inctKc.getCategory();
            
            Integer i = audienceADLogCache.get(key);
            
            if(i == null) {
                i=0;
            }
            audienceADLogCache.put(key, i+1);
            
        });
        
        return item;
    }

}

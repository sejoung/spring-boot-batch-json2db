package com.github.sejoung.processor;

import java.util.List;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.sejoung.model.AudienceADLog;
import com.github.sejoung.model.InctKc;
import com.github.sejoung.service.AudienceADLogCacheService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AudienceADLogItemProcessor implements ItemProcessor<AudienceADLog,AudienceADLog> {
/*
    @Autowired
    private StringRedisTemplate redisTemplate;
    
    */
    @Autowired
    private AudienceADLogCacheService audienceADLogCacheService;
    
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
            
            audienceADLogCacheService.update(key);
            
        });
        
        return item;
    }

}

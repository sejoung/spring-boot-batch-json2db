package com.github.sejoung.processor;

import org.springframework.batch.item.ItemProcessor;

import com.github.sejoung.model.AudienceADLog;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AudienceADLogItemProcessor implements ItemProcessor<AudienceADLog,AudienceADLog> {

    @Override
    public AudienceADLog process(AudienceADLog item) throws Exception {
        //log.debug(item.toString());
        return item;
    }

}

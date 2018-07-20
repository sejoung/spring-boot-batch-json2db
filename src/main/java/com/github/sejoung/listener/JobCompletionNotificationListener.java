package com.github.sejoung.listener;

import java.util.Map;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

/**
 * @author kim se joung
 *
 */
@Slf4j
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    @Autowired
    Map<String, Integer> audienceADLogCache;

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED!");

            for (Map.Entry<String, Integer> entry : audienceADLogCache.entrySet()) {
                log.info("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            }
            
            log.info("!!! JOB FINISHED!");
        }
    }
}

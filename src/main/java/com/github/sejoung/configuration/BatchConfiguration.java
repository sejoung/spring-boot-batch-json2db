package com.github.sejoung.configuration;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.github.sejoung.Incrementer.CurrentTimeIncrementer;
import com.github.sejoung.listener.ChunkExecutionListener;
import com.github.sejoung.listener.JobCompletionNotificationListener;
import com.github.sejoung.listener.StepExecutionNotificationListener;

/**
 * @author kim se joung
 *
 */
//@Configuration
public class BatchConfiguration extends DefaultBatchConfigurer {
    
 /*   
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

     (non-Javadoc)
     * @see org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer#setDataSource(javax.sql.DataSource)
     * 
     * 스프링 배치에서 배치 정보를 저장하는 DB를 사용하지 않을려고 데이터 소스를 set 하는 부분을 주석 처리함
     * 
     
    @Override
    public void setDataSource(DataSource dataSource) {
    }

    @Bean
    public StepExecutionNotificationListener stepExecutionListener() {
        return new StepExecutionNotificationListener();
    }

    @Bean
    public ChunkExecutionListener chunkListener() {
        return new ChunkExecutionListener();
    }

    @Bean
    public JobCompletionNotificationListener jobExecutionListener() {
        return new JobCompletionNotificationListener();
    }

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importUserJob").incrementer(new CurrentTimeIncrementer()).listener(listener).flow(step1).end().build();
    }
    
    @Bean
    public Step step1(Tasklet tasklet1) {
        return stepBuilderFactory.get("step1").tasklet(tasklet1).build();
    }
    
    @Bean
    public Tasklet tasklet1() {
        return (contribution, chunkContext)->{
            return RepeatStatus.FINISHED;
        };
    }
    
    */
}

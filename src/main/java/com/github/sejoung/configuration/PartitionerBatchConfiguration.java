package com.github.sejoung.configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.partition.support.MultiResourcePartitioner;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.task.TaskExecutor;

import com.github.sejoung.linemapper.AudienceADLogLineMapper;
import com.github.sejoung.listener.ChunkExecutionListener;
import com.github.sejoung.listener.JobCompletionNotificationListener;
import com.github.sejoung.listener.StepExecutionNotificationListener;
import com.github.sejoung.model.AudienceADLog;
import com.github.sejoung.processor.AudienceADLogItemProcessor;

import lombok.extern.slf4j.Slf4j;

/**
 * @author kim se joung
 *
 */
@Slf4j
@Configuration
public class PartitionerBatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public TaskExecutor taskExecutor;

    @Value("audiencelog/*/*/*.log")
    private Resource[] resources;

    @Bean
    public AudienceADLogLineMapper audienceADLogLineMapper() {
        return new AudienceADLogLineMapper();
    }
    
    @Bean
    public Map<String, Integer>  audienceADLogCache() {
        return new HashMap<String, Integer>();
    }

    @Bean
    public AudienceADLogItemProcessor audienceADLogItemProcessor() {
        return new AudienceADLogItemProcessor();
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
    public Job importUserJob(JobCompletionNotificationListener listener, Step partitionStep) {
        return jobBuilderFactory.get("importUserJob").incrementer(new RunIdIncrementer()).listener(listener).start(partitionStep).build();
    }

    @Bean(name = "partitionStep")
    public Step partitionStep() {
        return stepBuilderFactory.get("partitionStep").partitioner("step1", partitioner()).step(step1()).taskExecutor(taskExecutor).gridSize(10).build();
    }

    @Bean(name = "step1")
    public Step step1() {
        return stepBuilderFactory.get("step1").<AudienceADLog, AudienceADLog>chunk(1).reader(audienceADLogReader(null)).processor(audienceADLogItemProcessor()).listener(chunkListener()).listener(stepExecutionListener()).build();
    }

    @StepScope
    @Bean(name = "audienceADLogReader")
    public FlatFileItemReader<AudienceADLog> audienceADLogReader(@Value("#{stepExecutionContext['fileName']}") Resource resource) {
        return new FlatFileItemReaderBuilder<AudienceADLog>().name("audienceADLogReader").resource(resource).lineMapper(audienceADLogLineMapper()).build();
    }

    @Bean(name = "audienceADLogPartitioner")
    public MultiResourcePartitioner partitioner() {
        MultiResourcePartitioner partitioner = new MultiResourcePartitioner();
        partitioner.setResources(resources);
        return partitioner;
    }

}

package com.github.sejoung.configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.partition.support.MultiResourcePartitioner;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.builder.TaskletStepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.github.sejoung.Incrementer.CurrentTimeIncrementer;
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
public class PartitionerBatchConfiguration extends DefaultBatchConfigurer {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public TaskExecutor taskExecutor;

    @Value("audiencelog/*/*/*.log")
    private Resource[] resources;

    @Override
    public void setDataSource(DataSource dataSource) {
        // override to do not set datasource even if a datasource exist.
        // initialize will use a Map based JobRepository (instead of database)
    }

    @Bean
    public AudienceADLogLineMapper audienceADLogLineMapper() {
        return new AudienceADLogLineMapper();
    }

    @Bean
    public Map<String, Integer> audienceADLogCache() {
        return new ConcurrentHashMap<String, Integer>();
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
    public Job importUserJob(JobCompletionNotificationListener listener, Step step2) {
        return jobBuilderFactory.get("importUserJob").incrementer(new CurrentTimeIncrementer()).listener(listener).start(partitionStep()).next(step2).build();
    }

    @Bean
    public Step partitionStep() {
        return stepBuilderFactory.get("partitionStep").partitioner("step1", partitioner()).step(step1()).taskExecutor(taskExecutor).build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1").<AudienceADLog, AudienceADLog>chunk(1).reader(audienceADLogReader(null)).processor(audienceADLogItemProcessor()).listener(chunkListener()).listener(stepExecutionListener()).build();
    }

    @Bean
    public Step step2(Map<String, Integer> audienceADLogCache) {
        StepBuilder step2 = stepBuilderFactory.get("step2");
        TaskletStepBuilder tasklet = step2.tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                log.debug("step2 start");
        /*        
                redisTemplate.keys("*").forEach((s) -> {
                    log.info("Key = " + s + ", Value = " + redisTemplate.opsForValue().get(s));
                });
*/

                for (Map.Entry<String, Integer> entry : audienceADLogCache.entrySet()) {
                    log.info("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                }
                log.debug("step2 end");

                return RepeatStatus.FINISHED;
            }
        });

        // true: 이 단계가 이미 완료 되었더라도 모든 작업 실행은이 단계를 수행합니다.
        // false: 작업이 중단되고 다시 실행되면이 단계가 다시 수행되지 않습니다.
        tasklet.allowStartIfComplete(false);

        return tasklet.build();
    }

    @StepScope
    @Bean
    public FlatFileItemReader<AudienceADLog> audienceADLogReader(@Value("#{stepExecutionContext['fileName']}") Resource resource) {
        return new FlatFileItemReaderBuilder<AudienceADLog>().name("audienceADLogReader").resource(resource).lineMapper(audienceADLogLineMapper()).build();
    }

    @Bean
    public MultiResourcePartitioner partitioner() {
        MultiResourcePartitioner partitioner = new MultiResourcePartitioner();
        partitioner.setResources(resources);
        return partitioner;
    }

}

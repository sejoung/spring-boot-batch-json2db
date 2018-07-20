package com.github.sejoung.configuration;

/**
 * @author kim se joung
 *
 */
//@Configuration
public class BatchConfiguration {
/*
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

   // @Value("audiencelog.log")
    private Resource[] resources;
    
  //  @Value("${threadpool.size}")
    private int poolsize;

    @Bean(name = "audienceADLogLineMapper")
    public AudienceADLogLineMapper audienceADLogLineMapper() {
        return new AudienceADLogLineMapper();
    }
    @Bean(name = "audienceADLogItemProcessor")
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
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importUserJob").incrementer(new RunIdIncrementer()).listener(listener).flow(step1).end().build();
    }

    @Bean
    public Step step1(MultiResourceItemReader<AudienceADLog> audienceADLogReader, TaskExecutor taskExecutor, AudienceADLogItemProcessor audienceADLogItemProcessor, ChunkExecutionListener chunkListener, StepExecutionNotificationListener stepExecutionListener) {
        return stepBuilderFactory.get("step1").<AudienceADLog, AudienceADLog>chunk(10).reader(audienceADLogReader).processor(audienceADLogItemProcessor).taskExecutor(taskExecutor).listener(chunkListener).listener(stepExecutionListener).throttleLimit(poolsize).build();
    }

    @Bean(name = "audienceADLogReader")
    public MultiResourceItemReader<AudienceADLog> audienceADLogReader(AudienceADLogLineMapper audienceADLogLineMapper) {

        FlatFileItemReader<AudienceADLog> fileReader = new FlatFileItemReader<AudienceADLog>();
        fileReader.setLineMapper(audienceADLogLineMapper);

        return new MultiResourceItemReaderBuilder<AudienceADLog>().name("audience").delegate(fileReader).resources(resources).build();
    }
    */
}

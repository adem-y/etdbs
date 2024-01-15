package com.ademy.etdbs.config;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class JobConfiguration {

    private JobBuilderFactory jobBuilderFactory;
    private SpringBatchConfig batchConfig;

    @Bean
    public Job runJob(){
        return jobBuilderFactory.get("importEmployees")
                .listener(batchConfig.jobListener())
                .start(batchConfig.stepToLoadWholeData())
                .next(batchConfig.stepToLoadAdditionalData())
                .build();
    }
}

package com.ademy.etdbs.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ImportEmployeeJobListener extends JobExecutionListenerSupport {

    public ImportEmployeeJobListener() {
        super();
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("ETDBS-Job Completed");
        } else if (jobExecution.getStatus() == BatchStatus.FAILED) {
            log.error("ETDBS-Job Failed");
        }
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("ETDBS-Job Started");
        super.beforeJob(jobExecution);
    }
}

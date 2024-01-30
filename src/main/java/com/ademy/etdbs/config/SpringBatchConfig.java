package com.ademy.etdbs.config;

import com.ademy.etdbs.entity.Employee;
import com.ademy.etdbs.listener.ImportEmployeeJobListener;
import com.ademy.etdbs.listener.ImportEmployeeStepListener;
import com.ademy.etdbs.processor.AdditionalEmployeeDataProcessor;
import com.ademy.etdbs.processor.EmployeeDataProcessor;
import com.ademy.etdbs.repo.EmployeeRepo;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class SpringBatchConfig {

    private StepBuilderFactory stepBuilderFactory;

    private EmployeeRepo employeeRepo;

    @Bean
    public FlatFileItemReader<Employee> reader(){
        FlatFileItemReader<Employee> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("src/main/resources/employeeData.csv"));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1); // it will skip table header in csv file
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }
    @Bean
    public FlatFileItemReader<Employee> readerForAdditionalData(){
        FlatFileItemReader<Employee> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("src/main/resources/additionalEmployeeData.csv"));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1); // it will skip table header in csv file
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    private LineMapper<Employee> lineMapper() {
        DefaultLineMapper<Employee> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer(); // helps us to parse csv file
        delimitedLineTokenizer.setDelimiter(";");
        delimitedLineTokenizer.setStrict(false);
        delimitedLineTokenizer.setNames("id", "fullName", "jobTitle", "department", "businessUnit", "gender", "age",
                "hireDate", "annualSalary", "country", "city", "active");

        BeanWrapperFieldSetMapper<Employee> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Employee.class);

        lineMapper.setLineTokenizer(delimitedLineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    @Bean
    public EmployeeDataProcessor processor(){
        return new EmployeeDataProcessor();
    }

    @Bean
    public AdditionalEmployeeDataProcessor additionalProcessor(){
        return new AdditionalEmployeeDataProcessor();
    }

    @Bean
    public RepositoryItemWriter<Employee> writer(){
        RepositoryItemWriter<Employee> writer = new RepositoryItemWriter<>();
        writer.setRepository(employeeRepo);
        writer.setMethodName("save"); // it'll use save method of jpa repository
        return writer;
    }

    @Bean
    public Step stepToLoadWholeData(){
        return stepBuilderFactory.get("csv-step")
                .<Employee, Employee>chunk(20) // how many Employee for each transaction
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .listener(stepListener())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Step stepToLoadAdditionalData(){
        return stepBuilderFactory.get("csv-additional-step")
                .<Employee, Employee>chunk(20)
                .reader(readerForAdditionalData())
                .processor(additionalProcessor())
                .writer(writer())
                .listener(stepListener())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public ImportEmployeeJobListener jobListener() {
        return new ImportEmployeeJobListener();
    }

    @Bean
    public ImportEmployeeStepListener stepListener() {
        return new ImportEmployeeStepListener();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(20);
        return asyncTaskExecutor;
    } // without task executor: 2.28 s, with task executor(20): 1.82 s

}

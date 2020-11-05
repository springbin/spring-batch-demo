package com.bin.batch;

import com.bin.model.Person;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

/**
 * 实现从person.csv文件中读取一个人的姓和名。从这些数据生成一个问候语。然后将此问候语写入greeting .txt文件。
 */
@Configuration
public class JobConfig {

    /**
     * 一个Batch(批处理)过程由一个Job(作业)组成。这个实体封装了整个批处理过程。
     * @param jobBuilders
     * @param stepBuilders
     * @return
     */
    @Bean
    public Job myJob(JobBuilderFactory jobBuilders, StepBuilderFactory stepBuilders){
        return jobBuilders.get("myJob").start(myStep(stepBuilders)).build();
    }

    /**
     *  一个Job(作业)可以由一个或多个Step(步骤)组成。
     *     在大多数情况下，
     *       一个步骤将读取数据(通过ItemReader)，
     *       处理数据(使用ItemProcessor)，
     *       然后写入数据(通过ItemWriter)。
     * @param stepBuilders
     * @return
     */
    @Bean
    public Step myStep(StepBuilderFactory stepBuilders){
        return stepBuilders.get("myStep")
                .<Person,String>chunk(10)
                .reader(reader()) //读数据
                .processor(processor())//处理数据
                .writer(writer())//写入数据
                .build();
    }

    /**
     * 读取数据
     * @return
     */
    public FlatFileItemReader<Person> reader() {
        return new FlatFileItemReaderBuilder<Person>()
                .name("personItemReader")
                .resource(new ClassPathResource("csv/persons.csv"))
                .delimited().names(new String[] {"firstName", "lastName"})
                .targetType(Person.class).build();
    }

    /**
     * 处理数据
     * @return
     */
    public PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }

    /**
     * 写入数据
     * @return
     */
    public FlatFileItemWriter<String> writer() {
        return new FlatFileItemWriterBuilder<String>()
                .name("greetingItemWriter")
                .resource(new FileSystemResource("target/test-outputs/greetings.txt"))
                .lineAggregator(new PassThroughLineAggregator<>()).build();
    }


}
